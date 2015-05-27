package org.digger.processor;

import com.alibaba.fastjson.JSON;
import org.digger.WebPage;
import org.digger.WebSite;
import org.digger.scheduler.WebSiteQueue;
import org.digger.utils.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author linghf
 * @version 1.0
 * @class DiggerProcessor
 * @since 2015年5月18日
 */
public class DiggerProcessor {

    /**
     * 页面分析
     *
     * @param webSite
     * @param doc
     * @return
     */
    public WebPage parsePage(WebSite webSite, Document doc) {
        if (doc == null || webSite == null) {
            return null;
        }
        /*分析页面链接*/
        analyseLinks(doc, webSite);
        Map<String, String> fetchText = new HashMap<String, String>();

        if (webSite.isMainPage()) {
            WebPage webPage = new WebPage();

            /**
             * xpath解析
             */
            // Map<String, String> fetchXpath = webSite.getFetchXPaths();
            // if (fetchXpath != null && fetchXpath.size() > 0) {
            // for (String label: fetchXpath.keySet()) {
            // String xpath = fetchXpath.get(label);
            // if (!StringUtil.isEmpty(xpath)) {
            //
            // }
            // }
            // }

            /**
             * css path解析
             */
            Map<String, String> fetchCSSPaths = webSite.getFetchCSSPaths();
            if (fetchCSSPaths != null && fetchCSSPaths.size() > 0) {
                for (String label : fetchCSSPaths.keySet()) {
                    String cssPath = fetchCSSPaths.get(label);
                    if (!StringUtil.isEmpty(cssPath)) {
                        String text = getTextByCSSPath(doc, cssPath);
                        System.out.println(text);
                        fetchText.put(label, text);
                    }
                }
            }

            webPage.setFetchText(fetchText);
            System.out.println(JSON.toJSONString(webPage));
        }

        return null;
    }

    /**
     * @return
     */
    public String getTextByXPath() {
        return null;
    }

    /**
     * 通过cssQuery获得相应的属性值
     *
     * @param doc
     * @param cssQuery
     * @return
     */
    public String getTextByCSSPath(Document doc, String cssQuery) {
        if (StringUtil.isEmpty(cssQuery) || doc == null) {
            return null;
        }

        Elements e = doc.select(cssQuery);
        String content = null;
        System.out.println(e.size() + "----------------");
        if (e != null && e.size() > 0) {
            if (e.get(0) != null) {
                content = e.get(0).html();
            }
        }

        return content;
    }

    /**
     * 分析网页链接
     *
     * @param doc
     * @param webSite
     * @return
     */
    public Set<String> analyseLinks(Document doc, WebSite webSite) {
        if (doc == null && webSite != null) {
            return null;
        }

        Set<String> urls = new HashSet<String>();
        Elements links = doc.select("a");
        for (Element link : links) {
            urls.add(link.attr("href"));
        }

        String domain = webSite.getDomain();
        if (urls != null && urls.size() > 0) {
            List<String> linkFilters = webSite.getTextLinkFilters();
            for (String url : urls) {
                url = fillUrl(domain, url);
                if (linkFilters != null && linkFilters.size() > 0) {
                    for (String regex : linkFilters) {
                        if (matcher(url, regex)) { // 符合要求的url，需要再次进行抓取
                            WebSite newSite = buildNewWebSite(webSite, url);
                            newSite.setMainPage(true);
                            WebSiteQueue.put(newSite);
                            // System.out.println("add: " + url);
                        }
                    }
                }
            }
        }

        return urls;
    }

    /**
     * 填充url
     *
     * @param domain
     * @param url
     * @return
     */
    public static String fillUrl(String domain, String url) {
        if (url.startsWith("http://") || url.startsWith("https://")) {
            return url;
        }

        if (StringUtil.isEmpty(domain)) {
            return url;
        } else {
            if (url.startsWith("/")) {
                url = domain + url;
            } else {
                url = domain + "/" + url;
            }
            return url;
        }
    }

    /**
     * 正则匹配
     *
     * @param input
     * @param regex
     * @return
     */
    public static boolean matcher(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        boolean ret = matcher.matches(); // 当条件满足时，将返回true，否则返回false
        return ret;
    }

    /**
     * 根据生成的url生成新的要抓取的新的页面
     *
     * @param webSite
     * @param url
     * @return
     */
    private WebSite buildNewWebSite(WebSite webSite, String url) {
        if (!StringUtil.isEmpty(url)) {
            WebSite newWebSite = new WebSite();
            newWebSite.setUrl(url);
            newWebSite.setDepth(webSite.getDepth());
            newWebSite.setDomain(webSite.getDomain());
            newWebSite.setFetchXPaths(webSite.getFetchXPaths());
            newWebSite.setFetchCSSPaths(webSite.getFetchCSSPaths());
            newWebSite.setPriority(webSite.getPriority());
            newWebSite.setSaveFile(webSite.isSaveFile());
            newWebSite.setTextLinkFilters(webSite.getTextLinkFilters());
            return newWebSite;
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(matcher("http://job.gdut.edu.cn/activity/activity-show.php?id=3239",
                "http://job.gdut.edu.cn/activity/activity-show.php\\?id=[\\s\\S]*"));
    }

}
