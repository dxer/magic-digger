package org.digger.fetch;

import org.digger.manager.DiggerManager;
import org.digger.model.WebSite;
import org.digger.parse.DiggerParser;
import org.digger.resource.WebSiteQueue;
import org.digger.utils.StringUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.logging.Logger;

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
        while (DiggerManager.isIsFetchRun()) {
            try {
                WebSite webSite = DiggerManager.getWebSite(); // 从队列取出任务去执行
                if (webSite != null) {
                    String url = webSite.getUrl();
                    if (!StringUtil.isEmpty(url)) {
                        Document doc = download(url, Proxy.getRandomUa(), Proxy.getRandomIP());
                        if (doc != null) {
                            new DiggerParser().parsePage(webSite, doc);
                            Thread.sleep(1000);
                        } else {
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
