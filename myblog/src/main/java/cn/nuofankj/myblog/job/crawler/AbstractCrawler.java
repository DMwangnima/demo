package cn.nuofankj.myblog.job.crawler;

import cn.nuofankj.fun.common.HttpMethod;
import cn.nuofankj.fun.entity.HotVideoEnt;
import cn.nuofankj.fun.entity.WebPage;
import cn.nuofankj.fun.job.scheduler.AbstractSchedulerJob;
import cn.nuofankj.fun.util.HttpClientUtils;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author chenerzhu
 * @create 2018-09-02 13:40
 **/
@Slf4j
public abstract class AbstractCrawler extends AbstractSchedulerJob{
    protected ConcurrentLinkedQueue<HotVideoEnt> hotVideoQueue;
    protected WebPage webPage;

    private Map<String, String> headerMap = new HashMap<String, String>() {{
        put("Connection", "keep-alive");
        put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36");
        put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        put("Accept-Encoding", "gzip, deflate, sdch");
        put("Accept-Language", "zh-CN,zh;q=0.9");
        put("Redis-Control", "max-age=0");
        put("Upgrade-Insecure-Requests", "1");
    }};


    public AbstractCrawler(ConcurrentLinkedQueue<HotVideoEnt> HotVideoEntQueue) {
        this.hotVideoQueue = HotVideoEntQueue;
    }

    @Override
    public void run() {
        try {
            parsePage();
        } catch (Exception e) {
            log.error("process error", e);
        }

    }

    public abstract void parsePage();

    public WebPage getPage(String pageUrl, HttpMethod httpMethd, Map<String, String> formParamMap) {
        WebPage webPage = null;
        try {
            log.debug("start get page:{}", pageUrl);
            headerMap.put("Referer", pageUrl);
            String pageContent = "";
            if (httpMethd == HttpMethod.GET) {
                pageContent = HttpClientUtils.sendGet(pageUrl, headerMap);
            } else if (httpMethd == HttpMethod.POST) {
                pageContent = HttpClientUtils.sendPostForm(pageUrl, "", headerMap, formParamMap);
            }
            if(pageContent == null) {
                log.info("采集地址{},采集失败",pageUrl);
                return null;
            }
            webPage = new WebPage();
            webPage.setCrawlTime(new Date());
            webPage.setPage(pageContent);
            webPage.setDocument(Jsoup.parse(pageContent));
            webPage.setHtml(Jsoup.parse(pageContent).html());
            this.webPage = webPage;
            log.debug("end get page:{}", pageUrl);
        } catch (Exception e) {
            log.error("get page:{}", pageUrl, e);
        }
        return webPage;
    }
}
