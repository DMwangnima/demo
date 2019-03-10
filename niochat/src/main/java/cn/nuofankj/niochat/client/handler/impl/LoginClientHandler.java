package cn.nuofankj.niochat.client.handler.impl;

import cn.nuofankj.niochat.common.MessageType;
import cn.nuofankj.niochat.client.handler.AbstractHandler;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component("loginClientHandler")
public class LoginClientHandler extends AbstractHandler {

    @PostConstruct
    public void init() {
        MessageType messageType = MessageType.LOGIN;
        init(messageType);
    }
}
