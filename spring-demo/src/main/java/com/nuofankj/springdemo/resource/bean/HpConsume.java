package com.nuofankj.springdemo.resource.bean;

import com.nuofankj.springdemo.support.ClassMapper;

@ClassMapper
public class HpConsume extends AbstractConsume {

    private int hp;

    @Override
    void print() {
        System.out.println("this is " + this.getClass().getSimpleName());
    }

    @Override
    public String toString() {
        return "HpConsume{" +
                "hp=" + hp +
                '}';
    }
}
