package com.example.demo.processor;

import com.example.demo.bean.TestBeanA;
import com.example.demo.bean.TestBeanB;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * 完成bean实例化、配置以及其他初始化方法前后要添加一些自己逻辑处理则要实现接口BeanPostProcessor
 */
@Component
public class MyBeanPostProcessor implements BeanPostProcessor {

    public MyBeanPostProcessor() {
        super();
        System.out.println("【MyBeanPostProcessor】BeanPostProcessor实现类的构造函数");
    }

    // 实例化、依赖注入完毕，在调用显示的初始化之前完成一些定制的业务
    @Override
    public Object postProcessAfterInitialization(Object arg0, String arg1)
            throws BeansException {

        if (arg0.getClass() == TestBeanA.class || arg0.getClass() == TestBeanB.class) {
            System.out
                    .println("【BeanPostProcessor.postProcessAfterInitialization】来自MyBeanPostProcessor，beanName:" + arg1);
        }
        return arg0;
    }

    // 实例化、依赖注入、初始化后完成一些定制的业务
    @Override
    public Object postProcessBeforeInitialization(Object arg0, String arg1)
            throws BeansException {

        if (arg0.getClass() == TestBeanA.class || arg0.getClass() == TestBeanB.class) {
            System.out
                    .println("【BeanPostProcessor.postProcessBeforeInitialization】来自MyBeanPostProcessor，beanName:" + arg1);
        }
        return arg0;
    }
}