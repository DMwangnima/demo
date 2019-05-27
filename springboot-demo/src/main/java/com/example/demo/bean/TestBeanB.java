package com.example.demo.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestBeanB {

    @Autowired
    private TestBeanA testBeanA;

    public TestBeanB() {
        System.out.println("【TestBeanB.默认构造器】");
    }
}
