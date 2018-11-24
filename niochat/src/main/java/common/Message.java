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
        byte[] body = null;
        if(msg != null) {
            body = msg.getBytes();
        }
        Message message = new Message(msgHeader, body);
        return message;
    }
}