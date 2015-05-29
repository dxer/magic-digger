package org.digger.store;

import org.digger.model.FetchResult;

/**
 * @author linghf
 * @version 1.0
 * @class Pipeline
 * @since 2015年5月26日
 */
public interface Storer {

    public void process(FetchResult fetchResult);
}
