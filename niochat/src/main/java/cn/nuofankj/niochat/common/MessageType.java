package cn.nuofankj.niochat.common;

public enum MessageType {
    SYSTEM(0, "[系统对话]"),

    LOGIN(1,"[登录]"),

    LOGOUT(2,"[注销]"),

    NORMAL(3,"[单聊]"),

    BROADCAST(4,"[群发]"),

    TASK(5,"[任务]"),

    NAME(6,"[命名]"),
    ;
    
    private int code;
    private String  desc;

    MessageType(int code, String desc) {
        this.code = code;
        this.desc = desc;
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

    public static MessageType getMessageType(String msg) {
        MessageType[] values = MessageType.values();
        for(MessageType messageType : values) {
            if(msg.contains(messageType.getDesc())) {
                return messageType;
            }
        }
        return null;
    }
}