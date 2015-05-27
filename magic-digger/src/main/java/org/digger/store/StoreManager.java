package org.digger.store;

import org.digger.fetch.Fecther;
import org.digger.utils.SimpleThreadPool;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * Created by linghf on 2015/5/27.
 */
public class StoreManager {

    private static SimpleThreadPool threadPool = new SimpleThreadPool(5, 50, 60);
    private Store store;


    public static AbstractStore getPipeline(String className) {
        AbstractStore store = null;
        try {
            store = (AbstractStore) Class.forName(className).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return store;
    }

    /**
     * 提交线程
     *
     * @return
     */
    public static Future<?> submit(Callable<?> call) {
        return threadPool.submit(call);
    }

    public static void start() {
        AbstractStore store = getPipeline("");
        for (int i = 0; i < 3; i++) {
            threadPool.addTask(store);
        }
    }

    public static void shutDown() {
        threadPool.shutdown();
    }
}
