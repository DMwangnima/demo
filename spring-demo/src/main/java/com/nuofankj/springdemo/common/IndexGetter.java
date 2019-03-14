package com.nuofankj.springdemo.common;

import java.util.Comparator;

/**
 * 索引值获取器接口
 */
public interface IndexGetter {

    // 获取索引名
    String getName();

    // 是否唯一值索引
    boolean isUnique();

    // 获取索引值
    Object getValue(Object obj);

    // 获取索引排序器
    Comparator getComparator();

    // 检查是否存在索引排序器
    boolean hasComparator();
}
