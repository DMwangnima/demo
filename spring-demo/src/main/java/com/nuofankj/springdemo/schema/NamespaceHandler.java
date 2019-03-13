package com.nuofankj.springdemo.schema;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * 静态资源 命名空间注册器
 */
public class NamespaceHandler extends NamespaceHandlerSupport {
    @Override
    public void init() {
        registerBeanDefinitionParser(SchemaNames.CONFIG_ELEMENT, new ConfigDefinitionParser());
    }
}
