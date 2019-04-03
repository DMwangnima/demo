package com.nuofankj.springdemo.resource.bean;

import com.nuofankj.springdemo.anno.ParseIgnore;
import com.nuofankj.springdemo.support.PolyObjectMapper;
import com.nuofankj.springdemo.support.SelfParser;

public class Test5Bean implements PolyObjectMapper, SelfParser {

    private int a;

    private int b;

    @ParseIgnore
    private Test2Bean test2Bean;

    @Override
    public String toString() {
        return "Test5Bean{" +
                "a=" + a +
                ", b=" + b +
                ", test2Bean=" + test2Bean +
                '}';
    }

    @Override
    public void doParse() {
        Test2Bean bean = new Test2Bean();
        bean.setA1(a);
        bean.setA2(b);
        this.test2Bean = bean;
    }
}
