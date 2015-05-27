package org.digger.scheduler;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.digger.WebSite;
import org.digger.utils.StringUtil;

/**
 * @author linghf
 * @version 1.0
 * @class LinkQueue
 * @since 2015年5月19日
 */
public class WebSiteQueue {

    private static BlockingQueue<WebSite> queue = new LinkedBlockingQueue<WebSite>();

    private static BloomFilter bFilter = new BloomFilter();


    public static synchronized WebSite poll() {
        return queue.poll();
    }

    public static synchronized void put(WebSite webSite) {
        if (webSite != null) {
            String url = webSite.getUrl();
            if (!StringUtil.isEmpty(url)) {
                if (!bFilter.isExit(url)) {
                    try {
                        bFilter.add(url);
                        queue.put(webSite);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static synchronized boolean isEmpty() {
        return queue.isEmpty();
    }

    /**
     * 获得需要处理的任务数
     *
     * @return
     */
    public static int size() {
        return queue.size();
    }

}
