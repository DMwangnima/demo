package com.example.demo.processor;

import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.stereotype.Component;

import java.beans.PropertyDescriptor;

/**
 * 适配器类，基类是BeanPostProcessor的实现类
 */
@Component
public class MyInstantiationAwareBeanPostProcessor extends
        InstantiationAwareBeanPostProcessorAdapter {

    public MyInstantiationAwareBeanPostProcessor() {
        super();
        System.out
                .println("4、【InstantiationAwareBeanPostProcessorAdapter】实现类的构造函数");
    }

    // 接口方法、实例化Bean之前调用
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out
                .println("7、【InstantiationAwareBeanPostProcessorAdapter】的接口【postProcessBeforeInitialization】，beanName:" + beanName);
        return bean;
    }

    // 接口方法、实例化Bean之后调用
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName)
            throws BeansException {
        System.out
                .println("9、【InstantiationAwareBeanPostProcessorAdapter】的接口【postProcessAfterInitialization】，beanName:" + beanName);
        return bean;
    }

    // 接口方法、设置某个属性时调用
    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues pvs,
                                                    PropertyDescriptor[] pds, Object bean, String beanName)
            throws BeansException {
        System.out
                .println("5、【InstantiationAwareBeanPostProcessorAdapter】的接口【postProcessPropertyValues】，beanName:" + beanName);
        return pvs;
    }
}