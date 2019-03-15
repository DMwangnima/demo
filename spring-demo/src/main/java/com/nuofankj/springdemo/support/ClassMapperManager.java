package com.nuofankj.springdemo.support;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * 存放所有被spring容器管理的类的容器
 */
@Component
public class ClassMapperManager {

    // 存放所有被spring容器管理的类，key为类名
    private Map<String, Class> simpleNameToClass = new HashMap<>();
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private DefaultListableBeanFactory beanFactory;

    @PostConstruct
    private void initialize() {
        String[] beanNamesFortType = applicationContext.getBeanDefinitionNames();
        for (String beanName : beanNamesFortType) {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);
            String beanClassName = beanDefinition.getBeanClassName();
            if (!StringUtils.isEmpty(beanClassName)) {
                Class<?> beanClass = null;
                try {
                    beanClass = Class.forName(beanClassName);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                if (beanClass.isAnnotationPresent(ClassMapper.class) && simpleNameToClass.putIfAbsent(beanClass.getSimpleName(), beanClass) != null) {
                    throw new RuntimeException("Duplicate Class Mapper = " + beanClassName);
                }
            }
        }
        TypeEnum.classMapperManager = this;
    }

    public Class getMapperClass(String name) {
        return simpleNameToClass.get(name);
    }
}
