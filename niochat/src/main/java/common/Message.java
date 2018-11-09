package common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    private MessageHeader header;
    private byte[] body;

    public static Message valueOf(String sender, String receiver, MessageType type, String msg) {
        MessageHeader msgHeader = new MessageHeader(sender, receiver, type, new Date().getTime());
        Message message = new Message(msgHeader, msg.getBytes());
        return message;
    }

    public static Message valueOf( MessageType type) {
        Message message = Message.valueOf("","", MessageType.LOGIN, "");
        return message;
    }

    public static Message valueOf( MessageType type, String receiver, String msg) {
        Message message = Message.valueOf("",receiver, MessageType.LOGIN, msg);
        return message;
    }
}