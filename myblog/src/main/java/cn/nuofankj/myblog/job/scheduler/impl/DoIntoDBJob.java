package cn.nuofankj.myblog.job.scheduler.impl;

import cn.nuofankj.fun.constant.CrawlVideoConst;
import cn.nuofankj.fun.entity.HotVideoEnt;
import cn.nuofankj.fun.entity.ShowVideoEnt;
import cn.nuofankj.fun.job.scheduler.AbstractSchedulerJob;
import cn.nuofankj.fun.repository.HotVideoRepository;
import cn.nuofankj.fun.repository.ShowVideoRepository;
import cn.nuofankj.fun.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 队列 -> 数据库
 */
@Slf4j
public class DoIntoDBJob extends AbstractSchedulerJob {

    private HotVideoRepository hotVideoRepository;
    private ConcurrentLinkedQueue<HotVideoEnt> hotVideoQueue;

    public DoIntoDBJob(ConcurrentLinkedQueue<HotVideoEnt> hotVideoEntsQueue, HotVideoRepository hotVideoRepository) {
        this.hotVideoRepository = hotVideoRepository;
        this.hotVideoQueue = hotVideoEntsQueue;
    }

    @Override
    public void run() {
        Iterator<HotVideoEnt> hotVideoEntIterator = hotVideoQueue.iterator();
        while(hotVideoEntIterator.hasNext()) {
            HotVideoEnt hotVideoEnt = hotVideoQueue.poll();
            if (hotVideoEnt != null) {
                log.debug("save hotVideo:{}", hotVideoEnt.toString());
                if (hotVideoRepository.findHotVideoEntByMovieIdAndEnumSource(hotVideoEnt.getMovieId(), hotVideoEnt.getEnumSource()).size() == 0) {
                    String psdCode = CommonUtil.getRandomString(10);
                    hotVideoEnt.setPsdCode(psdCode);
                    hotVideoRepository.save(hotVideoEnt);
                }
            }
        }
    }
}
