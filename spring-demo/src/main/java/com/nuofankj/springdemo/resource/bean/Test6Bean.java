package com.nuofankj.springdemo.resource.bean;

import com.nuofankj.springdemo.support.PolyObjectMapper;

public class Test6Bean implements PolyObjectMapper {

    private CurrencyType currencyType;

    private int a;

    @Override
    public String toString() {
        return "Test6Bean{" +
                "currencyType=" + currencyType +
                ", a=" + a +
                '}';
    }
}
