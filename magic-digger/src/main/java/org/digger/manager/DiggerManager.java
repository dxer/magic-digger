package org.digger.manager;

import org.digger.model.FetchResult;
import org.digger.model.WebPage;
import org.digger.model.WebSite;
import org.digger.utils.BloomFilter;
import org.digger.utils.StringUtil;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 爬虫管理器
 * Created by linghf on 2015/5/29.
 */
public class DiggerManager {

    /**
     * WebSite 队列
     */
    private static BlockingQueue<WebSite> webSiteQueue = new LinkedBlockingQueue<WebSite>();

    /**
     * WebPage 队列
     */
    private static BlockingQueue<WebPage> webPageQueue = new LinkedBlockingQueue<WebPage>();

    /**
     * FetchResult 队列
     */
    private static BlockingQueue<FetchResult> fetchResultQueue = new LinkedBlockingQueue<FetchResult>();

    /**
     * 布隆过滤器用于url去重
     */
    private static BloomFilter webSiteBFilter = new BloomFilter();

    /**
     * fetch模块控制
     */
    private static boolean isFetchRun = true;

    /**
     * parse模块控制
     */
    private static boolean isParserRun = true;

    /**
     * store模块控制
     */
    private static boolean isStoreRun = true;

    public static BlockingQueue<WebSite> getWebSiteQueue() {
        return webSiteQueue;
    }

    public static void setWebSiteQueue(BlockingQueue<WebSite> webSiteQueue) {
        DiggerManager.webSiteQueue = webSiteQueue;
    }

    public static BlockingQueue<WebPage> getWebPageQueue() {
        return webPageQueue;
    }

    public static void setWebPageQueue(BlockingQueue<WebPage> webPageQueue) {
        DiggerManager.webPageQueue = webPageQueue;
    }

    public static BlockingQueue<FetchResult> getFetchResultQueue() {
        return fetchResultQueue;
    }

    public static void setFetchResultQueue(BlockingQueue<FetchResult> fetchResultQueue) {
        DiggerManager.fetchResultQueue = fetchResultQueue;
    }


    public static boolean isIsFetchRun() {
        return isFetchRun;
    }

    public static void setIsFetchRun(boolean isFetchRun) {
        DiggerManager.isFetchRun = isFetchRun;
    }

    public static boolean isIsParserRun() {
        return isParserRun;
    }

    public static void setIsParserRun(boolean isParserRun) {
        DiggerManager.isParserRun = isParserRun;
    }

    public static boolean isIsStoreRun() {
        return isStoreRun;
    }

    public static void setIsStoreRun(boolean isStoreRun) {
        DiggerManager.isStoreRun = isStoreRun;
    }

    public static void addWebSite(WebSite webSite) {
        if (webSite != null) {
            String url = webSite.getUrl();
            if (!StringUtil.isEmpty(url)) {
                if (!webSiteBFilter.isExit(url)) {
                    try {
                        webSiteBFilter.add(url);
                        webSiteQueue.put(webSite);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    public static WebSite getWebSite() {
        return webSiteQueue.poll();
    }

    public static int getWebSiteQueueSize() {
        return webSiteQueue.size();
    }

    public static void addWebPage(WebPage webPage) {
        try {
            webPageQueue.put(webPage);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static WebPage getWebPage() {
        return webPageQueue.poll();
    }

    public static int getWebPageQueueSize() {
        return webPageQueue.size();
    }

    public static void addFetchResult(FetchResult fetchResult) {
        try {
            fetchResultQueue.put(fetchResult);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static FetchResult getFetchResult() {
        return fetchResultQueue.poll();
    }

    public static int getFetchResultSize() {
        return fetchResultQueue.size();
    }
}
