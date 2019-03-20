package com.nuofankj.springdemo.resource.bean;

import com.nuofankj.springdemo.support.PolyObjectMapper;

public class ConsumeDef implements PolyObjectMapper {

    private ConsumeType type;

    private String value;

    @Override
    public String toString() {
        return "ConsumeDef{" +
                "type=" + type +
                ", value='" + value + '\'' +
                '}';
    }
}
