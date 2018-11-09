package client;

import common.Message;
import common.MessageType;
import common.ProtoStuffUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Scanner;
import java.util.Set;

@Slf4j
public class ChatClient {
    private Selector selector;
    private final int port = 8080;
    private ByteArrayOutputStream byteStream;

    public ChatClient() {
        init();
    }

    public void init() {
        try {
            this.byteStream = new ByteArrayOutputStream();
            selector = Selector.open();
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
            // 需要调用finishConnect才会真正进行连接
            socketChannel.connect(new InetSocketAddress("127.0.0.1", port));
            socketChannel.register(selector, SelectionKey.OP_CONNECT);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public void doWork() {
        try {
            while(selector.select() != 0) {
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                for (SelectionKey key : selectionKeys) {
                    if(key.isConnectable()) {
                        SocketChannel socketChannel = (SocketChannel)key.channel();
                        if(socketChannel.isConnectionPending()) {
                            // 完成连接
                            socketChannel.finishConnect();
                        }
                        log.info("已连接，坐等服务端分配账户名！");
                        socketChannel.register(selector, SelectionKey.OP_READ);
                        // 给服务端发送登录事件
                        Message message = Message.valueOf(MessageType.LOGIN);
                        ByteBuffer byteBuffer = ByteBuffer.wrap(ProtoStuffUtil.serialize(message));
                        socketChannel.write(byteBuffer);
                        // 开启读键盘消息并发送线程
                        new ScannerMsg(key).start();
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
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class ScannerMsg extends Thread {

        private SelectionKey targetKey;

        public ScannerMsg(SelectionKey targetKey) {
            this.targetKey = targetKey;
        }

        @Override
        public void run() {
            SocketChannel socketChannel = (SocketChannel)targetKey.channel();
            while(true) {
                Scanner scanner = new Scanner(System.in);
                String msg = scanner.nextLine();
                msg = "[客户端] " + msg;
                ByteBuffer byteBuffer = ByteBuffer.wrap(msg.getBytes());
                try {
                    socketChannel.write(byteBuffer);
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            }
        }
    }

    public static void main(String[] args) {
        ChatClient chatClient = new ChatClient();
        chatClient.doWork();
    }
}
