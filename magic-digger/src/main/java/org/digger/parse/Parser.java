package org.digger.parse;

import org.digger.model.WebPage;

/**
 * @author linghf
 * @version 1.0
 * @class Processor
 * @since 2015年5月21日
 */
public interface Parser {

    public void process(WebPage webPage);
}
