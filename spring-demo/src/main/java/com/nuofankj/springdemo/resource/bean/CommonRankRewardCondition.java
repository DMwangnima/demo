package com.nuofankj.springdemo.resource.bean;

import com.nuofankj.springdemo.support.ClassMapper;

@ClassMapper
public class CommonRankRewardCondition extends ActivityRewardCondition {

    @Override
    public void doParse() {
        System.out.println(this.getClass().getName());
    }
}
