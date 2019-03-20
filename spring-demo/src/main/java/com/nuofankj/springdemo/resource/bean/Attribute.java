package com.nuofankj.springdemo.resource.bean;

public class Attribute {

    protected AttributeType type;

    protected long value;

    @Override
    public String toString() {
        return "Attribute{" +
                "type=" + type +
                ", value=" + value +
                '}';
    }
}
