package com.nuofankj.springdemo.resource.bean;

import com.nuofankj.springdemo.support.ClassMapper;
import com.nuofankj.springdemo.support.SelfParser;

@ClassMapper
public class LevelRankRewardCondition extends ActivityRewardCondition implements SelfParser {

    @Override
    public void doParse() {
        System.out.println(this.getClass().getName());
    }
}
