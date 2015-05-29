package org.digger.store;

import org.apache.log4j.Logger;
import org.digger.manager.DiggerManager;
import org.digger.manager.DiggerResourceManager;
import org.digger.model.FetchResult;

/**
 * Created by linghf on 2015/5/27.
 */
public abstract class AbstractStorer implements Storer, Runnable {

    private Logger logger = Logger.getLogger(this.getClass());

    @Override
    public void run() {
        while (DiggerManager.isIsStorerKeepRun()) {
            logger.debug("get FetchResult from queue, now: " + DiggerResourceManager.getFetchResultSize());
            FetchResult fetchResult = DiggerResourceManager.getFetchResult();
            this.process(fetchResult);
        }
    }

}
