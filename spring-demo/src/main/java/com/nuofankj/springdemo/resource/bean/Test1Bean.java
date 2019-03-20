package com.nuofankj.springdemo.resource.bean;

import com.nuofankj.springdemo.support.PolyObjectMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Test1Bean implements PolyObjectMapper {

    private int a;

    private String b;

    private float c;

    private double d;

    private byte e;

    private short f;

    private long g;

    private char h;

    private boolean i;

    private List<Integer> j;

    private Map<Integer, Integer> k;

    private Test2Bean l;

    private int[] m;

    @Override
    public String toString() {
        return "Test1Bean{" +
                "a=" + a +
                ", b='" + b + '\'' +
                ", c=" + c +
                ", d=" + d +
                ", e=" + e +
                ", f=" + f +
                ", g=" + g +
                ", h=" + h +
                ", i=" + i +
                ", j=" + j +
                ", k=" + k +
                ", l=" + l +
                ", m=" + Arrays.toString(m) +
                '}';
    }
}
