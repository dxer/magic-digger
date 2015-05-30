package org.digger.manager;

import org.digger.conf.Config;
import org.digger.fetch.Fetcher;
import org.digger.monitor.DiggerMonitor;
import org.digger.parse.AbstractParser;
import org.digger.parse.DiggerParser;
import org.digger.store.AbstractStorer;
import org.digger.store.FileStorer;

/**
 * 爬虫管理器
 * Created by linghf on 2015/5/29.
 */
public class DiggerManager {


    /**
     * fetch模块控制
     */
    private static boolean isFetcherKeepRun = true;

    /**
     * parse模块控制
     */
    private static boolean isParserKeepRun = true;

    /**
     * store模块控制
     */
    private static boolean isStorerKeepRun = true;

    /**
     * fetcher运行状态
     */
    private static boolean isFetcherRunning = false;

    /**
     * parser运行状态
     */
    private static boolean isParserRunning = false;

    /**
     * storer运行状态
     */
    private static boolean isStorerRunning = false;


    public static boolean isIsFetcherKeepRun() {
        return isFetcherKeepRun;
    }

    public static void setIsFetcherKeepRun(boolean isFetcherKeepRun) {
        DiggerManager.isFetcherKeepRun = isFetcherKeepRun;
    }

    public static boolean isIsParserKeepRun() {
        return isParserKeepRun;
    }

    public static void setIsParserKeepRun(boolean isParserKeepRun) {
        DiggerManager.isParserKeepRun = isParserKeepRun;
    }

    public static boolean isIsStorerKeepRun() {
        return isStorerKeepRun;
    }

    public static void setIsStorerKeepRun(boolean isStorerKeepRun) {
        DiggerManager.isStorerKeepRun = isStorerKeepRun;
    }


    public static boolean isIsFetcherRunning() {
        return isFetcherRunning;
    }

    public static void setIsFetcherRunning(boolean isFetcherRunning) {
        DiggerManager.isFetcherRunning = isFetcherRunning;
    }

    public static boolean isIsParserRunning() {
        return isParserRunning;
    }

    public static void setIsParserRunning(boolean isParserRunning) {
        DiggerManager.isParserRunning = isParserRunning;
    }

    public static boolean isIsStorerRunning() {
        return isStorerRunning;
    }

    public static void setIsStorerRunning(boolean isStorerRunning) {
        DiggerManager.isStorerRunning = isStorerRunning;
    }

    public static void startFetcher() {
        setIsFetcherRunning(true);
        Fetcher fetcher = new Fetcher();
        int threadNum = Config.getFetcherThreadNum();
        for (int i = 0; i < 3; i++) {
            DiggerResourceManager.addToPool(fetcher);
        }
    }

    public static void startParser() {
        setIsParserRunning(true);
        AbstractParser parser = new DiggerParser();
        int threadNum = Config.getParserThreadNum();
        for (int i = 0; i < threadNum; i++) {
            DiggerResourceManager.addToPool(parser);
        }
    }

    public static void startStorer() {
        setIsStorerRunning(true);
        AbstractStorer storer = new FileStorer();
        int threadNum = Config.getStorerThreadNum();
        for (int i = 0; i < threadNum; i++) {
            DiggerResourceManager.addToPool(storer);
        }
    }

    public static void startMonitor(){
        if(Config.isMonitorEnable()){
            DiggerMonitor monitor = new DiggerMonitor();
            new Thread(monitor).start();
        }
    }

    public static void startAll() {
        startMonitor();
        startFetcher();
        startParser();
        startStorer();
    }


}
