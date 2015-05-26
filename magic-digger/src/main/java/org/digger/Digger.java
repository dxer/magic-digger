package org.digger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.digger.WebSite;

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
 * 1.0		2015年5月22日	linghf		created.
 * </pre>
 */

/**
 * 
 * @class Digger
 * @author linghf
 * @version 1.0
 * @since 2015年5月22日
 */
public class Digger {

    public WebSite getWebSiteDemo() {
        WebSite webSite = new WebSite();
        webSite.setUrl("http://www.cnblogs.com/");

        // http://www.cnblogs.com/laoguigame/p/4521947.html

        List<String> textLinkFilters = new ArrayList<>();
        textLinkFilters.add("http://www.cnblogs.com/*/p/*.html");
        webSite.setTextLinkFilters(textLinkFilters);

        Map<String, String> fetchXPaths = new HashMap<String, String>();
        webSite.setFetchXPaths(fetchXPaths);
        
        return webSite;
    }

    public static void main(String[] args) {

    }
}
