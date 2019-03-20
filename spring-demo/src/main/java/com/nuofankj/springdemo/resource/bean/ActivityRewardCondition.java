package com.nuofankj.springdemo.resource.bean;

import com.nuofankj.springdemo.support.PolyObjectMapper;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public abstract class ActivityRewardCondition implements PolyObjectMapper {

    protected String[] cond;

    private Map<Integer, int[]> reward = new HashMap<>();

    public String[] getCond() {
        return cond;
    }

    public Map<Integer, int[]> getReward() {
        return reward;
    }

    public abstract void doParse();

    @Override
    public String toString() {
        return "ActivityRewardCondition{" +
                "cond=" + Arrays.toString(cond) +
                ", reward=" + reward +
                '}';
    }
}
