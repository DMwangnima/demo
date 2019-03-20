package com.nuofankj.springdemo.resource.bean;

import com.nuofankj.springdemo.support.PolyObjectMapper;

public class Test2Bean implements PolyObjectMapper {

    private int a1;

    private int a2;

    @Override
    public String toString() {
        return "Test2Bean{" +
                "a1=" + a1 +
                ", a2=" + a2 +
                '}';
    }
}
