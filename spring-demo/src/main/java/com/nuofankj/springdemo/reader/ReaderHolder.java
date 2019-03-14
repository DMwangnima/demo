package com.nuofankj.springdemo.reader;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.annotation.PostConstruct;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 资源转换器容器
 * FIXME: 不是组件，也暂未被注册
 */
public class ReaderHolder implements ApplicationContextAware {

    private ApplicationContext applicationContext;
    // 存放所有静态资源转换器
    private ConcurrentHashMap<String, ResourceReader> readers = new ConcurrentHashMap<>();

    /**
     * 初始化，扫描Spring容器将所有实现了ResourceReader的类存入reades中
     */
    @PostConstruct
    protected void initialize() {

        for (String name : this.applicationContext.getBeanNamesForType(ResourceReader.class)) {
            ResourceReader reader = this.applicationContext.getBean(name, ResourceReader.class);
            register(reader);
        }
    }

    private ResourceReader register(ResourceReader reader) {

        return readers.putIfAbsent(reader.getFormat(), reader);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * 获取指定格式的ResourceReader
     *
     * @param format
     * @return
     */
    public ResourceReader getReader(String format) {

        return readers.get(format);
    }
}
