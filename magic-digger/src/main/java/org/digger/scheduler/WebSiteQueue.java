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

    /* 已经访问过 */
    private static Set<String> visited = new HashSet<String>();


    public static synchronized WebSite poll() {
        return queue.poll();
    }

    public static synchronized void put(WebSite webSite) {
        if (webSite != null) {
            String url = webSite.getUrl();
            if (!StringUtil.isEmpty(url)) {
                if (!visited.contains(url)) {
                    try {
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

}
