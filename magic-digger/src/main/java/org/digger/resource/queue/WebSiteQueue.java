package org.digger.resource.queue;

import org.digger.model.WebSite;
import org.digger.utils.BloomFilter;
import org.digger.utils.StringUtil;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by linghf on 2015/5/29.
 */
public class WebSiteQueue {
    /**
     * WebSite 队列
     */
    private static BlockingQueue<WebSite> webSiteQueue = new LinkedBlockingQueue<WebSite>();

    private static boolean isFirst = true;

    /**
     * 布隆过滤器用于url去重
     */
    private static BloomFilter webSiteBFilter = new BloomFilter();


    public static BlockingQueue<WebSite> getWebSiteQueue() {
        return webSiteQueue;
    }

    /**
     * 添加WebSited到队列
     *
     * @param webSite
     */
    public static void put(WebSite webSite) {
        if (webSite != null) {
            String url = webSite.getUrl();
            if (!StringUtil.isEmpty(url)) {
                if (!webSiteBFilter.isExit(url)) {
                    try {
                        webSiteBFilter.add(url);
                        webSiteQueue.put(webSite);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 从队列获取website
     *
     * @return
     */
    public static WebSite take() {
        WebSite webSite = null;
        try {
            webSite = webSiteQueue.take();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return webSite;
    }

    public static int size() {
        return webSiteQueue.size();
    }

    public static boolean isEmpty() {
        return webSiteQueue.isEmpty();
    }
}
