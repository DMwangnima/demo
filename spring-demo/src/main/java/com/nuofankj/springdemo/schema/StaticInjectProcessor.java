package com.nuofankj.springdemo.schema;

import com.nuofankj.springdemo.common.StorageManager;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.core.Ordered;
import org.springframework.core.convert.ConversionService;

public class StaticInjectProcessor extends InstantiationAwareBeanPostProcessorAdapter implements Ordered {

    @Autowired
    private StorageManager storageManager;
    @Autowired
    private ConversionService conversionService;

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {

        return false;
    }

    @Override
    public int getOrder() {
        return Integer.MAX_VALUE;
    }
}
