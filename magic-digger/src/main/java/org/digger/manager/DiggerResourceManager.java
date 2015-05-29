package org.digger.manager;

import org.digger.fetch.Fetcher;
import org.digger.model.FetchResult;
import org.digger.model.WebPage;
import org.digger.model.WebSite;
import org.digger.parse.AbstractParser;
import org.digger.resource.pool.FetchThreadPool;
import org.digger.resource.pool.ParseThreadPool;
import org.digger.resource.pool.StoreThreadPool;
import org.digger.resource.queue.FetchResultQueue;
import org.digger.resource.queue.WebPageQueue;
import org.digger.resource.queue.WebSiteQueue;
import org.digger.store.AbstractStorer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 资源管理器
 * Created by linghf on 2015/5/29.
 */
public class DiggerResourceManager {

    private static Condition fetcherLock = new ReentrantLock().newCondition();

    private static Condition parseLock = new ReentrantLock().newCondition();

    private static Condition storeLock = new ReentrantLock().newCondition();

    public static Condition getFetcherLock() {
        return fetcherLock;
    }

    public static Condition getParseLock() {
        return parseLock;
    }

    public static Condition getStoreLock() {
        return storeLock;
    }


    public static void addWebSite(WebSite webSite) {
        WebSiteQueue.put(webSite);
    }

    public static void addWePage(WebPage webPage) {
        WebPageQueue.put(webPage);
    }

    public static void addFetchResult(FetchResult fetchResult) {
        FetchResultQueue.put(fetchResult);
    }

    public static WebSite getWebSite() {
        return WebSiteQueue.poll();
    }

    public static WebPage getWebPage() {
        return WebPageQueue.poll();
    }

    public static FetchResult getFetchResult() {
        return FetchResultQueue.poll();
    }


    public static int getWebSiteSize() {
        return WebSiteQueue.size();
    }


    public static int getWebPageSize() {
        return WebPageQueue.size();
    }

    public static int getFetchResultSize() {
        return FetchResultQueue.size();
    }

    public static void addToPool(Runnable runnable) {
        if (runnable instanceof Fetcher) {
            FetchThreadPool.getFetchThreadPool().submit(runnable);
        } else if (runnable instanceof AbstractParser) {
            ParseThreadPool.getParseThreadPool().submit(runnable);
        } else if (runnable instanceof AbstractStorer) {
            StoreThreadPool.getStoreThreadPool().submit(runnable);
        }
    }
}
