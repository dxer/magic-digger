package org.digger.resource.queue;

import org.digger.manager.DiggerResourceManager;
import org.digger.model.WebPage;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by linghf on 2015/5/29.
 */
public class WebPageQueue {

    /**
     * WebPage 队列
     */
    private static BlockingQueue<WebPage> webPageQueue = new LinkedBlockingQueue<WebPage>();

    public static BlockingQueue<WebPage> getWebPageQueue() {
        return webPageQueue;
    }

    public static void put(WebPage webPage) {
        if (webPage != null) {
            try {
                webPageQueue.put(webPage);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static WebPage poll() {
        return webPageQueue.poll();
    }

    public static int size() {
        return webPageQueue.size();
    }

    public static boolean isEmpty() {
        return webPageQueue.isEmpty();
    }
}
