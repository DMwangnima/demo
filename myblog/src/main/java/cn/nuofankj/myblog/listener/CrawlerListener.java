package cn.nuofankj.myblog.listener;

import cn.nuofankj.fun.job.crawler.DBCrawlVideo;
import cn.nuofankj.fun.job.crawler.time.CrawlerJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * 采集监听器
 */
@Slf4j
@WebListener
public class CrawlerListener implements ServletContextListener {

    @Autowired
    private CrawlerJob crawlerJob;

    @Autowired
    private DBCrawlVideo dbCrawlVideo;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        log.debug("CrawlerListener contextInitialized");
        new Thread(crawlerJob).start();
        new Thread(dbCrawlVideo).start();

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        log.debug("CrawlerListener contextDestroyed");
    }
}
