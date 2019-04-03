package com.nuofankj.springdemo.resource.bean;

import com.nuofankj.springdemo.support.ClassMapper;

@ClassMapper
public class ItemReward extends AbstractReward {

    private ItemPure itemPure;

    @Override
    void print() {
        System.out.println("this is " + this.getClass().getSimpleName());
    }

    @Override
    public String toString() {
        return "ItemReward{" +
                "itemPure=" + itemPure +
                '}';
    }
}
