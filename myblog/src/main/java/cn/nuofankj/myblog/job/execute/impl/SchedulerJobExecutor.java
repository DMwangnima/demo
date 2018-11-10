package cn.nuofankj.myblog.job.execute.impl;

import cn.nuofankj.fun.job.execute.ISchedulerJobExecutor;
import cn.nuofankj.fun.job.scheduler.AbstractSchedulerJob;
import cn.nuofankj.fun.thread.ThreadFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author chenerzhu
 * @create 2018-08-30 12:15
 **/
public class SchedulerJobExecutor implements ISchedulerJobExecutor {

    private ScheduledExecutorService scheduledExecutorService;

    public SchedulerJobExecutor() {
    }

    public SchedulerJobExecutor(String threadFactory) {
        scheduledExecutorService = Executors.newScheduledThreadPool(10, new ThreadFactory(threadFactory));
    }

    public SchedulerJobExecutor(int corePoolSize, String threadFactory) {
        scheduledExecutorService = Executors.newScheduledThreadPool(corePoolSize, new ThreadFactory(threadFactory));
    }


    public void execute(AbstractSchedulerJob schedulerJob, long delayTime, long intervalTime, TimeUnit timeUnit) {
        scheduledExecutorService.scheduleAtFixedRate(schedulerJob, delayTime, intervalTime, timeUnit);
    }

    public void executeDelay(AbstractSchedulerJob schedulerJob, long delayTime, long intervalTime, TimeUnit timeUnit) {
        scheduledExecutorService.scheduleWithFixedDelay(schedulerJob, delayTime, intervalTime, timeUnit);
    }

    public void shutdown() {
        scheduledExecutorService.shutdown();
    }
}
