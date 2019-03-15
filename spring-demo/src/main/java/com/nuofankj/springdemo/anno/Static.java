package com.nuofankj.springdemo.anno;

import java.beans.PropertyEditor;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 静态资源注入注释
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Static {

    // 标志值
    String value() default "";

    // 加载解析的方法名字
    String initializeMethodName() default "";

    // 唯一标志转换器声明
    Class<? extends PropertyEditor> converter() default PropertyEditor.class;

    // 注入值是否必须
    boolean required() default true;

    // 标志值是否唯一
    boolean unique() default false;

    // 默认值
    String default_() default "";
}
