package cn.nuofankj.myblog.job.crawler.time;

import cn.nuofankj.fun.common.HttpMethod;
import cn.nuofankj.fun.constant.CrawlVideoConst;
import cn.nuofankj.fun.constant.SourceEnum;
import cn.nuofankj.fun.entity.HotVideoEnt;
import cn.nuofankj.fun.entity.WebPage;
import cn.nuofankj.fun.job.crawler.AbstractCrawler;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 正在上映的视频 采集器
 * 采集点 时光网
 */
@Slf4j
public class HotVideoCrawlerJob extends AbstractCrawler  implements Runnable{
    public HotVideoCrawlerJob(ConcurrentLinkedQueue<HotVideoEnt> hotVideoQueue) {
        super(hotVideoQueue);
    }

    @Override
    public void parsePage() {
        log.info("the HotVideoCrawlerJob crapy  size:{}", hotVideoQueue.size());
        getPage(CrawlVideoConst.TIME_URL, HttpMethod.GET, null);
        String content = webPage.getPage();
        if (content != null) {
            JSONObject jsonObject = JSON.parseObject(content);
            String movies = jsonObject.getString("movies");
            List<HotVideoEnt> videos = JSON.parseArray(movies, HotVideoEnt.class);
            for(HotVideoEnt videoEnt : videos) {
                videoEnt.setEnumSource(SourceEnum.TIME_SOURCE.getId());
            }
            hotVideoQueue.addAll(videos);
        }

    }

    @Override
    public void run() {
        try {
            parsePage();
        } catch (Exception e) {
            log.error("process error", e);
        }

    }
}