package com.nuofankj.springdemo.resource.bean;

public class KaiFuRewardConditionConfig {

    private int level;

    private int rankStart;

    private int rankEnd;

    private long value;

    public static KaiFuRewardConditionConfig valueOf(int level, int rankStart, int rankEnd, long value) {

        KaiFuRewardConditionConfig conditionConfig = new KaiFuRewardConditionConfig();
        conditionConfig.level = level;
        conditionConfig.rankStart = rankStart;
        conditionConfig.rankEnd = rankEnd;
        conditionConfig.value = value;

        return conditionConfig;
    }

    @Override
    public String toString() {
        return "KaiFuRewardConditionConfig{" +
                "level=" + level +
                ", rankStart=" + rankStart +
                ", rankEnd=" + rankEnd +
                ", value=" + value +
                '}';
    }
}
