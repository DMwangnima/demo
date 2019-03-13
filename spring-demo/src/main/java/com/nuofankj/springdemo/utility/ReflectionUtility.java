package com.nuofankj.springdemo.utility;

import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

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
}
