package org.digger.fetch;

import org.digger.utils.SimpleThreadPool;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * Created by linghf on 2015/5/27.
 */
public class FetchManager {

    private static boolean keepRun = true;

    private static SimpleThreadPool threadPool = new SimpleThreadPool(5, 50, 60);


    public static boolean isKeepRun() {
        return keepRun;
    }

    public static void setKeepRun(boolean keepRun) {
        FetchManager.keepRun = keepRun;
    }

    /**
     * 提交线程
     *
     * @return
     */
    public static Future<?> submit(Callable<?> call) {
        return threadPool.submit(call);
    }

    public static void shutDown() {
        threadPool.shutdown();
    }

    public static void start() {
        for (int i = 0; i < 3; i++) {
            threadPool.addTask(new Fecther());
        }
    }
}
