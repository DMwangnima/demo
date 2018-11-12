package cn.nuofankj.myblog.constant;

/**
 * 状态，0-正常（发布），1-删除，2-记录（待发布）
 */
public enum ArticleStatusEnum {
    NORMAL(0,"正常发布"),
    DELETE(1,"删除"),
    RRECORD(2,"记录待发布"),
    ;
    long status;
    String statusMsg;

    ArticleStatusEnum(long status,String statusMsg) {
        this.status = status;
        this.statusMsg = statusMsg;
    }

    public long getStatus() {
        return status;
    }

    public String getStatusMsg() {
        return statusMsg;
    }
}
