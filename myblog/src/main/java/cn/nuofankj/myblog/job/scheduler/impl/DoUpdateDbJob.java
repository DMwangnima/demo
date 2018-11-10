package cn.nuofankj.myblog.job.scheduler.impl;

import cn.nuofankj.fun.constant.CrawlVideoConst;
import cn.nuofankj.fun.constant.VideoSortEnum;
import cn.nuofankj.fun.entity.HotVideoEnt;
import cn.nuofankj.fun.entity.ShowVideoEnt;
import cn.nuofankj.fun.job.scheduler.AbstractSchedulerJob;
import cn.nuofankj.fun.repository.HotVideoRepository;
import cn.nuofankj.fun.repository.ShowVideoRepository;
import lombok.extern.slf4j.Slf4j;

import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 队列 update 数据库
 */
@Slf4j
public class DoUpdateDbJob extends AbstractSchedulerJob {

    private HotVideoRepository hotVideoRepository;
    private ConcurrentLinkedQueue<HotVideoEnt> hotVideoQueue;
    private ConcurrentLinkedQueue<ShowVideoEnt> showVideoQueue;
    private ShowVideoRepository showVideoRepository;

    public DoUpdateDbJob(ConcurrentLinkedQueue<HotVideoEnt> hotVideoEntsQueue, ConcurrentLinkedQueue<ShowVideoEnt> showVideoQueue, ShowVideoRepository showVideoRepository, HotVideoRepository hotVideoRepository) {
        this.hotVideoRepository = hotVideoRepository;
        this.hotVideoQueue = hotVideoEntsQueue;
        this.showVideoQueue = showVideoQueue;
        this.showVideoRepository = showVideoRepository;
    }

    @Override
    public void run() {
        Iterator<HotVideoEnt> hotVideoEntIterator = hotVideoQueue.iterator();
        while(hotVideoEntIterator.hasNext()) {
            HotVideoEnt hotVideoEnt = hotVideoEntIterator.next();
            if(hotVideoEnt.getStatus() == CrawlVideoConst.CRAWL_SUCCESS_STATUS) {
                log.debug(hotVideoEnt.getTitleCn()+"采集成功，更新状态后入库！");
                hotVideoRepository.save(hotVideoEnt);
            } else {
                log.debug(hotVideoEnt.getTitleCn()+"采集失败，继续采集！");
            }
        }
        while(!showVideoQueue.isEmpty()) {
            ShowVideoEnt showVideoEnt = showVideoQueue.poll();
            if(showVideoRepository.findShowVideoEntByShowUrl(showVideoEnt.getShowUrl()).size() == 0) {
                log.debug(showVideoEnt.getShowUrl()+"采集成功，入库！");
                int videoSortScore = VideoSortEnum.getVideoSortScore(showVideoEnt.getTip());
                showVideoEnt.setScore(videoSortScore);
                showVideoRepository.save(showVideoEnt);
            }
        }
    }
}
