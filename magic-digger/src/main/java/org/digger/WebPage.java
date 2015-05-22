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
     * 抓取时间
     */
    private Date fetchTime = new Date();

    private Map<String, String> fetchText;

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
