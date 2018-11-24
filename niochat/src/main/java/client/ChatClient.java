package client;

import common.Message;
import common.MessageType;
import common.ProtoStuffUtil;
import constant.ChatConstant;
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
    private String clientName;
    private SocketChannel slefClientChannel;

    public ChatClient() {
        init();
    }

    public void init() {
        try {
            this.byteStream = new ByteArrayOutputStream();
            selector = Selector.open();
            slefClientChannel = SocketChannel.open();
            slefClientChannel.configureBlocking(false);
            // 需要调用finishConnect才会真正进行连接
            slefClientChannel.connect(new InetSocketAddress("127.0.0.1", port));
            slefClientChannel.register(selector, SelectionKey.OP_CONNECT);
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
                        MessageType.LOGIN.sendMessage(socketChannel, null, ChatConstant.SYS_NAME, null);
                        // 开启读键盘消息并发送线程
                        new ScannerMsg(key).start();
                    } else if(key.isReadable()) {
                        String message = MessageType.LOGIN.receiveMessage(key);
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
                MessageType.NORMAL.sendMessage(socketChannel, );
            }
        }
    }

    public static void main(String[] args) {
        ChatClient chatClient = new ChatClient();
        chatClient.doWork();
    }
}
