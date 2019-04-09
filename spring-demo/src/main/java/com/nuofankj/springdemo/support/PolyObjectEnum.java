package com.nuofankj.springdemo.support;

import com.nuofankj.springdemo.resource.bean.CurrencyConsume;

public enum PolyObjectEnum {

    CURRENCY_CONSUME("CurrencyConsume", CurrencyConsume.class),
    ;

    private String className;

    private Class clz;

    PolyObjectEnum(String className, Class clz) {
        this.className = className;
        this.clz = clz;
    }

    /**
     * 根据类名取出类
     *
     * @param className
     * @return
     */
    public static Class<?> getPolyObject(String className) {

        for (PolyObjectEnum polyObjectEnum : values()) {
            if (polyObjectEnum.className.equals(className)) {
                return polyObjectEnum.clz;
            }
        }

        return null;
    }
}
