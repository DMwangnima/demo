package com.nuofankj.springdemo.common;

import com.nuofankj.springdemo.schema.ResourceDefinition;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class StorageManager implements ApplicationContextAware {

    private ApplicationContext applicationContext;
    // 静态资源定义
    private ConcurrentHashMap<Class<?>, ResourceDefinition> definitions = new ConcurrentHashMap<>();
    // 资源存储空间
    private ConcurrentHashMap<Class<?>, Storage<?, ?>> storages = new ConcurrentHashMap<>();
    // 缓存文件
    private static ConcurrentHashMap<String, InputStream> caches = new ConcurrentHashMap<>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * 初始化StorageManager
     */
    void postInit() {
        List<ResourceDefinition> definitions = new ArrayList<>(this.definitions.values());
        Collections.sort(definitions, new Comparator<ResourceDefinition>() {
            @Override
            public int compare(ResourceDefinition o1, ResourceDefinition o2) {
                return o1.getOrder() > o2.getOrder() ? 1 : o1.getOrder() < o2.getOrder() ? -1 : 0;
            }
        });

        for (ResourceDefinition resourceDefinition : definitions) {
            Storage<?, ?> storage = getStorage(resourceDefinition.getClass());
            storage.postInit(storage.getData().values);
        }
    }

    private Storage<?, ?> getStorage(Class clz) {
        if (storages.contains(clz)) {
            return storages.get(clz);
        }
        return initializeStorage(clz);
    }

    /**
     * Storage初始化操作
     *
     * @param clz
     * @return
     */
    private Storage initializeStorage(Class clz) {

        ResourceDefinition definition = this.definitions.get(clz);
        if (definition == null) {
            FormattingTuple message = MessageFormatter.format("静态资源[{}]的信息定义不存在，可能是配置确实", clz.getSimpleName());
            throw new IllegalStateException(message.getMessage());
        }

        // 通过唯一标志@Id构造Storage对象
        Getter getter = GetterBuilder.createIdGetter(definition.getClz());
        Class<?> idClz = getter.getClz();
        AutowireCapableBeanFactory beanFactory = this.applicationContext.getAutowireCapableBeanFactory();
        Storage storage = null;
        if (idClz.equals(int.class) || idClz.equals(long.class) || idClz.equals(short.class) || idClz.equals(Short.class) || idClz.equals(Integer.class) || idClz.equals(Long.class)) {
            storage = beanFactory.createBean(StorageLong.class);
        } else {
            storage = beanFactory.createBean(Storage.class);
        }

        // 将构造的Storage放入storages
        Storage prev = storages.putIfAbsent(clz, storage);
        if (prev == null) {
            storage.initialize(definition);
        }
        return prev == null ? storage : prev;
    }

    /**
     * 初始化ResourceDefinition
     *
     * @param definition
     */
    public void initialize(ResourceDefinition definition) {

        Class<?> clz = definition.getClz();
        if (definitions.putIfAbsent(clz, definition) != null) {
            ResourceDefinition prev = definitions.get(clz);
            FormattingTuple message = MessageFormatter.format("类[{}]的资源定义[{}]已经存在", clz, prev);
            throw new IllegalStateException(message.getMessage());
        }
        initializeStorage(clz);
    }

    public static InputStream getFromCache(String key) {
        return caches.get(key);
    }
}
