package org.digger.resource.pool;

import org.digger.utils.SimpleThreadPool;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by linghf on 2015/5/29.
 */
public class ParseThreadPool {
    private static SimpleThreadPool parseThreadPool = new SimpleThreadPool(5, 20, 3000L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(2000));

    public static SimpleThreadPool getParseThreadPool() {
        return parseThreadPool;
    }
}
