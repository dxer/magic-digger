/**
 * Copyright (c) 2015 21CN.COM . All rights reserved.
 *
 * Description: digger
 *
 * <pre>
 * Modified log:
 * ------------------------------------------------------
 * Ver.		Date		Author			Description
 * ------------------------------------------------------
 * 1.0		2015年4月16日	linghf		created.
 * </pre>
 */
package org.digger.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author linghf
 * @version 1.0
 * @class DateUtil
 * @since 2015年4月16日
 */
public class DateUtil {

    public static Date formatDate(String dateStr, String formatStr) {
        Date dDate = null;
        try {
            SimpleDateFormat format = new SimpleDateFormat(formatStr);
            dDate = format.parse(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dDate;
    }
}
