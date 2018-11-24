package common;

import client.ClientData;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

public enum MessageType {
    LOGIN(1,"登录") {
        public void sendMessage(SocketChannel socketChannel, String sender, String receiver, String msg) {
            // 给服务端发送登录事件
            Message message = Message.valueOf(sender, receiver, this, msg);
            ByteBuffer byteBuffer = ByteBuffer.wrap(ProtoStuffUtil.serialize(message));
            try {
                socketChannel.write(byteBuffer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    },

    LOGOUT(2,"注销"),

    NORMAL(3,"单聊"),

    BROADCAST(4,"群发"),

    TASK(5,"任务"),

    NAME(6,"命名"),
    ;
    
    private int code;
    private String  desc;

    MessageType(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public void sendMessage(SocketChannel socketChannel, String sender, String receiver, String msg) {
        // 给服务端发送登录事件
        Message message = Message.valueOf(sender, receiver, this, msg);
        ByteBuffer byteBuffer = ByteBuffer.wrap(ProtoStuffUtil.serialize(message));
        try {
            socketChannel.write(byteBuffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String receiveMessage(ClientData clientData, SelectionKey key) throws IOException {
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
        String clientName = message.getBody();
        clientData.setClientName(clientName);
        return clientName;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}