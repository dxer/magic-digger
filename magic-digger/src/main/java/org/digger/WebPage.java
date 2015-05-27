package org.digger;

import java.util.Date;
import java.util.Map;

/**
 * 
 * @class WebPage
 * @author linghf
 * @version 1.0
 * @since 2015年5月18日
 */
public class WebPage {

    /**
     * 抓取的url
     */
    private String url;

    /**
     * 抓取的内容(根据在定制爬虫的时候设置的抓取规则xpath和css path的)
     */
    private Map<String, String> fetchText;

    /**
     * 抓取时间
     */
    private Date fetchTime = new Date();

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, String> getFetchText() {
        return fetchText;
    }

    public void setFetchText(Map<String, String> fetchText) {
        this.fetchText = fetchText;
    }

    /**
     * 根据标签获得对应的值
     * 
     * @param label
     * @return
     */
    public String get(String label) {
        if (fetchText != null && fetchText.size() > 0) {
            if (fetchText.containsKey(label)) {
                return fetchText.get(label);
            }
        }

        return null;
    }

    public Date getFetchTime() {
        return fetchTime;
    }

    public void setFetchTime(Date fetchTime) {
        this.fetchTime = fetchTime;
    }

}
