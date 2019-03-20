package com.nuofankj.springdemo.resource.bean;

import com.nuofankj.springdemo.support.PolyObjectMapper;

public class Test2BeanArray implements PolyObjectMapper {

    private int a;

    private int b;

    private int c;

    @Override
    public String toString() {
        return "Test2BeanArray{" +
                "a=" + a +
                ", b=" + b +
                ", c=" + c +
                '}';
    }
}
