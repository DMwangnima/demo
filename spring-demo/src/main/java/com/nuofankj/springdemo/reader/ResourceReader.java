package com.nuofankj.springdemo.reader;

import java.io.InputStream;
import java.util.Iterator;

public interface ResourceReader {

    // 获取资源的格式名
    String getFormat();

    <E> Iterator<E> read(InputStream inputStream, Class<E> clz);
}
