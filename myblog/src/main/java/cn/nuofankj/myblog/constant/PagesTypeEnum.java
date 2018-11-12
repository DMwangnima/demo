package cn.nuofankj.myblog.constant;

public enum PagesTypeEnum {
    ABOUT(1, "about"),
    RESUME(2, "resume"),
    ;
    int status;
    String statusMsg;

    PagesTypeEnum(int status,String statusMsg) {
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