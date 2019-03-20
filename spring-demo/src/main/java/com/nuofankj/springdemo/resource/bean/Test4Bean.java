package com.nuofankj.springdemo.resource.bean;

import com.nuofankj.springdemo.support.PolyObjectMapper;

import java.util.List;
import java.util.Map;

public class Test4Bean implements PolyObjectMapper {

    private Map<Integer, List<Integer>> testMap;

    @Override
    public String toString() {
        return "Test4Bean{" +
                "testMap=" + testMap +
                '}';
    }
}
