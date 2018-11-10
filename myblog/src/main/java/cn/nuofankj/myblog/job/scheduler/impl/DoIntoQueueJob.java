package cn.nuofankj.myblog.job.scheduler.impl;

import cn.nuofankj.fun.constant.CrawlVideoConst;
import cn.nuofankj.fun.entity.HotVideoEnt;
import cn.nuofankj.fun.job.scheduler.AbstractSchedulerJob;
import cn.nuofankj.fun.repository.HotVideoRepository;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 数据库 -> 队列
 */
public class DoIntoQueueJob extends AbstractSchedulerJob {

    private HotVideoRepository hotVideoRepository;
    private ConcurrentLinkedQueue<HotVideoEnt> hotVideoEntsQueue;

    public DoIntoQueueJob(ConcurrentLinkedQueue<HotVideoEnt> hotVideoEntsQueue, HotVideoRepository hotVideoRepository) {
        this.hotVideoRepository = hotVideoRepository;
        this.hotVideoEntsQueue = hotVideoEntsQueue;
    }


    @Override
    public void run() {
        List<HotVideoEnt> toCrawlVideos = hotVideoRepository.findHotVideoEntNoCrawl(CrawlVideoConst.NO_CRAWL_STATUS, CrawlVideoConst.CRAWL_FAILED_STATUS);
        hotVideoEntsQueue.addAll(toCrawlVideos);
    }
}
