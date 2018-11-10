package cn.nuofankj.myblog.job.crawler.q2029;

import cn.nuofankj.fun.common.HttpMethod;
import cn.nuofankj.fun.constant.CrawlVideoConst;
import cn.nuofankj.fun.constant.SourceEnum;
import cn.nuofankj.fun.entity.HotVideoEnt;
import cn.nuofankj.fun.entity.ShowVideoEnt;
import cn.nuofankj.fun.entity.WebPage;
import cn.nuofankj.fun.job.crawler.AbstractCrawler;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

@Slf4j
public class Q2029CrawlerJob extends AbstractCrawler {

    private ConcurrentLinkedQueue<ShowVideoEnt> showVideoEntsQueue;

    public Q2029CrawlerJob(ConcurrentLinkedQueue<HotVideoEnt> hotVideoEntsQueue, ConcurrentLinkedQueue<ShowVideoEnt> showVideoEntsQueue) {
        super(hotVideoEntsQueue);
        this.showVideoEntsQueue = showVideoEntsQueue;
    }

    @Override
    public void parsePage() {
        Iterator<HotVideoEnt> hotVideoEntIterator = hotVideoQueue.iterator();
        while (hotVideoEntIterator.hasNext()) {
            HotVideoEnt hotVideoEnt = hotVideoEntIterator.next();
            hotVideoEnt.setStatus(hotVideoEnt.getStatus() - 1);
            log.info(hotVideoEnt.getTitleCn() + "正在采集！");
            int status = hotVideoEnt.getStatus();
            if (status <= CrawlVideoConst.NO_CRAWL_STATUS && status >= CrawlVideoConst.CRAWL_FAILED_STATUS) {
                Map<String, String> map = new HashMap<>();
                map.put("wd", hotVideoEnt.getTitleCn());
                WebPage webPage = getPage(CrawlVideoConst.Q2029_SEARCH_URL, HttpMethod.POST, map);
                if (webPage == null) {
                    log.info("采集资源{},采集不到对应地址{}", hotVideoEnt.getTitleCn(), CrawlVideoConst.HALIHALI_SEARCH_URL);
                    continue;
                }

                Document document = webPage.getDocument();
                Element movieItemElement = document.getElementsByClass("movie-item").first();
                if (movieItemElement == null) {
                    log.info("采集资源{},采集不到对应地址{}" , hotVideoEnt.getTitleCn(), CrawlVideoConst.HALIHALI_SEARCH_URL);
                    continue;
                }

                String searchUrl = CrawlVideoConst.Q2029_BASE_URL + movieItemElement.select("a").first().attr("href");
                webPage = getPage(searchUrl, HttpMethod.GET, null);
                if (webPage == null) {
                    log.info("采集资源{},采集不到对应地址{}" , hotVideoEnt.getTitleCn(), searchUrl);
                    continue;
                }

                document = webPage.getDocument();
                Elements dlistGroupItem = document.getElementsByClass("dslist-group-item");
                if (dlistGroupItem == null) {
                    log.info("采集资源{},采集不到对应地址{}" , hotVideoEnt.getTitleCn(), searchUrl);
                    continue;
                }

                for(Element dlistItem : dlistGroupItem) {
                    String tip = dlistItem.select("a").first().text();
                    if(tip.contains("预告")) {
                        log.info("采集资源{},只采集到{},放弃采集,对应地址{}" , hotVideoEnt.getTitleCn(),tip, searchUrl);
                        continue;
                    }
                    String finalShowUrl =  dlistItem.select("a").first().attr("href");
                    if(finalShowUrl == null || finalShowUrl.equals("")) {
                        log.info("采集资源{},采集不到对应地址{}" , hotVideoEnt.getTitleCn(), searchUrl);
                        continue;
                    }
                    showVideoEntsQueue.add(new ShowVideoEnt().setTip(tip).setVideoId(hotVideoEnt.getId()).setShowUrl(CrawlVideoConst.Q2029_BASE_URL + finalShowUrl).setSourceEnum(SourceEnum.Q2029_SOURCE.getId()));
                    hotVideoEnt.setStatus(CrawlVideoConst.CRAWL_SUCCESS_STATUS);
                    log.info("采集视频名字{},采集地址{},采集成功", hotVideoEnt.getTitleCn(), finalShowUrl);
                }
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
