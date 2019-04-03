package com.nuofankj.springdemo.resource.bean;

import com.nuofankj.springdemo.support.ClassMapper;

@ClassMapper
public class CurrencyReward extends AbstractReward {

    private CurrencyType currencyType;
    private long value;

    @Override
    void print() {
        System.out.println("this is " + this.getClass().getSimpleName());
    }

    @Override
    public String toString() {
        return "CurrencyReward{" +
                "currencyType=" + currencyType +
                ", value=" + value +
                '}';
    }
}
