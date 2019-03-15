package com.nuofankj.springdemo.schema;

import com.nuofankj.springdemo.anno.Static;
import com.nuofankj.springdemo.common.Storage;

import java.lang.reflect.Field;
import java.util.Observable;
import java.util.Observer;

public class StaticObserver implements Observer {

    // 注入目标
    private Object target;
    // 注入属性
    private Field field;
    // 注解
    private Static anno;
    // 资源主键
    private Object key;

    public StaticObserver(Object target, Field field, Static anno, Object key) {
        this.target = target;
        this.field = field;
        this.anno = anno;
        this.key = key;
    }

    public Object getTarget() {
        return target;
    }

    public Field getField() {
        return field;
    }

    public Static getAnno() {
        return anno;
    }

    public Object getKey() {
        return key;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (!(o instanceof Storage)) {
            return;
        }

    }

    // 注入资源实例
    private void inject(Storage o) {
        Object value = o.get(key, false);
    }
}
