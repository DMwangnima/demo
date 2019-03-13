package com.nuofankj.springdemo.schema;

import com.nuofankj.springdemo.anno.Resource;
import com.nuofankj.springdemo.anno.ResourceInject;
import com.nuofankj.springdemo.utility.ReflectionUtility;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ReflectionUtils;

import java.io.File;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

/**
 * 资源定义信息对象
 */
public class ResourceDefinition {

    // 资源类
    private Class<?> clz;
    // 资源路径
    private String location;
    // 资源格式
    private String format;
    // 资源的注入信息
    private Set<InjectDefinition> injects = new HashSet<>();

    private String cacheKey;

    private int order;

    private final String FILE_SPLIT = ".";

    private final String FILE_PATH = File.separator;

    private ReflectionUtils.FieldFilter INJECT_FILTER = new ReflectionUtils.FieldFilter() {
        @Override
        public boolean matches(Field field) {
            if (field.isAnnotationPresent(ResourceInject.class)) {
                return true;
            }
            return false;
        }
    };

    public ResourceDefinition(Class<?> clz, FormatDefinition formatDefinition) {

        this.clz = clz;
        this.format = formatDefinition.getType();
        Resource anno = clz.getAnnotation(Resource.class);
        String name = null;
        if (StringUtils.isBlank(anno.value())) {
            name = clz.getSimpleName();
        } else {
            name = anno.value();
            if (StringUtils.startsWith(name, FILE_PATH)) {
                name = StringUtils.substringAfter(name, FILE_PATH);
            }
        }
        this.location = formatDefinition.getLocation() + FILE_PATH + name + FILE_SPLIT + formatDefinition.getSuffix();
        ReflectionUtility.doWithDeclaredFields(clz, new ReflectionUtils.FieldCallback() {
            @Override
            public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {

                InjectDefinition injectDefinition = new InjectDefinition(field);
                injects.add(injectDefinition);
            }
        }, INJECT_FILTER);

        this.order = anno.order();
    }

}
