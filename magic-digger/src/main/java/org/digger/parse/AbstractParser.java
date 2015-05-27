package org.digger.parse;

import org.digger.manager.DiggerManager;

/**
 * Created by linghf on 2015/5/29.
 */
public class AbstractParser implements Parser, Runnable {

    @Override
    public void run() {
        while (DiggerManager.isIsParserRun()) {

        }
    }
}
