package cn.nuofankj.niochat.server.handler.impl;

import cn.nuofankj.niochat.common.MessageType;
import cn.nuofankj.niochat.common.NameUtil;
import cn.nuofankj.niochat.server.ServerInfo;
import cn.nuofankj.niochat.server.handler.AbstractHandler;

import java.nio.channels.SelectionKey;
import java.util.Map;

public class LoginServerHandler extends AbstractHandler {

    public LoginServerHandler() {
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
