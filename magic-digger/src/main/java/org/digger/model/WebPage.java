package org.digger.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 抓取的页面信息
 *
 * @author linghf
 * @version 1.0
 * @class WebPage
 * @since 2015年5月18日
 */
public class WebPage implements Serializable {

    private static final long serialVersionUID = -7340509453613547007L;

    private String url;

    private String html;

    private Date fetchTime = new Date();


}
