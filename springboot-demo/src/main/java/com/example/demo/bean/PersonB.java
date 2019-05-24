package com.example.demo.bean;

import org.springframework.stereotype.Component;

@Component
public class PersonB {

    public PersonB() {
        System.out.println("【构造器】调用PersonB的构造器实例化");
    }
}
