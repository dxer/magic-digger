package org.digger.parse;

import org.apache.log4j.Logger;
import org.digger.manager.DiggerManager;
import org.digger.manager.DiggerResourceManager;
import org.digger.model.WebPage;

/**
 * Created by linghf on 2015/5/29.
 */
public abstract class AbstractParser implements Parser, Runnable {

    private Logger logger = Logger.getLogger(this.getClass());

    @Override
    public void run() {
        while (DiggerManager.isIsParserKeepRun()) {
            logger.debug("get WebPage from queue, now: " + DiggerResourceManager.getWebPageSize());
            if (DiggerResourceManager.getWebPageSize() > 0) {
                WebPage webPage = DiggerResourceManager.getWebPage();
                this.process(webPage);
            } else {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
