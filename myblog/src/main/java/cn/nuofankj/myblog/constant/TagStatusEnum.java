package cn.nuofankj.myblog.constant;

public enum TagStatusEnum {
    NORMAL(0,"正常发布"),
    DELETE(1,"删除"),
    ;
    int status;
    String statusMsg;

    TagStatusEnum(int status,String statusMsg) {
        this.status = status;
        this.statusMsg = statusMsg;
    }

    public int getStatus() {
        return status;
    }

    public String getStatusMsg() {
        return statusMsg;
    }
}
