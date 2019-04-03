package com.nuofankj.springdemo.resource.bean;

import com.nuofankj.springdemo.anno.ParseIgnore;
import com.nuofankj.springdemo.support.PolyObjectMapper;

public class Test2Bean implements PolyObjectMapper {

    private int a1;

    private int a2;

    public int getA1() {
        return a1;
    }

    public void setA1(int a1) {
        this.a1 = a1;
    }

    public int getA2() {
        return a2;
    }

    public void setA2(int a2) {
        this.a2 = a2;
    }

    @Override
    public String toString() {
        return "Test2Bean{" +
                "a1=" + a1 +
                ", a2=" + a2 +
                '}';
    }
}
