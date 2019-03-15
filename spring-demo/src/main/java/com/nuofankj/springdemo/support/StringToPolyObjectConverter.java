package com.nuofankj.springdemo.support;

import org.springframework.core.ResolvableType;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.ConditionalGenericConverter;

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class StringToPolyObjectConverter implements ConditionalGenericConverter {
    @Override
    public boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType) {

        if (sourceType.getType() != String.class) {
            return false;
        }

        if (StringToPolyObjectConverter.isPolyObjectType(targetType)) {
            return true;
        }

        return PolyObjectMapper.class.isAssignableFrom(targetType.getType()) || PolyObjectMapper[].class.isAssignableFrom(targetType.getType());
    }

    private static boolean isPolyObjectType(TypeDescriptor typeDescriptor) {

        if (typeDescriptor.getResolvableType() == null) {
            return false;
        }

        ResolvableType[] resolvableTypes = typeDescriptor.getResolvableType().getGenerics();
        for (ResolvableType resolvableType : resolvableTypes) {
            if (PolyObjectMapper.class.isAssignableFrom((Class<?>) resolvableType.getType())) {
                return true;
            }
        }

        return false;
    }

    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {

        Set<ConvertiblePair> convertiblePairs = new HashSet<>();
        convertiblePairs.add(new ConvertiblePair(String.class, PolyObjectMapper.class));
        convertiblePairs.add(new ConvertiblePair(String.class, PolyObjectMapper[].class));
        convertiblePairs.add(new ConvertiblePair(String.class, List.class));
        convertiblePairs.add(new ConvertiblePair(String.class, Map.class));

        return convertiblePairs;
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {

        String content = (String) source;
        Object obj = null;
        try {
            if (targetType.getType().isInterface()) {
                obj = TypeEnum.doAnalyse(targetType, content);

            } else {
                Class<?> type = targetType.getType();
                obj = TypeEnum.doAnalyse(targetType, content);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return obj;
    }
}
