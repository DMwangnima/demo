package com.nuofankj.springdemo.resource.bean;

import com.nuofankj.springdemo.anno.ParseIgnore;
import com.nuofankj.springdemo.support.ClassMapper;
import com.nuofankj.springdemo.support.SelfParser;

import java.util.Arrays;

@ClassMapper
public class CommonRankRewardCondition extends ActivityRewardCondition implements SelfParser {

    @ParseIgnore
    private KaiFuRewardConditionConfig conditionConfig;

    @Override
    public void doParse() {
        conditionConfig = KaiFuRewardConditionConfig.valueOf(1, 1, 1, 1);
    }

    @Override
    public String toString() {
        return "CommonRankRewardCondition{" +
                "conditionConfig=" + conditionConfig +
                ", cond=" + Arrays.toString(cond) +
                '}';
    }
}
