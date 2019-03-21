package com.nuofankj.springdemo.resource;

import com.nuofankj.springdemo.support.StringToObjectMapper;
import com.nuofankj.springdemo.utility.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.ConversionService;

public abstract class AbstractGlobalConfig<T> {

    // 值
    private transient volatile T value;

    // 获取配置值
    public T getValue() {
        if (value != null) {
            return value;
        }
        synchronized (this) {
            if (value == null) {
                initializeValue();
            }
        }

        return value;
    }

    // FIXME: 2019/3/15 JSONChange工具感觉不靠谱，如果靠谱JSON那里可以优化下
    private void initializeValue() {
        switch (getType()) {
            case NORMAL:
                value = (T) getConversionService().convert(getContent(), getClz());
                break;
            case JSON:
                if (StringUtils.startsWith(getContent(), "[")) {
                    if (!getClz().isArray()) {
                        value = (T) JsonUtils.string2Object(getContent(), getClz());
                    } else {
                        value = (T) JsonUtils.string2Array(getContent(), getClz());
                    }
                } else {
                    value = (T) JsonUtils.string2Object(getContent(), getClz());
                }
                break;
            case ARRAY:
                value = (T) JsonUtils.string2Array(getContent(), getClz());
                break;
            case PARSER:
                if (StringToObjectMapper.class.isAssignableFrom(getClz())) {
                    StringToObjectMapper stringToObjectMapper = null;
                    try {
                        stringToObjectMapper = (StringToObjectMapper) getClz().newInstance();
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    stringToObjectMapper.parse(getContent());
                    value = (T) stringToObjectMapper;
                    break;
                }
            default:
                break;
        }
    }

    public abstract String getId();

    public abstract ConversionService getConversionService();

    public abstract ConfigValueType getType();

    public abstract String getContent();

    public abstract Class getClz();
}
