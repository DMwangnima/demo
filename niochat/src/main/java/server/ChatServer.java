package server;

import common.*;
import constant.ChatConstant;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Slf4j
public class ChatServer {

    private ServerSocketChannel serverSocketChannel;
    private Selector selector;
    private final int port = 8080;
    private ByteArrayOutputStream byteStream;
    // key 为昵称
    private Map<String, SelectionKey> users = new HashMap<>();

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
                        SocketChannel clientChannel = (SocketChannel)key.channel();
                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                        StringBuffer stringBuffer = new StringBuffer();
                        int bufferSize = 0;
                        while((bufferSize = clientChannel.read(byteBuffer)) > 0) {
                            // TODO 此处是否要加 flip ???
                            byteStream.write(byteBuffer.array(), 0, bufferSize);
                        }
                        // 接收到客户端推送的消息
                        Message message = ProtoStuffUtil.deserialize(byteStream.toByteArray(), Message.class);
                        log.info("接收到消息，消息对象为{}", message);
                        if(message.getHeader().getType() == MessageType.LOGIN) {
                            String nickName = NameUtil.getChineseName();
                            users.put(nickName, key);
                            MessageType.LOGIN.sendMessage(clientChannel, ChatConstant.SYS_NAME, nickName, null);
                        }
                    } else if(key.isWritable()) {
                        log.info("不做处理，因为很麻烦");
                    }
                }
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public static void main(String[] args) {
        ChatServer chatServer = new ChatServer();
        chatServer.doWork();
    }
}
