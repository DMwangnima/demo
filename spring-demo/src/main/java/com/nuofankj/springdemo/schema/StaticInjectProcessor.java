package com.nuofankj.springdemo.schema;

import com.nuofankj.springdemo.anno.Id;
import com.nuofankj.springdemo.anno.Static;
import com.nuofankj.springdemo.common.Storage;
import com.nuofankj.springdemo.common.StorageLong;
import com.nuofankj.springdemo.common.StorageManager;
import com.nuofankj.springdemo.utility.ReflectionUtility;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.core.Ordered;
import org.springframework.core.convert.ConversionService;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.*;

/**
 * 静态注入处理器，负责完成@Static声明的资源的注入工作
 */
public class StaticInjectProcessor extends InstantiationAwareBeanPostProcessorAdapter implements Ordered {

    @Autowired
    private StorageManager storageManager;
    @Autowired
    private ConversionService conversionService;

    private enum InjectType {
        // 特殊存储空间
        STORAGELONG,
        // 存储空间
        STORAGE,
        // 实例
        INSTANCE,
        ;
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        ReflectionUtils.doWithFields(bean.getClass(), new ReflectionUtils.FieldCallback() {
            @Override
            public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
                Static anno = field.getAnnotation(Static.class);
                if (anno == null) {
                    return;
                }
                InjectType type = getFieldInjectType(field);
                switch (type) {
                    case STORAGELONG:
                        injectStorageLong(bean, field, anno);
                        break;
                    case STORAGE:
                        injectStorage(bean, field, anno);
                        break;
                    case INSTANCE:
                        injectInstance(bean, field, anno);
                        break;
                }
            }
        });

        return super.postProcessAfterInstantiation(bean, beanName);
    }

    /**
     * 注入静态资源实例
     *
     * @param bean
     * @param field
     * @param anno
     */
    private void injectInstance(Object bean, Field field, Static anno) {

        // 构造主键
        Class<?> clz = getIdType(field.getType());
        Object key = null;
        if (!anno.value().equals("")) {
            key = conversionService.convert(anno.value(), clz);
        } else {
            key = conversionService.convert(field.getName(), clz);
        }

        Storage storage = storageManager.getStorage(clz);
        // 观察者模式[添加监听器]
        StaticObserver observer = new StaticObserver(bean, field, anno, key);
        storage.addObserver(observer);
        // FIXME: 2019/3/15 这段是什么鬼东西
        Object instance = storage.get(key, false);
        if (anno.required() && instance == null) {
            if (!anno.default_().equals("")) {
                try {
                    instance = field.getType().newInstance();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                String[] defaultEntries = anno.default_().split("[,\\s]+");
                for (String entry : defaultEntries) {
                    if (StringUtils.isNotBlank(entry)) {
                        int pos = entry.indexOf("=");
                        if (pos != -1) {
                            String entryKey = entry.substring(0, pos).trim();
                            String entryValue = entry.substring(pos + 1).trim();
                            try {
                                BeanUtils.setProperty(instance, entryKey, entryValue);
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }

        inject(bean, field, instance);
        initialize(bean, field, anno);
    }

    /**
     * 初始化@Static中的initializeMethodName函数
     *
     * @param bean
     * @param field
     * @param anno
     */
    private void initialize(Object bean, Field field, Static anno) {

        try {
            if (!"".equals(anno.initializeMethodName())) {
                Method loadMethod = bean.getClass().getDeclaredMethod(anno.initializeMethodName());
                loadMethod.setAccessible(true);
                loadMethod.invoke(bean, null);
            }
        } catch (Exception e) {
            throw new RuntimeException(bean.getClass().getSimpleName() + "_" + field + ",初始化异常", e);
        }

    }

    // 取得指定class中@Id注解的属性类型
    private Class<?> getIdType(Class<?> clz) {
        Field field = ReflectionUtility.getFirstDeclaredFieldWith(clz, Id.class);
        return field.getType();
    }

    /**
     * 给@Static的属性并且是Storage类型的做注入,格式
     *
     * @param bean
     * @param field
     * @param anno
     */
    private void injectStorage(Object bean, Field field, Static anno) {
        Type type = field.getGenericType();
        // 泛型类型
        if (!(type instanceof ParameterizedType)) {
            throw new RuntimeException("类型声明不正确");
        }

        Type[] types = ((ParameterizedType) type).getActualTypeArguments();
        if (!(types[1] instanceof Class)) {
            throw new RuntimeException("类型声明不正确");
        }

        Class clz = (Class) types[1];
        Storage storage = storageManager.getStorage(clz);
        // 观察者模式[添加监听器]
        StorageObserver observer = new StorageObserver(bean, field, anno);
        storage.addObserver(observer);

        boolean required = anno.required();
        if (required && storage == null) {
            FormattingTuple message = MessageFormatter.format("静态资源[{}]不存在", clz);
            throw new RuntimeException(message.getMessage());
        }

        inject(bean, field, storage);
    }

    /**
     * 给@Static的属性并且是StorageLong类型的做注入，格式: @Static StorageLong<TestResource></>
     *
     * @param bean
     * @param field
     * @param anno
     */
    private void injectStorageLong(Object bean, Field field, Static anno) {

        Type type = field.getGenericType();
        // 泛型类型
        if (!(type instanceof ParameterizedType)) {
            throw new RuntimeException("类型声明不正确");
        }

        Type[] types = ((ParameterizedType) type).getActualTypeArguments();
        if (!(types[0] instanceof Class)) {
            throw new RuntimeException("类型声明不正确");
        }

        Class clz = (Class) types[0];
        Storage storage = storageManager.getStorage(clz);
        // 观察者模式[添加监听器]
        StorageObserver observer = new StorageObserver(bean, field, anno);
        storage.addObserver(observer);

        boolean required = anno.required();
        if (required && storage == null) {
            FormattingTuple message = MessageFormatter.format("静态资源[{}]不存在", clz);
            throw new RuntimeException(message.getMessage());
        }

        inject(bean, field, storage);
    }

    /**
     * 注入属性值
     *
     * @param bean
     * @param field
     * @param value
     */
    private void inject(Object bean, Field field, Object value) {
        ReflectionUtils.makeAccessible(field);
        try {
            field.set(bean, value);
        } catch (IllegalAccessException e) {
            FormattingTuple message = MessageFormatter.format("属性[{}]注入失败", field);
            throw new RuntimeException(message.getMessage());
        }
    }

    /**
     * 取得Field的注入类型
     *
     * @param field
     * @return
     */
    private InjectType getFieldInjectType(Field field) {

        if (field.getType().equals(StorageLong.class)) {
            return InjectType.STORAGELONG;
        }

        if (field.getType().equals(Storage.class)) {
            return InjectType.STORAGE;
        }

        return InjectType.INSTANCE;
    }

    @Override
    public int getOrder() {
        return Integer.MAX_VALUE;
    }
}
