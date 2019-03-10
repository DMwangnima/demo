package cn.nuofankj.niochat;

import cn.nuofankj.niochat.client.handler.impl.LoginClientHandler;
import cn.nuofankj.niochat.common.SpringContextUtil;

public class Main {
    public static void main(String[] args) {
        LoginClientHandler loginClientHandler = (LoginClientHandler)SpringContextUtil.getBean("loginClientHandler");
        if(loginClientHandler == null) {
            System.out.println("null");
        }
    }
}
