package org.digger.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 抓取的页面信息
 *
 * @author linghf
 * @version 1.0
 * @class WebPage
 * @since 2015年5月18日
 */
public class WebPage implements Serializable {

    private static final long serialVersionUID = -7340509453613547007L;

    private WebSite webSite;

    private String html;

    private Date fetchTime = new Date();


    public WebSite getWebSite() {
        return webSite;
    }

    public void setWebSite(WebSite webSite) {
        this.webSite = webSite;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public Date getFetchTime() {
        return fetchTime;
    }

    public void setFetchTime(Date fetchTime) {
        this.fetchTime = fetchTime;
    }
}
