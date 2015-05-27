package org.digger.scheduler;

import org.digger.WebPage;
import org.digger.WebSite;
import org.digger.utils.StringUtil;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by linghf on 2015/5/27.
 */
public class WebPageQueue {

    private static ConcurrentLinkedQueue<WebPage> webPages = new ConcurrentLinkedQueue<WebPage>();


    public static synchronized void add(WebPage webPage) {
        if (webPage != null) {
            webPages.add(webPage);
        }
    }

    public static synchronized void addAll(List<WebPage> newWebPage) {
        if (newWebPage != null && newWebPage.size() > 0) {
            webPages.addAll(newWebPage);
        }
    }


    public static synchronized boolean isEmpty() {
        return webPages.isEmpty();
    }

    /**
     * 获得需要处理的任务数
     *
     * @return
     */
    public static int size() {
        return webPages.size();
    }
}
