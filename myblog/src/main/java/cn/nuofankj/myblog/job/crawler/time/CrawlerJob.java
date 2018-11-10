package cn.nuofankj.myblog.job.crawler.time;

import cn.nuofankj.fun.entity.HotVideoEnt;
import cn.nuofankj.fun.job.execute.ISchedulerJobExecutor;
import cn.nuofankj.fun.job.execute.impl.SchedulerJobExecutor;
import cn.nuofankj.fun.job.scheduler.impl.DoIntoDBJob;
import cn.nuofankj.fun.repository.HotVideoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

/**
 * 采集时光网影讯 -> 入库
 */
@Slf4j
@Component
@SuppressWarnings("unchecked")
public class CrawlerJob implements Runnable {


    private ISchedulerJobExecutor schedulerJobExecutor = new SchedulerJobExecutor(30, "crawlerJob-producer");

    @Autowired
    private HotVideoRepository hotVideoRepository;

    @Override
    public void run() {
            ConcurrentLinkedQueue<HotVideoEnt> hotVideoQueue = new ConcurrentLinkedQueue<>();

            // 采集时光网的影讯
            schedulerJobExecutor.execute(new HotVideoCrawlerJob(hotVideoQueue), 0, 20, TimeUnit.MINUTES);

            // 入库处理
            schedulerJobExecutor.execute(new DoIntoDBJob(hotVideoQueue, hotVideoRepository), 0, 20, TimeUnit.MINUTES);
    }
}
