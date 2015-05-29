package org.digger.store;

import org.digger.manager.DiggerManager;

/**
 * Created by linghf on 2015/5/27.
 */
public abstract class AbstractStorer implements Storer, Runnable {

    @Override
    public void run() {
        while (DiggerManager.isIsStorerKeepRun()) {
            this.process();
        }
    }

}
