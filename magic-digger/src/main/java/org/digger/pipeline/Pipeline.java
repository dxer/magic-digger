package org.digger.pipeline;

import org.digger.WebPage;

/**
 * 
 * @class Pipeline
 * @author linghf
 * @version 1.0
 * @since 2015年5月26日
 */
public interface Pipeline {

    public void process(WebPage webPage);
}
