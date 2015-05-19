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

    private String getTextByXPath(Document doc, String xpath) {
        if (StringUtil.isEmpty(xpath) || doc == null) {
            return null;
        }

        Elements e = doc.select(xpath);
        String content = e.get(0).html();

        return content;
    }

    private Set<String> getLinks(Document doc) {
        if (doc == null) {
            return null;
        }

        Set<String> urls = new HashSet<String>();
        Elements links = doc.select("a");
        for (Element link: links) {
            urls.add(link.attr("href"));

        }
        return urls;
    }

}
