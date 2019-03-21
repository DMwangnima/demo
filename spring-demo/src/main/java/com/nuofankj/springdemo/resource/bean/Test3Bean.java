package com.nuofankj.springdemo.resource.bean;

public class Test3Bean {

    private int a1;

    private int a2;

    @Override
    public String toString() {
        return "Test3Bean{" +
                "a1=" + a1 +
                ", a2=" + a2 +
                '}';
    }

    public void setA1(int a1) {
        this.a1 = a1;
    }

    public void setA2(int a2) {
        this.a2 = a2;
    }
}
