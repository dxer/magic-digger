/**
 * Copyright (c) 2015 21CN.COM . All rights reserved.
 * 
 * Description: magic-digger
 * 
 * <pre>
 * Modified log:
 * ------------------------------------------------------
 * Ver.		Date		Author			Description
 * ------------------------------------------------------
 * 1.0		2015年5月26日	linghf		created.
 * </pre>
 */
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
