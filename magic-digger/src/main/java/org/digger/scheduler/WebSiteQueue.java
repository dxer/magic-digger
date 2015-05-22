/**
 * Copyright (c) 2015 21CN.COM . All rights reserved.
 * 
 * Description: gl
 * 
 * <pre>
 * Modified log:
 * ------------------------------------------------------
 * Ver.		Date		Author			Description
 * ------------------------------------------------------
 * 1.0		2015年3月10日	linghf		created.
 * </pre>
 */
package org.digger.scheduler;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.digger.WebSite;

/**
 * @author linghf
 * @version 1.0
 * @class LinkQueue
 * @since 2015年5月19日
 */
public class WebSiteQueue {
    
    private static BlockingQueue<WebSite> queue = new LinkedBlockingQueue<WebSite>();
    
    
    public static synchronized WebSite poll(){
        return queue.poll(); 
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

    /* 已访问的 url 集合 */
    private static Set<String> visited = new HashSet<String>();

    /* 待访问的 url 集合 */
    private static BlockingQueue<String> unVisited = new LinkedBlockingQueue<String>();

    /**
     * 获得待访问的URL队列
     * 
     * @return
     */
    public static BlockingQueue<String> getUnVisitedQueue() {
        return unVisited;
    }

    /**
     * 添加到访问过的URL队列中
     * 
     * @param url
     */
    public static void addVisitedUrl(String url) {
        visited.add(url);
    }

    /**
     * 未访问的URL出队列
     * 
     * @return
     */
    public static String getUrl() {
        if (unVisited.size() <= 0) {
            return null;
        } else {
            String url = unVisited.poll(); // 取出一个未访问的url
            addVisitedUrl(url); // 添加到已读列表中
            return url;
        }
    }

    /**
     * 保证每个 url 只被访问一次
     * 
     * @param url
     */
    public static void addUrl(String url) {
        if (url != null && !url.trim().equals("") && !visited.contains(url) && !unVisited.contains(url))
            unVisited.add(url);
    }

    public static void addUrls(Set<String> urls) {
        if (urls != null && urls.size() > 0) {
            for (String url: urls) {
                addUrl(url);
            }
        }
    }

    /**
     * 获得已经访问的URL数目
     * 
     * @return
     */
    public static int getVisitedSize() {
        return visited.size();
    }

    /**
     * 判断未访问的URL队列中是否为空
     * 
     * @return
     */
    public static boolean isEmpty() {
        return unVisited.isEmpty();
    }
}
