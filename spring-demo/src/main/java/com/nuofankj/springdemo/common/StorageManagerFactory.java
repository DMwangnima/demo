package com.nuofankj.springdemo.common;

import com.nuofankj.springdemo.schema.ResourceDefinition;
import com.nuofankj.springdemo.support.TypeEnum;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.convert.ConversionService;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class StorageManagerFactory implements FactoryBean<StorageManager>, ApplicationContextAware {

    private ApplicationContext applicationContext;

    // 静态资源列表
    private List<ResourceDefinition> definitionList;
    @Autowired
    private ConversionService conversionService;

    public void setDefinitionList(List<ResourceDefinition> definitionList) {
        this.definitionList = definitionList;
    }

    @Override
    public StorageManager getObject() throws Exception {

        // FIXME: 2019/3/21 枚举转换器注入
        TypeEnum.conversionService = conversionService;

        // 使用AutowireCapableBeanFactory构造StorageManager对象
        StorageManager result = this.applicationContext.getAutowireCapableBeanFactory().createBean(StorageManager.class);
        Collections.sort(definitionList, new Comparator<ResourceDefinition>() {
            @Override
            public int compare(ResourceDefinition o1, ResourceDefinition o2) {
                return o1.getOrder() > o2.getOrder() ? 1 : o1.getOrder() < o2.getOrder() ? -1 : 0;
            }
        });

        for (ResourceDefinition definition : definitionList) {
            // 初始化ResourceDefinition
            result.initialize(definition);
        }
        // 初始化StorageManager
        result.postInit();
        return result;
    }

    @Override
    public Class<?> getObjectType() {
        return StorageManager.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
