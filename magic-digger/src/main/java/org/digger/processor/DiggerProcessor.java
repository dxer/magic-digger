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
package org.digger.processor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.digger.WebPage;
import org.digger.WebSite;
import org.digger.utils.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 
 * @class DiggerProcessor
 * @author linghf
 * @version 1.0
 * @since 2015年5月18日
 */
public class DiggerProcessor {

    /**
     * 
     * @param webSite
     * @param doc
     * @return
     */
    public WebPage parsePage(WebSite webSite, Document doc) {
        if (doc == null || webSite == null) {
            return null;
        }
        WebPage webPage = new WebPage();

        Map<String, String> fetchXpath = webSite.getFetchXPath();
        Map<String, String> fetchText = new HashMap<String, String>();
        if (fetchXpath != null && fetchXpath.size() > 0) {
            for (String label: fetchXpath.keySet()) {
                String xpath = fetchText.get(label);

                if (!StringUtil.isEmpty(xpath)) {
                    String text = getTextByXPath(doc, xpath);
                    fetchText.put(label, text);
                }
            }
        }
        webPage.setFetchText(fetchText);

        return null;
    }

    /**
     * 通过xpath获得相应的属性值
     * 
     * @param doc
     * @param xpath
     * @return
     */
    private String getTextByXPath(Document doc, String xpath) {
        if (StringUtil.isEmpty(xpath) || doc == null) {
            return null;
        }

        Elements e = doc.select(xpath);
        String content = null;
        if (e != null && e.get(0) != null) {
            content = e.get(0).html();
        }

        return content;
    }

    private Set<String> getLinks(Document doc, WebSite webSite) {
        if (doc == null && webSite != null) {
            return null;
        }

        Set<String> urls = new HashSet<String>();
        Elements links = doc.select("a");
        for (Element link: links) {
            urls.add(link.attr("href"));
        }

        if (urls != null && urls.size() > 0) {
            for (String url: urls) {
                

            }
        }

        return urls;
    }

    public boolean matcher(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        boolean ret = matcher.matches(); // 当条件满足时，将返回true，否则返回false
        return ret;
    }

    private WebSite test(WebSite webSite, String url) {
        if (!StringUtil.isEmpty(url)) {
            WebSite newWebSite = new WebSite();

        }
        return null;
    }

}
