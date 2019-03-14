package com.nuofankj.springdemo.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StorageData<K, V> {

    // 主存储空间
    Map<K, V> values = new HashMap<>();
    // 索引存储空间
    Map<String, Map<Object, List<V>>> indexs = new HashMap<>();
    // 唯一值存储空间
    Map<String, Map<Object, V>> uniques = new HashMap<>();
}
