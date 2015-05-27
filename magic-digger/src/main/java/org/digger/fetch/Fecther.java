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
 * @author linghf
 * @version 1.0
 * @class Fecther
 * @since 2015年5月18日
 */
public class Fecther implements Runnable {

    private static Logger logger = Logger.getLogger(Fecther.class.getName());

    /**
     * @param url
     * @param userAgent
     * @param ip
     * @return
     */
    public Document download(String url, String userAgent, String ip) {
        Document doc = null;
        try {
            doc = Jsoup.connect(url).userAgent(userAgent).header("X-Forwarded-For", ip).timeout(6000).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doc;
    }


    @Override
    public void run() {
        while (FetchManager.isKeepRun()) {
            try {
                WebSite webSite = WebSiteQueue.poll(); // 从队列取出任务去执行
                if (webSite == null) {
                    Thread.sleep(3000);
                    continue;
                }

                String url = webSite.getUrl();
                if (!StringUtil.isEmpty(url)) {
                    Document doc = download(url, Proxy.getRandomUa(), Proxy.getRandomIP());
                    if (doc != null) {
                        new DiggerProcessor().parsePage(webSite, doc);
                        Thread.sleep(1000);
                    } else {
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
