package com.nuofankj.eventmechanism.support;

import java.lang.reflect.Method;

/**
 * @author xifanxiaxue
 * @date 3/31/19
 * @desc
 */
public class MethodInfo {

  public Object obj;
  public Method method;

  public static MethodInfo valueOf(Method method, Object obj) {

    MethodInfo info = new MethodInfo();
    info.method = method;
    info.obj = obj;
    return info;
  }

  public Object getObj() {
    return obj;
  }

  public Method getMethod() {
    return method;
  }
}
