package cn.nuofankj.myblog.constant;

public class CrawlVideoConst{

    /**  0未采集 **/
    public static final int NO_CRAWL_STATUS = 0;

    /**  1采集成功 **/
    public static final int CRAWL_SUCCESS_STATUS = 1;

    /** -1采集失败 **/
    public static final int CRAWL_FAILED_STATUS = -30;

    /** 2放弃采集 **/
    public static final int GIVEUP_CRAWL_STATUS = 2;

    /** 分页显示个数 **/
    public static final int pageCount = 20;

    public static final String PHANTOMJS_PATH = "D:\\java\\idea\\t-fun\\fun\\phantomjs\\phantomjs.exe";

    /** frame生成地址模板 **/
    public static final String HALIHALI_OUTPUT_FRAME = "./video/SOURCE_ID.html";

    /** halihali采集地址 **/
    public static final String HALIHALI_VIDEO_PAGE = "https://www.halihali.me/v/NAME/ID1-ID2.html";

    /** halihali采集脚本 **/
    public static final String HALIHALI_CRAWL_JS = "D:\\java\\idea\\t-fun\\fun\\src\\main\\java\\cn\\nuofankj\\fun\\job\\crawler\\halihali\\halihali_videourl.js";

    /** halihali根路径 **/
    public static final String HALIHALI_BASE_URL = "https://www.halihali.tv/";

    /** halihali 搜索页面 **/
    public static final String HALIHALI_SEARCH_URL = HALIHALI_BASE_URL + "search/";

    /** 时光网 影讯采集 **/
    public static final String TIME_URL =  "https://api-m.mtime.cn/PageSubArea/HotPlayMovies.api?locationId=290";

    /** q2029 根路径 **/
    public static final String Q2029_BASE_URL = "http://www.q2029.com/";

    /** q2029 搜索页面 **/
    public static final String Q2029_SEARCH_URL = Q2029_BASE_URL + "?c=search";

}
