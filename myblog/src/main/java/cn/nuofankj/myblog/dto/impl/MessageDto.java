package cn.nuofankj.myblog.dto.impl;

import lombok.Data;

@Data
public class MessageDto {

    private Object data;

    private int code;

    private String msg;

    private boolean status;



    public static MessageDto valueOf(Object data, int code, String message, boolean status) {
        MessageDto messageDto = new MessageDto();
        messageDto.setData(data);
        messageDto.setCode(code);
        messageDto.setMsg(message);
        messageDto.setStatus(status);

        return messageDto;
    }
}
