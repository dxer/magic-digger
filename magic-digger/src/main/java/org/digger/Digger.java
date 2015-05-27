package org.digger;

import org.digger.fetch.Fecther;
import org.digger.fetch.FetchManager;
import org.digger.scheduler.WebSiteQueue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author linghf
 * @version 1.0
 * @class Digger
 * @since 2015年5月22日
 */
public class Digger {

    private void addWebsite(WebSite webSite) {
        if (webSite != null) {
            WebSiteQueue.put(webSite);
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

        FetchManager.start();

    }
}
