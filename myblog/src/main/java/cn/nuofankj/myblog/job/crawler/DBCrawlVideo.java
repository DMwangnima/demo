package cn.nuofankj.myblog.job.crawler;

import cn.nuofankj.fun.entity.HotVideoEnt;
import cn.nuofankj.fun.entity.ShowVideoEnt;
import cn.nuofankj.fun.job.crawler.halihali.HaliCrawlerJob;
import cn.nuofankj.fun.job.crawler.q2029.Q2029CrawlerJob;
import cn.nuofankj.fun.job.execute.ISchedulerJobExecutor;
import cn.nuofankj.fun.job.execute.impl.SchedulerJobExecutor;
import cn.nuofankj.fun.job.scheduler.impl.DoUpdateDbJob;
import cn.nuofankj.fun.job.scheduler.impl.DoIntoQueueJob;
import cn.nuofankj.fun.repository.HotVideoRepository;
import cn.nuofankj.fun.repository.ShowVideoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

/**
 * 数据库放入队列 -> 哈里哈里视频采集
 */
@Slf4j
@Component
public class DBCrawlVideo implements Runnable{

    private ISchedulerJobExecutor schedulerJobExecutor = new SchedulerJobExecutor(30, "crawlerJob-producer");

    @Autowired
    private HotVideoRepository hotVideoRepository;

    @Autowired
    private ShowVideoRepository showVideoRepository;

    @Override
    public void run() {
        ConcurrentLinkedQueue<HotVideoEnt> hotVideoQueue = new ConcurrentLinkedQueue<>();
        ConcurrentLinkedQueue<ShowVideoEnt> showVideoQueue = new ConcurrentLinkedQueue<>();

        // 数据库 -> 队列
        schedulerJobExecutor.execute(new DoIntoQueueJob(hotVideoQueue, hotVideoRepository), 0, 20, TimeUnit.MINUTES);

        // 队列 采集哈利哈利
        schedulerJobExecutor.execute(new HaliCrawlerJob(hotVideoQueue, showVideoQueue), 0, 20, TimeUnit.MINUTES);

        // 队列 采集q2029
        schedulerJobExecutor.execute(new Q2029CrawlerJob(hotVideoQueue, showVideoQueue), 0, 20, TimeUnit.MINUTES);

        // 队列 -> 数据库
        schedulerJobExecutor.execute(new DoUpdateDbJob(hotVideoQueue, showVideoQueue, showVideoRepository, hotVideoRepository), 0, 20, TimeUnit.MINUTES);
    }
}
