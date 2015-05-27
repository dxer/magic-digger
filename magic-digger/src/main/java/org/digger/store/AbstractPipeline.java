package org.digger.store;

/**
 * Created by linghf on 2015/5/27.
 */
public abstract class AbstractPipeline implements Pipeline, Runnable {

    @Override
    public void run() {
        while (true) {
            this.process();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName());
                e.printStackTrace();
            }
        }
    }

}
