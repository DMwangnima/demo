package com.nuofankj.springdemo.support;

import com.nuofankj.springdemo.utility.JsonUtils;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.ConditionalGenericConverter;

import java.util.Collections;
import java.util.Set;

public class JsonToArrayConverter implements ConditionalGenericConverter {
    @Override
    public boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType) {
        if (sourceType.getType() != String.class) {
            return false;
        }
        if (!targetType.getType().isArray()) {
            return false;
        }
        return true;
    }

    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        return Collections.singleton(new ConvertiblePair(String.class, Object[].class));
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {

        String content = (String) source;
        if (content == null || content.equals("") || content.equals("null")) {
            return null;
        }
        // 数组形式
        if (!targetType.getElementTypeDescriptor().isPrimitive()) {
            if (!content.startsWith("[")) {
                content = "[" + content + "]";
            }
        }
        Object object = null;
        object = JsonUtils.string2Array(content, targetType.getElementTypeDescriptor().getObjectType());
        return object;
    }
}
