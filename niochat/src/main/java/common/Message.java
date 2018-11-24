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
    private String body;

    public static Message valueOf(String sender, String receiver, MessageType type, String msg) {
        MessageHeader msgHeader = new MessageHeader(sender, receiver, type, new Date().getTime());
        Message message = new Message(msgHeader, msg);
        return message;
    }
}