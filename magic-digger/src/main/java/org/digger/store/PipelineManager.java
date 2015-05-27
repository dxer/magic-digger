package org.digger.store;

import org.digger.fetch.Fecther;
import org.digger.utils.SimpleThreadPool;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * Created by linghf on 2015/5/27.
 */
public class PipelineManager {

    private static SimpleThreadPool threadPool = new SimpleThreadPool(5, 50, 60);


    public Pipeline getPipeline(String str) {
        return null;
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
        for (int i = 0; i < 3; i++) {
            threadPool.addTask(new Fecther());
        }
    }

    public static void shutDown() {
        threadPool.shutdown();
    }
}
