package com.nuofankj.springdemo.reader;

import com.nuofankj.springdemo.support.TypeEnum;
import com.nuofankj.springdemo.utility.ReflectionUtility;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CsvReader implements ResourceReader {

    private final String ROW_SERVER = "SERVER";
    private final String ROW_END = "END";
    // 转换器，通过applicationContext中注入
    @Autowired
    private ConversionService conversionService;
    // 静态资源中的都是String的配置
    private TypeDescriptor sourceType = TypeDescriptor.valueOf(String.class);

    @Override
    public String getFormat() {
        return "csv";
    }

    @Override
    public <E> Iterator<E> read(InputStream inputStream, Class<E> clz) {

        List<E> resourceBeans = new ArrayList<>();
        boolean hasHeader = false;
        CSVHeader<E> csvHeader = new CSVHeader<>(clz);
        try {
            // 静态资源中的具体配置
            CSVParser parser = CSVParser.parse(inputStream, Charset.forName("GBK"), CSVFormat.DEFAULT);
            // CSVRecord对应每一行的配置
            for (CSVRecord csvRecord : parser) {
                if (csvRecord.size() == 0) {
                    continue;
                }
                if (!hasHeader) {
                    hasHeader = readerHeaders(csvHeader, csvRecord);
                    continue;
                }
                boolean end = readEnd(csvRecord);
                // 将每一行的配置转换为对应类对象
                E resourceBean = csvHeader.readResourceBean(csvRecord);
                if (resourceBean != null) {
                    resourceBeans.add(resourceBean);
                }
                if (end) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return resourceBeans.iterator();
    }

    private boolean readEnd(CSVRecord csvRecord) {
        String firstColumnValue = csvRecord.get(0);
        return StringUtils.equals(ROW_END, firstColumnValue);
    }

    /**
     * 设置静态配置中对应配置的列位置和对应类内的Field
     *
     * @param csvHeader
     * @param csvRecord
     * @return
     */
    private boolean readerHeaders(CSVHeader<?> csvHeader, CSVRecord csvRecord) {

        Class<?> clz = csvHeader.getResourceClass();
        String firstColumnValue = csvRecord.get(0);
        // 配置中以SERVER为开始证明是属于服务端配置
        if (ROW_SERVER.equals(firstColumnValue)) {
            // 存放该资源配置下Field的数据和对应的位置
            List<FieldInfo> fieldInfos = new ArrayList<>();
            for (int i = 1; i < csvRecord.size(); i++) {
                // 取出第i列的数据
                String value = csvRecord.get(i);
                if (!StringUtils.isBlank(value)) {
                    try {
                        // 根据命名取出Field
                        Field field = clz.getDeclaredField(value);
                        FieldInfo info = new FieldInfo(i, field);
                        fieldInfos.add(info);
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    }
                }
            }
            csvHeader.setFieldInfos(fieldInfos);
            return true;
        }
        return false;
    }

    private class CSVHeader<E> {

        private Class<E> resourceClass;

        private List<FieldInfo> fieldInfos;

        public CSVHeader(Class<E> resourceClass) {
            this.resourceClass = resourceClass;
        }

        public Class<E> getResourceClass() {
            return resourceClass;
        }

        public void setFieldInfos(List<FieldInfo> fieldInfos) {
            this.fieldInfos = fieldInfos;
        }

        public E readResourceBean(CSVRecord csvRecord) {

            E resourceObject = null;
            try {
                // 实例化配置对象
                resourceObject = resourceClass.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            // b遍历每个FieldInfo，其中存放对应的Field以及对应的在配置中的第几列
            for (FieldInfo fieldInfo : fieldInfos) {
                String value = csvRecord.get(fieldInfo.index);
                if (!StringUtils.isEmpty(value)) {
                    // 将value注入field中，同时放入resourceObject中
                    if (!inject(resourceObject, fieldInfo.field, value)) {
                        throw new IllegalArgumentException(getResourceClass().getName() + " inject " + fieldInfo + " failure");
                    }
                }
            }

            return resourceObject;
        }
    }

    /**
     * 真正实现给实例注入资源中的配置
     *
     * @param instance
     * @param field
     * @param content
     * @return
     */
    private boolean inject(Object instance, Field field, String content) {

        TypeDescriptor targetType = new TypeDescriptor(field);
        // 根据类型匹配，选中合适的转换器将content转化为对应的类型
        Object value = conversionService.convert(content, sourceType, targetType);
        try {
            field.set(instance, value);
            return true;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Field信息
     */
    private class FieldInfo {
        // 第几列
        public int index;
        // 资源类属性
        public Field field;

        public FieldInfo(int index, Field field) {
            ReflectionUtility.makeAccessible(field);
            this.index = index;
            this.field = field;
        }
    }
}
