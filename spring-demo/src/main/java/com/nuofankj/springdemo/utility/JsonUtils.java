package com.nuofankj.springdemo.utility;


import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.ArrayType;
import org.codehaus.jackson.map.type.TypeFactory;
import org.codehaus.jackson.type.JavaType;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

import java.io.IOException;
import java.io.StringWriter;

/**
 * 操作json的封装方法
 * use:jackson
 */
public class JsonUtils {

    private static TypeFactory factory = TypeFactory.defaultInstance();
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T[] string2Array(String content, Class<T> clz) {

        ArrayType type = ArrayType.construct(factory.constructType(clz));
        try {
            return objectMapper.readValue(content, type);
        } catch (IOException e) {
            FormattingTuple message = MessageFormatter.format("将字符串[{}]转换为数组时出现异常", content, e);
            throw new RuntimeException(message.getMessage());
        }
    }

    public static <T> T string2Object(String content, Class<T> clz) {

        JavaType type = factory.constructType(clz);
        try {
            return objectMapper.readValue(content, type);
        } catch (IOException e) {
            FormattingTuple message = MessageFormatter.format("将字符串[{}]转换为对象时出现异常", content, e);
            throw new RuntimeException(message.getMessage());
        }
    }

    public static String object2String(Object obj) {
        StringWriter writer = new StringWriter();
        try {
            objectMapper.writeValue(writer, obj);
        } catch (IOException e) {
            FormattingTuple message = MessageFormatter.format("将对象[{}]转换为字符串出现异常", obj, e);
            throw new RuntimeException(message.getMessage());        }

        return writer.toString();
    }
}