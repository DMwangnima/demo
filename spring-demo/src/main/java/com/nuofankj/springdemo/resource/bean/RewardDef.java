package com.nuofankj.springdemo.resource.bean;

import com.nuofankj.springdemo.support.PolyObjectMapper;

public class RewardDef implements PolyObjectMapper {

    private String type;

    private String value;

    private IReward iReward;

    @Override
    public String toString() {
        return "RewardDef{" +
                "type='" + type + '\'' +
                ", value='" + value + '\'' +
                ", iReward=" + iReward +
                '}';
    }
}
