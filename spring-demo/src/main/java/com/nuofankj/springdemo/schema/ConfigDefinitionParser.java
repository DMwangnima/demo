package com.nuofankj.springdemo.schema;

import com.nuofankj.springdemo.common.StorageManagerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.beans.factory.xml.AbstractBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;
import org.springframework.util.SystemPropertyUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * 资源配置处理器
 */
public class ConfigDefinitionParser extends AbstractBeanDefinitionParser {

    // 资源搜索分析器，可以用来扫出指定路径下的所有类
    private ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();

    // 类的元数据读取器，可以用它来负责读取类上的注解信息
    private MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(this.resourcePatternResolver);

    // 静态资源注解
    private String annoName = com.nuofankj.springdemo.anno.Resource.class.getName();

    @Override
    protected AbstractBeanDefinition parseInternal(Element element, ParserContext parserContext) {

        BeanDefinitionBuilder factory = BeanDefinitionBuilder.rootBeanDefinition(StorageManagerFactory.class);
        ManagedList<BeanDefinition> resourceBeanList = new ManagedList<>();
        Set<Class> resourceClassList = new HashSet<>();
        NodeList child = element.getChildNodes();
        FormatDefinition formatDefinition = null;

        for (int i = 0; i < child.getLength(); i++) {

            Node node = child.item(i);
            if (node.getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }
            String name = node.getLocalName();

            if (name.equals(SchemaNames.PACKAGE_ELEMENT)) {
                String packageName = ((Element) node).getAttribute(SchemaNames.PAKCAGE_ATTRIBUTE_NAME);
                // 取出包下所有使用了@Resource注解的类名放入resourceClassList
                String[] resourceNames = getResources(packageName);
                for (String resourceName : resourceNames) {
                    try {
                        Class<?> resourceClz = Class.forName(resourceName);
                        resourceClassList.add(resourceClz);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }

            // 取出配置中format的格式
            if (name.equals(SchemaNames.FORMAT_ELEMENT)) {
                Element e = (Element) node;
                String type = e.getAttribute(SchemaNames.FORMAT_ATTRIBUTE_TYPE);
                String suffix = e.getAttribute(SchemaNames.FORMAT_ATTRIBUTE_SUFFIX);
                String location = e.getAttribute(SchemaNames.FORMAT_ATTRIBUTE_LOCATION);
                formatDefinition = new FormatDefinition(location, type, suffix);
            }
        }

        // 遍历resourceClassList并构建对应的BeanDefinition
        for (Class resourceClass : resourceClassList) {
            BeanDefinition beanDefinition = parseResource(resourceClass, formatDefinition);
            resourceBeanList.add(beanDefinition);
        }

        factory.addPropertyValue("definitions", resourceBeanList);

        return factory.getBeanDefinition();
    }

    /**
     * 构建 ResourceDefinition 并返回对应的BeanDefinition
     *
     * @param clz
     * @param formatDefinition
     * @return
     */
    private BeanDefinition parseResource(Class<?> clz, FormatDefinition formatDefinition) {
        // 采用BeanDefinitionBuilder构建BeanDefinition
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(ResourceDefinition.class);
        beanDefinitionBuilder.addConstructorArgValue(clz);
        beanDefinitionBuilder.addConstructorArgValue(formatDefinition);
        return beanDefinitionBuilder.getBeanDefinition();
    }

    /**
     * 根据packageName取得包路径下的所有类名
     *
     * @param packageName
     * @return
     */
    private String[] getResources(String packageName) {

        // 扫描包路径下的所有类
        String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + resolveBasePackage(packageName) + "/**/*.class";
        // 提取资源
        Set<String> resourceNameSet = new HashSet<String>();
        try {
            Resource[] resources = resourcePatternResolver.getResources(packageSearchPath);
            // 过滤
            for (Resource resource : resources) {
                if (!resource.isReadable()) {
                    continue;
                }
                // 取出对应类上的注解信息
                MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(resource);
                AnnotationMetadata annotationMetadata = metadataReader.getAnnotationMetadata();
                if (!annotationMetadata.hasAnnotation(annoName)) {
                    continue;
                }
                // 取出类名
                ClassMetadata clzMeta = metadataReader.getClassMetadata();
                resourceNameSet.add(clzMeta.getClassName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return resourceNameSet.toArray(new String[0]);
    }

    private String resolveBasePackage(String basePackage) {
        return ClassUtils.convertClassNameToResourcePath(SystemPropertyUtils.resolvePlaceholders(basePackage));
    }
}

class FormatDefinition {

    private final String location;

    private final String type;

    private final String suffix;

    public FormatDefinition(String location, String type, String suffix) {
        this.location = location;
        this.type = type;
        this.suffix = suffix;
    }

    public String getLocation() {
        return location;
    }

    public String getType() {
        return type;
    }

    public String getSuffix() {
        return suffix;
    }
}
