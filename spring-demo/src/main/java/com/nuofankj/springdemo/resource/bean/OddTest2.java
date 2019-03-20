package com.nuofankj.springdemo.resource.bean;

import com.nuofankj.springdemo.support.ClassMapper;
import com.nuofankj.springdemo.support.PolyObjectMapper;

@ClassMapper
public class OddTest2 implements PolyObjectMapper {

    private int num;

    private int monsterId;

    @Override
    public String toString() {
        return "OddTest2{" +
                "num=" + num +
                ", monsterId=" + monsterId +
                '}';
    }
}
