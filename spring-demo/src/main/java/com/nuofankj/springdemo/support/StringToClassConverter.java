package com.nuofankj.springdemo.support;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.convert.converter.Converter;

public class StringToClassConverter implements Converter<String, Class>, ApplicationContextAware {

    private ClassMapperManager classMapperManager;
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        this.classMapperManager = applicationContext.getBean(ClassMapperManager.class);
    }

    @Override
    public Class convert(String source) {
        if (!StringUtils.contains(source, ".")) {
            Class mapperClass = classMapperManager.getMapperClass(source);
            if (mapperClass != null) {
                return mapperClass;
            }
            if (!source.startsWith("[")) {
                source = "java.lang." + source;
            }
        }
        ClassLoader loader = applicationContext.getClassLoader();
        try {
            return Class.forName(source, true, loader);
        } catch (ClassNotFoundException e) {
            FormattingTuple message = MessageFormatter.format("无法将字符串[{}]转换为Class", source);
            throw new IllegalStateException(message.getMessage());
        }
    }
}
