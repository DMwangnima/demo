package cn.nuofankj.niochat.server.handler.impl;

import cn.nuofankj.niochat.common.MessageType;
import cn.nuofankj.niochat.common.NameUtil;
import cn.nuofankj.niochat.server.ServerInfo;
import cn.nuofankj.niochat.server.handler.AbstractHandler;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.channels.SelectionKey;
import java.util.Map;

@Component("loginServerHandler")
public class LoginServerHandler extends AbstractHandler {

    @PostConstruct
    public void init() {
        MessageType messageType = MessageType.LOGIN;
        init(messageType);
    }

    @Override
    public void doHandler(Object info, SelectionKey key) {
        String nickName = NameUtil.getChineseName();
        Map<String, SelectionKey> users = ((ServerInfo) info).getUsers();
        users.put(nickName, key);
    }
}
