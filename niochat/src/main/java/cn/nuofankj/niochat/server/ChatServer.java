package cn.nuofankj.niochat.server;

import cn.nuofankj.niochat.common.Message;
import cn.nuofankj.niochat.server.handler.AbstractHandler;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.*;
import java.util.Set;

@Slf4j
public class ChatServer {

    private ServerSocketChannel serverSocketChannel;
    private Selector selector;
    private final int port = 8080;
    private ByteArrayOutputStream byteStream;
    private ServerInfo serverInfo;

    public ChatServer() {
        init();
    }

    public void init() {
        try {
            this.byteStream = new ByteArrayOutputStream();
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress("127.0.0.1", port));
            serverSocketChannel.configureBlocking(false);
            selector = Selector.open();
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            serverInfo = new ServerInfo();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public void doWork() {
        try {
            while(selector.select() > 0) {
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                for (SelectionKey key : selectionKeys) {
                    selectionKeys.remove(key);
                    if(key.isAcceptable()) {
                        SocketChannel clientChannel = serverSocketChannel.accept();
                        if(!clientChannel.isRegistered()) {
                            clientChannel.configureBlocking(false);
                            clientChannel.register(selector, SelectionKey.OP_READ);
                        }
                    } else if(key.isReadable()) {
                        Message message = AbstractHandler.getMessage(key);
                        AbstractHandler handler = AbstractHandler.getHandler(message.getHeader().getType());
                        handler.doHandler(serverInfo, key);
                    } else if(key.isWritable()) {
                        log.info("不做处理，因为很麻烦");
                    }
                }
            }
        } catch (IOException e) {
            log.error(e.getLocalizedMessage());
        }
    }

    public static void main(String[] args) {
        ChatServer chatServer = new ChatServer();
        chatServer.doWork();
    }
}
