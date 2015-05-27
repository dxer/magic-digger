package org.digger.utils;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;

/**
 * 线程池的简单实现，可以控制任务队列的大小，也可以查看队列的剩余任务数等情况
 */
public class SimpleThreadPool extends ThreadPoolExecutor {

    private Logger log = Logger.getLogger(getClass());

    private AtomicInteger taskBalance = new AtomicInteger(0);

    private int queueCapacity = 100;

    /**
     * 构造线程池
     *
     * @param corePoolSize    核心线程数
     * @param maximumPoolSize 最大线程数
     * @param keepAliveTime   非核心线程空闲时间
     * @param unit            非核心线程空闲时间单位
     * @param workQueue       任务队列
     */
    public SimpleThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    /**
     * 构造线程池
     *
     * @param corePoolSize    核心线程数
     * @param maximumPoolSize 最大线程数
     * @param keepAliveSecond 非核心线程空闲时间（单位：秒）
     * @param workQueue       任务队列
     */
    public SimpleThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveSecond, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveSecond, TimeUnit.SECONDS, workQueue);
    }

    /**
     * 构造线程池
     *
     * @param corePoolSize    核心线程数
     * @param maximumPoolSize 最大线程数
     * @param keepAliveSecond 非核心线程空闲时间（单位：秒）
     */
    public SimpleThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveSecond) {
        super(corePoolSize, maximumPoolSize, keepAliveSecond, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
    }

    /**
     * 构造单线程的线程池
     *
     * @param workQueue 任务队列
     */
    public SimpleThreadPool(BlockingQueue<Runnable> workQueue) {
        super(1, 1, 1, TimeUnit.SECONDS, workQueue);
    }

    /**
     * 构造单线程的线程池
     */
    public SimpleThreadPool() {
        super(1, 1, 1, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
    }


    /**
     * 构造单线程的线程池
     */
    public static SimpleThreadPool createSingleThread() {
        return new SimpleThreadPool();
    }

    protected void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);
        taskBalance.decrementAndGet();

        if (log.isDebugEnabled())
            log.debug("task : " + r.getClass().getSimpleName() + " completed,Throwable:" + t + ",taskBalance:" + getTaskBalance());

        synchronized (this) {
            notifyAll();
        }
    }

    public void execute(Runnable task) {
        taskBalance.getAndIncrement();
        super.execute(task);
    }

    /**
     * 在等待队列未满的情况下，向线程池添加一个任务
     *
     * @param task 待执行的任务
     * @return 如果队列满，返回false，否则返回true
     */
    public boolean addTask(Runnable task) {
        if (queueCapacity < getQueue().size()) {
            log.warn("task Queue full!");
            return false;
        } else {
            execute(task);
        }
        return true;
    }

    /**
     * @return 未执行的任务数
     */
    public int getTaskBalance() {
        return taskBalance.get();
    }

    /**
     * @param capacity 要设置的队列容量
     */
    public void setQueueCapacity(int capacity) {
        this.queueCapacity = capacity;
    }

    public void setMaxPoolSize(int size) {
        super.setMaximumPoolSize(size);
    }

    /**
     * @param time 非核心线程可空闲的秒数
     */
    public void setKeepAliveSecond(int time) {
        super.setKeepAliveTime(time, TimeUnit.SECONDS);
    }

    /**
     * 挂起当前线程，直到所有的任务都执行完成
     */
    public void waitCompleted() {
        try {
            synchronized (this) {
                while (getTaskBalance() > 0) {
                    wait(500);
                }
            }
        } catch (InterruptedException iex) {
        }
        log.info("taskBalance: " + getTaskBalance());
    }

}
