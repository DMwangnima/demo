package com.nuofankj.springdemo.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nuofankj.springdemo.anno.Init;
import com.nuofankj.springdemo.anno.PostInit;
import com.nuofankj.springdemo.reader.ReaderHolder;
import com.nuofankj.springdemo.reader.ResourceReader;
import com.nuofankj.springdemo.schema.InjectDefinition;
import com.nuofankj.springdemo.schema.ResourceDefinition;
import com.nuofankj.springdemo.utility.JSONChange;
import com.nuofankj.springdemo.utility.ReflectionUtility;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 存储空间对象
 *
 * @param <K>
 * @param <V>
 */
public class Storage<K, V> extends Observable implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Autowired
    private ReaderHolder readerHolder;

    // 初始化标记
    private boolean initialized;

    // 资源定义
    private ResourceDefinition resourceDefinition;

    protected volatile StorageData<K, V> data;

    // 标志获取器
    private Getter identifier;

    // 索引获取器集合
    private Map<String, IndexGetter> indexGetters;

    private Method postInitMethod;

    private Method initMethod;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * 初始化Storage
     *
     * @param definition
     */
    public synchronized void initialize(ResourceDefinition definition) {

        // 已经初始化过则不再初始化
        if (initialized) {
            return;
        }
        // 设置初始化标识
        this.initialized = true;
        this.resourceDefinition = definition;
        // 取得对应资源转化器
        ResourceReader reader = readerHolder.getReader(definition.getFormat());
        if (reader == null) {
            throw new IllegalStateException("resource reader not found. format: " + definition.getFormat());
        }

        this.identifier = GetterBuilder.createIdGetter(definition.getClz());
        this.indexGetters = GetterBuilder.createIndexGetters(definition.getClz());
        this.initMethod = findMethodByAnno(definition, Init.class);
        this.postInitMethod = findMethodByAnno(definition, PostInit.class);

        Set<InjectDefinition> injects = definition.getStaticInjects();
        for (InjectDefinition injectDefinition : injects) {
            Field field = injectDefinition.getField();
            Object injectValue = injectDefinition.getValue(applicationContext);
            try {
                field.set(null, injectValue);
            } catch (IllegalAccessException e) {
                FormattingTuple message = MessageFormatter.format("无法注入静态资源[{}]的[{}]属性值", definition.getClz().getName(), injectDefinition.getField().getName());
                throw new IllegalStateException(message.getMessage());
            }
        }

        // 加载静态资源
        load();
    }

    /**
     * 加载静态资源
     */
    private void load() {
        StorageData<K, V> data = new StorageData<>();
        // 加载静态资源
        this.load(data);
        this.data = data;
        // 通知监听器
        notifyChange();
    }

    private void notifyChange() {
        // 通知监听器
        this.setChanged();
        this.notifyObservers();
    }

    private boolean load(StorageData<K, V> data) {
        isReady();

        boolean isSuccess = true;
        String location = resourceDefinition.getLocation();
        String format = resourceDefinition.getFormat();
        ResourceReader reader = readerHolder.getReader(format);
        Iterator<V> readResource = readResource(location, reader);
        while (readResource.hasNext()) {
            V obj = readResource.next();
            injectField(obj);
            if (put(data, obj) != null) {
                FormattingTuple message = MessageFormatter.format("[{}]资源[{}]的唯一标识重复", getClz(), getResourceStr(obj));
                throw new IllegalStateException(message.getMessage());
            }
        }

        if (!init(data.values)) {
            isSuccess = false;
        }

        sortIndex(data);

        return isSuccess;
    }

    // 处理自定义排序规则的静态资源，在@Index处自定义排序规则
    private void sortIndex(StorageData<K, V> data) {
        // 对排序索引进行排序
        for (Map.Entry<String, Map<Object, List<V>>> entry : data.indexs.entrySet()) {
            String key = entry.getKey();
            IndexGetter getter = indexGetters.get(key);
            // 判断是否有自定义比较器
            if (getter.hasComparator()) {
                for (List<V> values : entry.getValue().values()) {
                    Collections.sort(values, getter.getComparator());
                }
            }
        }
    }

    private boolean init(Map<K, V> resources) {

        boolean isSuccess = true;
        if (initMethod == null) {
            return isSuccess;
        }
        for (Map.Entry<K, V> entry : resources.entrySet()) {
            try {
                initMethod.invoke(entry.getValue(), null);
            } catch (Exception e) {
                FormattingTuple message = MessageFormatter.format("初始化资源失败, id:{}, value:{}", entry.getKey(), entry.getValue());
                throw new IllegalStateException(message.getMessage());
            }
        }

        return isSuccess;
    }

    private String getResourceStr(Object obj) {
        if (obj == null) {
            return "null";
        }
        try {
            return JSONChange.objToJson(obj);
        } catch (JsonProcessingException e) {
            K key = (K) identifier.getValue(obj);
            return key.toString();
        }
    }

    private V put(StorageData<K, V> data, V value) {

        K key = (K) identifier.getValue(value);
        if (key == null) {
            FormattingTuple message = MessageFormatter.format("静态资源[{}][{}]存在标志属性为null的配置项", getClz().getName());
            throw new IllegalStateException(message.getMessage());
        }

        V result = data.values.put(key, value);
        // 索引处理
        for (IndexGetter getter : indexGetters.values()) {
            String name = getter.getName();
            Object indexKey = getter.getValue(value);
            if (getter.isUnique()) {
                Map<Object, V> index = loadUniqueIndex(data, name);
                if (index.put(indexKey, value) != null) {
                    FormattingTuple message = MessageFormatter.format("[{}]资源的唯一索引[{}]的值[{}]重复", new Object[]{getClz().getName(), name, indexKey});
                    throw new IllegalStateException(message.getMessage());
                }
            } else {
                List<V> index = loadListIndex(data, name, indexKey);
                index.add(value);
            }
        }

        return result;
    }

    private List<V> loadListIndex(StorageData<K, V> data, String name, Object key) {
        Map<Object, List<V>> index = loadListIndex(data, name);
        if (index.containsKey(key)) {
            return index.get(key);
        }

        List<V> result = new ArrayList<>();
        index.put(key, result);
        return result;
    }

    private Map<Object, List<V>> loadListIndex(StorageData<K, V> data, String name) {
        if (data.indexs.containsKey(name)) {
            return data.indexs.get(name);
        }

        Map<Object, List<V>> result = new HashMap<>();
        data.indexs.put(name, result);
        return result;
    }

    /**
     * 从data.uniques中取出key为name的存储容器
     *
     * @param data
     * @param name
     * @return
     */
    private Map<Object, V> loadUniqueIndex(StorageData<K, V> data, String name) {
        if (data.uniques.containsKey(name)) {
            return data.uniques.get(name);
        }

        Map<Object, V> result = new HashMap<>();
        data.uniques.put(name, result);
        return result;
    }

    /**
     * 注入@ResoureInject注解的Field
     *
     * @param obj
     */
    private void injectField(V obj) {
        Set<InjectDefinition> injects = resourceDefinition.getInjects();
        for (InjectDefinition injectDefinition : injects) {
            Field field = injectDefinition.getField();
            Object value = injectDefinition.getValue(applicationContext);
            try {
                field.set(obj, value);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private Iterator<V> readResource(String location, ResourceReader reader) {

        boolean cache = false;
        InputStream inputStream = null;
        try {
            if (resourceDefinition.getCacheKey() != null) {
                inputStream = StorageManager.getFromCache(resourceDefinition.getCacheKey());
                if (inputStream != null) {
                    cache = true;
                } else {
                    return null;
                }
            } else {
                Resource resource = applicationContext.getResource(location);
                inputStream = ((Resource) resource).getInputStream();
            }
            return reader.read(inputStream, getClz());
        } catch (IOException e) {
            FormattingTuple message = MessageFormatter.format("静态资源[{}][{}]读取失败", getClz().getName(), location);
            throw new IllegalStateException(message.getMessage());
        } finally {
            if (!cache && inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 判断是否已经初始化完成
     *
     * @return
     */
    private void isReady() {
        if (!isInitialized()) {
            throw new RuntimeException("未初始化完成");
        }
    }

    private Method findMethodByAnno(ResourceDefinition definition, Class<? extends Annotation> annotationClass) {
        Method[] methods = ReflectionUtility.getDeclaredGetMethodsWith(definition.getClz(), annotationClass);
        if (methods.length > 0) {
            Method method = methods[0];
            method.setAccessible(true);
            return method;
        }
        return null;
    }

    public boolean postInit(Map<?, ?> resources) {

        boolean isSuccess = true;
        if (postInitMethod == null) {
            return isSuccess;
        }
        for (Map.Entry entry : resources.entrySet()) {
            try {
                postInitMethod.invoke(entry.getValue(), null);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        return isSuccess;
    }

    public StorageData<K, V> getData() {
        return data;
    }

    public boolean isInitialized() {
        return initialized;
    }

    private Class<V> getClz() {
        return (Class<V>) resourceDefinition.getClz();
    }
}
