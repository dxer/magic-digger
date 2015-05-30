package org.digger.monitor;

import org.apache.log4j.Logger;
import org.digger.manager.DiggerResourceManager;
import org.digger.resource.queue.FetchResultQueue;
import org.digger.resource.queue.WebPageQueue;
import org.digger.resource.queue.WebSiteQueue;

/**
 * 监控线程
 * Created by linghf on 2015/5/29.
 */
public class DiggerMonitor implements Runnable {

    private Logger logger = Logger.getLogger("Monitor");

    private boolean keepRun = true;

    private Long waitTime = 30000L;

    private int checkCount = 0;

    @Override
    public void run() {
        logger.debug("Digger monitor is starting...");
        while (keepRun) {
            try {
                Thread.sleep(waitTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(DiggerResourceManager.getFetchResultSize()<=0 && DiggerResourceManager.getWebSiteSize()<=0 && DiggerResourceManager.getWebPageSize()<=0){
                checkCount++;
            }else{
                checkCount = 0;
                logger.debug("WebSite pool size：["+ WebSiteQueue.size()+"]");
                logger.debug("WebPage pool size: ["+WebPageQueue.size()+"]");
                logger.debug("FetcherResult pool size：["+FetchResultQueue.size()+"]");
            }

            if(checkCount ==3){
                keepRun = false;
                logger.debug("Digger Monitor is existing...");
            }
        }

    }
}
