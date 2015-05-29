package org.digger.manager;

import org.digger.model.FetchResult;
import org.digger.model.WebPage;
import org.digger.model.WebSite;
import org.digger.resource.FetchResultQueue;
import org.digger.resource.WebPageQueue;
import org.digger.resource.WebSiteQueue;


/**
 * 爬虫管理器
 * Created by linghf on 2015/5/29.
 */
public class DiggerManager {


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

    public static void startFetcher() {

    }

    public static void startParser() {

    }

    public static void startStorer() {

    }

}
