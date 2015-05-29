package org.digger.resource;

import org.digger.model.FetchResult;
import org.digger.model.WebSite;
import org.digger.utils.StringUtil;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by linghf on 2015/5/29.
 */
public class FetchResultQueue {

    /**
     * FetchResult 队列
     */
    private static BlockingQueue<FetchResult> fetchResultQueue = new LinkedBlockingQueue<FetchResult>();

    public static BlockingQueue<FetchResult> getFetchResultQueue() {
        return fetchResultQueue;
    }

    public static void put(FetchResult fetchResult) {
        if (fetchResult != null) {
            try {
                fetchResultQueue.put(fetchResult);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static FetchResult poll() {
        return fetchResultQueue.poll();
    }

    public static int size() {
        return fetchResultQueue.size();
    }

    public static boolean isEmpty() {
        return fetchResultQueue.isEmpty();
    }
}
