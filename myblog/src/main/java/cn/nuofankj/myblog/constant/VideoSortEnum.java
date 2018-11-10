package cn.nuofankj.myblog.constant;

/**
 * 初始排序，分值越高越靠前
 */
public enum VideoSortEnum {

    /*高清*/
    HD("高", 50),

    /* 清晰 */
    CLEAR("清", 40),

    /* 抢先版 */
    BE_FIRST("抢先", 10),

    /* 枪版 */
    GUN("枪", 10),;

    // 资源名
    String enumName;
    // 评分
    int score;

    public String getEnumName() {
        return enumName;
    }

    public void setEnumName(String enumName) {
        this.enumName = enumName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    VideoSortEnum(String enumName, int score) {
        this.enumName = enumName;
        this.score = score;
    }


    public static int getVideoSortScore(String enumName) {

        for(VideoSortEnum sortEnum : VideoSortEnum.values()) {
            if(enumName.contains(sortEnum.getEnumName())) {
                return sortEnum.getScore();
            }
        }
        return 0;
    }
}
