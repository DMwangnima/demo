package cn.nuofankj.myblog.constant;

/**
 * 是否是作者，0否，1是，默认0
 */
public enum ArticleAuthorEnum {
    NO_AUTHOR(0, "否"),
    YES_AUTHOR(1, "是"),
    ;
    int status;
    String statusMsg;

    ArticleAuthorEnum(int status,String statusMsg) {
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
