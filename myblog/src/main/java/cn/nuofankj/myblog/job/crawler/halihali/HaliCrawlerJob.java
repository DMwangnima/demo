package cn.nuofankj.myblog.job.crawler.halihali;

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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 正在上映的视频具体视频采集 采集器
 * 采集点 哈里哈里视频网
 * 数据库 -> 采集显示页面 -> 更新数据库
 */
@Slf4j
public class HaliCrawlerJob extends AbstractCrawler {

    private ConcurrentLinkedQueue<ShowVideoEnt> showVideoEntsQueue;

    public HaliCrawlerJob(ConcurrentLinkedQueue<HotVideoEnt> hotVideoEntsQueue, ConcurrentLinkedQueue<ShowVideoEnt> showVideoEntsQueue) {
        super(hotVideoEntsQueue);
        this.showVideoEntsQueue = showVideoEntsQueue;
    }

    @Override
    public void parsePage() {

        Iterator<HotVideoEnt> hotVideoEntIterator = hotVideoQueue.iterator();
        while (hotVideoEntIterator.hasNext()) {
            HotVideoEnt hotVideoEnt = hotVideoEntIterator.next();
            hotVideoEnt.setStatus(hotVideoEnt.getStatus() - 1);
            log.info(hotVideoEnt.getTitleCn()+"正在采集！");
            int status = hotVideoEnt.getStatus();
            if (status <= CrawlVideoConst.NO_CRAWL_STATUS && status >= CrawlVideoConst.CRAWL_FAILED_STATUS) {
                Map<String, String> map = new HashMap<>();
                map.put("wd", hotVideoEnt.getTitleCn());
                WebPage webPage = getPage(CrawlVideoConst.HALIHALI_SEARCH_URL, HttpMethod.POST, map);
                if (webPage == null) {
                    log.info("采集资源{},采集不到对应地址{}", hotVideoEnt.getTitleCn(), CrawlVideoConst.HALIHALI_SEARCH_URL);
                    continue;
                }

                Document document = webPage.getDocument();
                Element listImgElement = document.getElementsByClass("list-img").first();
                if (listImgElement == null) {
                    log.info("采集资源{},采集不到对应地址{}" , hotVideoEnt.getTitleCn(), CrawlVideoConst.HALIHALI_SEARCH_URL);
                    continue;
                }
                String title = listImgElement.attr("title");
                if(!title.equals(hotVideoEnt.getTitleCn())) {
                    log.info("采集资源{},只采集到类似的{}数据,放弃采集，对应地址{}" , hotVideoEnt.getTitleCn(),title, CrawlVideoConst.HALIHALI_SEARCH_URL);
                    continue;
                }
                String searchUrl = CrawlVideoConst.HALIHALI_BASE_URL + listImgElement.attr("href");
                webPage = getPage(searchUrl, HttpMethod.GET, null);
                if (webPage == null) {
                    log.info("采集资源{},采集不到对应地址{}" , hotVideoEnt.getTitleCn(), searchUrl);
                    continue;
                }

                document = webPage.getDocument();
                Element playerListElement = document.getElementsByClass("player_list").first();
                if (playerListElement == null) {
                    log.info("采集资源{},采集不到对应地址{}" , hotVideoEnt.getTitleCn(), searchUrl);
                    continue;
                }

                String tip =  playerListElement.select("a").first().attr("title");
                String finalShowUrl =  playerListElement.select("a").first().attr("href");
                if(finalShowUrl == null || finalShowUrl.equals("")) {
                    log.info("采集资源{},采集不到对应地址{}" , hotVideoEnt.getTitleCn(), searchUrl);
                    continue;
                }
                showVideoEntsQueue.add(new ShowVideoEnt().setTip(tip).setVideoId(hotVideoEnt.getId()).setShowUrl(CrawlVideoConst.HALIHALI_BASE_URL + finalShowUrl).setSourceEnum(SourceEnum.HALIHALI_SOURCE.getId()));
                hotVideoEnt.setStatus(CrawlVideoConst.CRAWL_SUCCESS_STATUS);
                log.info("采集视频名字{},采集地址{},采集成功", hotVideoEnt.getTitleCn(), finalShowUrl);
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
