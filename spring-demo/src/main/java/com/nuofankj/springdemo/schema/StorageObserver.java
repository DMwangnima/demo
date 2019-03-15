package com.nuofankj.springdemo.schema;

import com.nuofankj.springdemo.anno.Static;
import com.nuofankj.springdemo.common.Storage;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Observable;
import java.util.Observer;

public class StorageObserver implements Observer {

    // 注入目标
    private Object target;
    // 注入属性
    private Field field;
    // 注解
    private Static anno;

    public StorageObserver(Object target, Field field, Static anno) {
        this.target = target;
        this.field = field;
        this.anno = anno;
    }

    // 观察者模式[更新数据]
    @Override
    public void update(Observable o, Object arg) {
        if (!(o instanceof Storage)) {
            return;
        }
        if (!"".equals(anno.initializeMethodName())) {
            try {
                Method loadMethod = target.getClass().getDeclaredMethod(anno.initializeMethodName());
                loadMethod.setAccessible(true);
                try {
                    loadMethod.invoke(target, null);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }

        }
    }
}
