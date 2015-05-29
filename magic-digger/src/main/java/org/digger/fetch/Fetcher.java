package org.digger.fetch;

import org.apache.log4j.Logger;
import org.digger.Constants;
import org.digger.manager.DiggerManager;
import org.digger.manager.DiggerResourceManager;
import org.digger.model.WebPage;
import org.digger.model.WebSite;
import org.digger.utils.StringUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;


/**
 * @author linghf
 * @version 1.0
 * @class Fecther
 * @since 2015年5月18日
 */
public class Fetcher implements Runnable {

    private static Logger logger = Logger.getLogger(Fetcher.class.getName());

    private String html;

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


    public String download(String urlStr) {
        String html = null;

        InputStreamReader isr = null;
        BufferedReader br = null;
        try {
            URL url = new URL(urlStr);
            URLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(7000);
            conn.setRequestProperty(Constants.USER_AGENT, Proxy.getRandomUa()); // 设置User-Agent
            conn.setRequestProperty(Constants.XXF, Proxy.getRandomIP()); // 设置ip
            isr = new InputStreamReader(conn.getInputStream());
            br = new BufferedReader(isr);
            String s = null;
            StringBuffer htmlContent = new StringBuffer();
            while ((s = br.readLine()) != null) {
                htmlContent = htmlContent.append(s);
            }
            html = htmlContent.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (isr != null) {
                try {
                    isr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return html;
    }


    @Override
    public void run() {
        while (DiggerManager.isIsFetcherKeepRun()) {
            try {
                WebSite webSite = DiggerResourceManager.getWebSite(); // 从队列取出任务去执行
                if (webSite != null) {
                    logger.debug("fetch url: " + webSite.getUrl());
                    String url = webSite.getUrl();
                    if (!StringUtil.isEmpty(url)) {
                        String html = download(url);
                        if (!StringUtil.isEmpty(html) && DiggerManager.isIsParserRunning()) {
                            WebPage webPage = new WebPage();
                            webPage.setWebSite(webSite);
                            webPage.setHtml(html);

                            DiggerResourceManager.addWePage(webPage);
                            logger.debug("add WebPage to resource.");
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
