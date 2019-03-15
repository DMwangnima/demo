package com.nuofankj.springdemo.resource;

import com.nuofankj.springdemo.anno.Id;
import com.nuofankj.springdemo.anno.Resource;
import com.nuofankj.springdemo.anno.ResourceInject;
import org.springframework.core.convert.ConversionService;

@Resource
public class ConfigValue<T> extends AbstractGlobalConfig<T> {

    @Id
    private String id;
    @ResourceInject
    private ConversionService conversionService;
    // 配置值
    private String content;
    // 配置值格式类型
    private ConfigValueType type;
    // 值类型参数
    private Class clz;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public ConversionService getConversionService() {
        return conversionService;
    }

    @Override
    public ConfigValueType getType() {
        return type;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public Class getClz() {
        return null;
    }
}
