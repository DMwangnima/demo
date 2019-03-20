package com.nuofankj.springdemo.resource.bean;

import com.nuofankj.springdemo.support.ClassMapper;
import com.nuofankj.springdemo.support.PolyObjectMapper;

@ClassMapper
public class OddTest1 implements PolyObjectMapper {

    private int num;

    private int itemId1;

    private int itemId2;

    private int itemId3;

    @Override
    public String toString() {
        return "OddTest1{" +
                "num=" + num +
                ", itemId1=" + itemId1 +
                ", itemId2=" + itemId2 +
                ", itemId3=" + itemId3 +
                '}';
    }
}
