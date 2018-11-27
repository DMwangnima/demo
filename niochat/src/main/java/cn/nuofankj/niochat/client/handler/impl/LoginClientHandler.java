package cn.nuofankj.niochat.client.handler.impl;

import cn.nuofankj.niochat.common.MessageType;
import cn.nuofankj.niochat.client.handler.AbstractHandler;
import org.springframework.stereotype.Component;

@Component("loginClientHandler")
public class LoginClientHandler extends AbstractHandler {

    public LoginClientHandler() {
        MessageType messageType = MessageType.LOGIN;
        init(messageType);
    }
}
