package com.nuofankj.springdemo.resource.bean;

import com.nuofankj.springdemo.support.ClassMapper;

@ClassMapper
public class CurrencyConsume extends AbstractConsume {

    private CurrencyType type;

    private long value;

    @Override
    void print() {
        System.out.println("this is " + this.getClass().getSimpleName());
    }

    @Override
    public String toString() {
        return "CurrencyConsume{" +
                "type=" + type +
                ", value=" + value +
                '}';
    }
}
