package com.nuofankj.springdemo.utility;

import org.springframework.util.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public abstract class ReflectionUtility extends ReflectionUtils {

    /**
     * 类似ReflectionUtils.doWithFields，只是该方法不会递归检查父类的域
     *
     * @param clz
     * @param fc
     * @param fieldFilter
     */
    public static void doWithDeclaredFields(Class<?> clz, FieldCallback fc, FieldFilter fieldFilter) {

        if (clz == null || clz == Object.class) {
            return;
        }
        Field[] fields = clz.getDeclaredFields();
        for (Field field : fields) {
            if (fieldFilter != null && !fieldFilter.matches(field)) {
                continue;
            }
            try {
                // 回调
                fc.doWith(field);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 取出对应class所有加了指定注解声明的属性
     *
     * @param clz
     * @param annotationClass
     * @return
     */
    public static Field[] getDeclaredFieldSWith(Class<?> clz, Class<? extends Annotation> annotationClass) {

        List<Field> fields = new ArrayList<>();
        for (Field field : clz.getDeclaredFields()) {
            if (field.isAnnotationPresent(annotationClass)) {
                fields.add(field);
            }
        }

        return fields.toArray(new Field[0]);
    }

    /**
     * 取出对应class所有加了指定注解声明的方法
     *
     * @param clz
     * @param annotationClass
     * @return
     */
    public static Method[] getDeclaredGetMethodsWith(Class<?> clz, Class<? extends Annotation> annotationClass) {

        List<Method> methods = new ArrayList<>();
        for (Method method : clz.getDeclaredMethods()) {
            if (method.getAnnotation(annotationClass) == null) {
                continue;
            }
            if (method.getReturnType().equals(void.class)) {
                continue;
            }
            if (method.getParameterTypes().length > 0) {
                continue;
            }
            methods.add(method);
        }
        return methods.toArray(new Method[0]);
    }

    /**
     * 获得第一个使用指定注解声明的属性
     *
     * @param clz
     * @param annotationClass
     * @return
     */
    public static Field getFirstDeclaredFieldWith(Class<?> clz, Class<? extends Annotation> annotationClass) {
        for (Field field : clz.getDeclaredFields()) {
            if (field.isAnnotationPresent(annotationClass)) {
                return field;
            }
        }

        return null;
    }
}
