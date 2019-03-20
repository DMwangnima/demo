package com.nuofankj.springdemo.resource;

import com.nuofankj.springdemo.anno.Id;
import com.nuofankj.springdemo.anno.PostInit;
import com.nuofankj.springdemo.anno.Resource;
import com.nuofankj.springdemo.resource.bean.*;
import com.nuofankj.springdemo.support.PolyObjectMapper;

import java.util.Arrays;
import java.util.List;

@Resource
public class CommonResource {

    @Id
    private int id;

    // 基础结构
    private Test1Bean test1Bean;

    // 对象数组
    private Test2BeanArray[] test2Array;

    // 多态&预处理
    private List<ActivityRewardCondition> conditionRewards;

    // 消耗
    private ConsumeDef[] consume;

    // 属性
    private List<ImmutableAttribute> attributes;

    // 掉落
    private Test4Bean effect;

    // 掉落 多态
    private PolyObjectMapper odd4;

    // FIXME: 2019/3/20 json数组注入还有问题
    private Test3Bean bean;

    @PostInit
    public void init() {
        System.out.println("------------------postInit注解调用------------------");
        for (ActivityRewardCondition condition : conditionRewards) {
            condition.doParse();
        }
    }

    @Override
    public String toString() {
        return "CommonResource{" +
                "id=" + id +
                ", test1Bean=" + test1Bean +
                ", test2Array=" + Arrays.toString(test2Array) +
                ", conditionRewards=" + conditionRewards +
                ", consume=" + Arrays.toString(consume) +
                ", attributes=" + attributes +
                ", effect=" + effect +
                ", odd4=" + odd4 +
                ", bean=" + bean +
                '}';
    }
}
