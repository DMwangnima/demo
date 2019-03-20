package com.nuofankj.springdemo.resource;

import com.nuofankj.springdemo.anno.Id;
import com.nuofankj.springdemo.anno.Resource;

@Resource
public class TestResource {

    @Id
    private int id;

    private String testStr;

    @Override
    public String toString() {
        return "TestResource{" +
                "id=" + id +
                ", testStr='" + testStr + '\'' +
                '}';
    }
}
