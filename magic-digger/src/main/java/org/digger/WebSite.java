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

import java.util.List;
import java.util.Map;

/**
 * 
 * @class WebSite
 * @author linghf
 * @version 1.0
 * @since 2015年5月18日
 */
public class WebSite {

    /**
     * 抓取的url
     */
    private String url;

    /**
     * 优先级
     */
    private int priority = 0;

    /**
     * 抓取深度
     */
    private int depth = 0;

    /**
     * 是否保存文件
     */
    private boolean isSaveFile = false;

    /**
     * 网址过滤
     */
    private List<String> linkFilterRules;

    /**
     * 抓取标签<名字，xpath>
     */
    private Map<String, String> fetchXPath;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public boolean isSaveFile() {
        return isSaveFile;
    }

    public void setSaveFile(boolean isSaveFile) {
        this.isSaveFile = isSaveFile;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public List<String> getLinkFilterRules() {
        return linkFilterRules;
    }

    public void setLinkFilterRules(List<String> linkFilterRules) {
        this.linkFilterRules = linkFilterRules;
    }

    public Map<String, String> getFetchXPath() {
        return fetchXPath;
    }

    public void setFetchXPath(Map<String, String> fetchXPath) {
        this.fetchXPath = fetchXPath;
    }

}
