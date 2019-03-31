package com.nuofankj.eventmechanism.support;

import com.nuofankj.eventmechanism.event.IEvent;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xifanxiaxue
 * @date 3/31/19
 * @desc 事件容器
 */
public class EventContainer {

  private static Map<Class<IEvent>, List<MethodInfo>> eventListMap = new HashMap<>();

  public static void addEventToMap(Class clz, Method method, Object obj) {

    List<MethodInfo> methodInfos = eventListMap.get(clz);
    if (methodInfos == null) {
      methodInfos = new ArrayList<>();
      eventListMap.put(clz, methodInfos);
    }

    methodInfos.add(MethodInfo.valueOf(method, obj));
  }

  public static void submit(Class clz) {

    List<MethodInfo> methodInfos = eventListMap.get(clz);
    if (methodInfos == null) {
      return;
    }

    for (MethodInfo methodInfo : methodInfos) {
      Method method = methodInfo.getMethod();
      try {
        method.setAccessible(true);
        method.invoke(methodInfo.getObj());
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      } catch (InvocationTargetException e) {
        e.printStackTrace();
      }
    }
  }
}
