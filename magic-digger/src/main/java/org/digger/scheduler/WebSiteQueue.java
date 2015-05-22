/**
 * Copyright (c) 2015 21CN.COM . All rights reserved.
 * 
 * Description: gl
 * 
 * <pre>
 * Modified log:
 * ------------------------------------------------------
 * Ver.		Date		Author			Description
 * ------------------------------------------------------
 * 1.0		2015年3月10日	linghf		created.
 * </pre>
 */
package org.digger.scheduler;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.digger.WebSite;

/**
 * @author linghf
 * @version 1.0
 * @class LinkQueue
 * @since 2015年5月19日
 */
public class WebSiteQueue {

    private static BlockingQueue<WebSite> queue = new LinkedBlockingQueue<WebSite>();

    public static synchronized WebSite poll() {

        return queue.poll();

    }

    public static synchronized void put(WebSite webSite) throws InterruptedException {
        if (webSite != null) {
            queue.put(webSite);
        }
    }

    public static synchronized boolean isEmpty() {
        return queue.isEmpty();
    }

}
