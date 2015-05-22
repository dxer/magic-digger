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
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.digger.WebSite;
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

    // private static Logger logger = Logger.getLogger(Fecther.class.getName());

    private static boolean keepRun = false;

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

}
