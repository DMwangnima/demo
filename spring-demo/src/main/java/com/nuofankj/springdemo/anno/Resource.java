package com.nuofankj.springdemo.anno;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Resource {

    // 资源位置
    String value() default "";

    // 是否缓存
    String acahe() default "";

    // 初始化优先级
    int order() default 10;
}
