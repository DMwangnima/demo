package cn.nuofankj.niochat.client.handler;

import cn.nuofankj.niochat.common.Message;
import cn.nuofankj.niochat.common.MessageType;
import cn.nuofankj.niochat.common.ProtoStuffUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class AbstractHandler {

    private static Map<MessageType, AbstractHandler> handerMap = new HashMap<>();
    private MessageType messageType;

    public void init(MessageType messageType) {
        AbstractHandler.handerMap.put(messageType, this);
    }

    public void sendMessage(SocketChannel socketChannel, String sender, String receiver, String msg) {
        // 给服务端发送登录事件
        Message message = Message.valueOf(sender, receiver, messageType, msg);
        ByteBuffer byteBuffer = ByteBuffer.wrap(ProtoStuffUtil.serialize(message));
        try {
            socketChannel.write(byteBuffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void doHandler(Object info, SelectionKey key) {
    }

    public static AbstractHandler getHandler(MessageType messageType) {
        AbstractHandler handler = null;
        Set<Map.Entry<MessageType, AbstractHandler>> entries = handerMap.entrySet();
        for(Map.Entry<MessageType, AbstractHandler> messageTypeHandler : entries) {
            if(messageTypeHandler.getKey() == messageType) {
                handler = messageTypeHandler.getValue();
            }
        }
        return handler;
    }

    public static Message getMessage(SelectionKey key) throws IOException {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        SocketChannel clientChannel = (SocketChannel)key.channel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        StringBuffer stringBuffer = new StringBuffer();
        int bufferSize = 0;
        while((bufferSize = clientChannel.read(byteBuffer)) > 0) {
            byteStream.write(byteBuffer.array(), 0, bufferSize);
        }
        // 接收到客户端推送的消息
        Message message = ProtoStuffUtil.deserialize(byteStream.toByteArray(), Message.class);
        return message;
    }
}
