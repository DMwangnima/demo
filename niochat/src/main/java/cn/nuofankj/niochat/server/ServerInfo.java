package cn.nuofankj.niochat.server;

import lombok.Data;

import java.nio.channels.SelectionKey;
import java.util.HashMap;
import java.util.Map;

@Data
public class ServerInfo {
    // key 为昵称
    private Map<String, SelectionKey> users = new HashMap<>();
}
