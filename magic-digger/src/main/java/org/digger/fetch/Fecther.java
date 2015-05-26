/**
 * Copyright (c) 2015 21CN.COM . All rights reserved.
 * 
 * Description: magic-digger
 * 
 * <pre>
 * Modified log:
 * ------------------------------------------------------
 * Ver.		Date		Author			Description
 * ------------------------------------------------------
 * 1.0		2015年5月18日	linghf		created.
 * </pre>
 */
package org.digger.fetch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.digger.WebSite;
import org.digger.processor.DiggerProcessor;
import org.digger.scheduler.WebSiteQueue;
import org.digger.utils.StringUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * 
 * @class Fecther
 * @author linghf
 * @version 1.0
 * @since 2015年5月18日
 */
public class Fecther {

    private static Logger logger = Logger.getLogger(Fecther.class.getName());

    private static boolean keepRun = true;

    private int threadNum = 3;

    public Document download(String url) {
        Document doc = null;
        try {
            doc = Jsoup.connect(url).userAgent(Proxy.getRandomUa()).header("X-Forwarded-For", Proxy.getRandomIP())
                            .timeout(6000).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doc;
    }

    /**
     * 启动工作
     */
    public void startWork() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 200, TimeUnit.MILLISECONDS,
                        new ArrayBlockingQueue<Runnable>(5));

        for (int i = 0; i < threadNum; i++) {
            HandlerTask task = new HandlerTask();
            executor.execute(task);
        }
        executor.shutdown();
    }

    public class HandlerTask implements Runnable {

        @Override
        public void run() {
            while (keepRun) {
                try {
                    WebSite webSite = WebSiteQueue.poll();
                    if (webSite == null) {
                        Thread.sleep(3000);
                        continue;
                    }

                    String url = webSite.getUrl();
                    if (!StringUtil.isEmpty(url)) {
                        Document doc = download(url);
                        if (doc != null) {
                            new DiggerProcessor().parsePage(webSite, doc);
                        } else {
                            continue;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        // 暨南大学
        // WebSite webSite = new WebSite();
        // webSite.setUrl("http://career.jnu.edu.cn/showmore.php?actiontype=0&pg=1");
        // webSite.setDomain("http://career.jnu.edu.cn");
        // Map<String, String> fetchXPath = new HashMap<String, String>();
        // // fetchXPath.put("content", "//*[@id=\"content_border\"]");
        // fetchXPath.put("content", "#content_border > div.page3_content");
        // webSite.setFetchXPath(fetchXPath);
        // List<String> textLinkFilters = new ArrayList<String>();
        // textLinkFilters.add("http://career.jnu.edu.cn/showarticle.php\\?actiontype=0&id=[\\s\\S]*");
        // webSite.setTextLinkFilters(textLinkFilters);
        //
        // WebSiteQueue.put(webSite);
        //
        // new Fecther().startWork();

        // 广东工业大学
        // WebSite webSite = new WebSite();
        // webSite.setUrl("http://job.gdut.edu.cn/activity/activity-list.php?id=1&page=1");
        // webSite.setDomain("http://job.gdut.edu.cn");
        // Map<String, String> fetchXPath = new HashMap<String, String>();
        // fetchXPath.put("content", "body > div.activity-show > div.left > div");
        // webSite.setFetchXPath(fetchXPath);
        // List<String> textLinkFilters = new ArrayList<String>();
        // textLinkFilters.add("http://job.gdut.edu.cn/activity/activity-show.php\\?id=[\\s\\S]*");
        // webSite.setTextLinkFilters(textLinkFilters);
        //
        // WebSiteQueue.put(webSite);
        //
        // new Fecther().startWork();

        // 开源中国
        // WebSite webSite = new WebSite();
        // webSite.setUrl("http://www.oschina.net/");
        // webSite.setDomain("http://www.oschina.net");
        // Map<String, String> fetchCSSPath = new HashMap<String, String>();
        // fetchCSSPath.put("content", "#NewsChannel > div.NewsBody > div > div.NewsEntity > h1");
        // webSite.setFetchCSSPaths(fetchCSSPath);
        // List<String> textLinkFilters = new ArrayList<String>();
        // textLinkFilters.add("http://www.oschina.net/news/[\\s\\S]*");
        // webSite.setTextLinkFilters(textLinkFilters);
        //
        // WebSiteQueue.put(webSite);
        //
        // new Fecther().startWork();
        
        
        WebSite webSite = new WebSite();
        webSite.setUrl("http://www.oschina.net/");
        webSite.setDomain("http://www.oschina.net");
        Map<String, String> fetchCSSPath = new HashMap<String, String>();
        fetchCSSPath.put("content", "#NewsChannel > div.NewsBody > div > div.NewsEntity > h1");
        webSite.setFetchCSSPaths(fetchCSSPath);
        List<String> textLinkFilters = new ArrayList<String>();
        textLinkFilters.add("http://www.oschina.net/news/[\\s\\S]*");
        webSite.setTextLinkFilters(textLinkFilters);

        WebSiteQueue.put(webSite);

        new Fecther().startWork();

    }

}
