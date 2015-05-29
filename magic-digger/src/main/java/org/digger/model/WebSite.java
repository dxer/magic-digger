package org.digger.model;

import org.digger.utils.StringUtil;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 要抓取的信息
 *
 * @author linghf
 * @version 1.0
 * @class WebSite
 * @since 2015年5月18日
 */
public class WebSite implements Serializable {

    private static final long serialVersionUID = -7340509453613547007L;

    /**
     * 域名
     */
    private String domain;

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
     * 保存路径
     */
    private String savePath = null;

    /**
     * 是否是正文内容页面
     */
    private boolean isMainPage = false;

    /**
     * 正文网址过滤
     */
    private List<String> textLinkFilters;

    /**
     * 抓取xpath路径<名字，xpath>
     */
    private Map<String, String> fetchXPaths;

    /**
     * css path 路径<标签, cssPath>
     */
    private Map<String, String> fetchCSSPaths;

    public static void main(String[] args) {
        String url = "http://sss.www/ssss";
        if (!StringUtil.isEmpty(url)) {
            String[] s = url.split("/");
            if (s != null && s.length >= 3) {
                System.out.println(s[0] + "//" + s[2]);
            }
        }
    }

    public String getDomain() {
        if (StringUtil.isEmpty(domain)) {
            if (!StringUtil.isEmpty(url)) {
                String[] s = url.split("/");
                if (s != null && s.length >= 3) {
                    domain = s[0] + "//" + s[2];
                }
            }
        }

        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

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

    public List<String> getTextLinkFilters() {
        return textLinkFilters;
    }

    public void setTextLinkFilters(List<String> textLinkFilters) {
        this.textLinkFilters = textLinkFilters;
    }

    public Map<String, String> getFetchXPaths() {
        return fetchXPaths;
    }

    public void setFetchXPaths(Map<String, String> fetchXPaths) {
        this.fetchXPaths = fetchXPaths;
    }

    public Map<String, String> getFetchCSSPaths() {
        return fetchCSSPaths;
    }

    public void setFetchCSSPaths(Map<String, String> fetchCSSPaths) {
        this.fetchCSSPaths = fetchCSSPaths;
    }

    public boolean isMainPage() {
        return isMainPage;
    }

    public void setMainPage(boolean isMainPage) {
        this.isMainPage = isMainPage;
    }

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

}
