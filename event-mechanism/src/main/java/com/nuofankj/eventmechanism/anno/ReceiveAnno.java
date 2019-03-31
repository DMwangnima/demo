package com.nuofankj.eventmechanism.anno;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author xifanxiaxue
 * @date 3/31/19
 * @desc 接收事件的注解
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface ReceiveAnno {

  // 监听的事件
  Class clz();
}