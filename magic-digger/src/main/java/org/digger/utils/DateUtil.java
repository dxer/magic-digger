package org.digger.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author linghf
 * @version 1.0
 * @class DateUtil
 * @since 2015年4月16日
 */
public class DateUtil {

    public static final String YYYY_MM_DD = "yyyy-mm-dd";

    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    public static final String YYYYMMDD = "yyyyMMdd";

    public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public static final String YYYYMMDDHHMM = "yyyyMMddHHmm";


    /**
     * 获取day对应的Calendar对象
     *
     * @param day
     * @return 返回date对应的Calendar对象
     */
    public static Calendar getCalendar(Date day) {
        Calendar c = Calendar.getInstance();
        if (day != null)
            c.setTime(day);
        return c;
    }


    /**
     * 获取日期的年份
     *
     * @param date
     * @return 日期的年份
     */
    public static int getYear(Date date) {
        return getCalendar(date).get(Calendar.YEAR);
    }

    /**
     * 获取日期的月份（0-11）
     *
     * @param date
     * @return 日期的月份（0-11）
     */
    public static int getMonth(Date date) {
        return getCalendar(date).get(Calendar.MONTH);
    }

    /**
     * 获取日期的一个月中的某天
     *
     * @param date
     * @return 日期的一个月中的某天(1-31)
     */
    public static int getDay(Date date) {
        return getCalendar(date).get(Calendar.DATE);
    }

    /**
     * 获取日期的一个星期中的某天
     *
     * @param date
     * @return 日期的星期中日期(1:sunday-7:SATURDAY)
     */
    public static int getWeek(Date date) {
        return getCalendar(date).get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 将字符串解析为日期
     *
     * @param dateStr
     * @param formatStr
     * @return
     */
    public static Date parseDate(String dateStr, String formatStr) {
        if (dateStr == null || StringUtil.isEmpty(formatStr)) {
            return null;
        }

        Date dDate = null;
        try {
            SimpleDateFormat format = new SimpleDateFormat(formatStr);
            dDate = format.parse(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dDate;
    }

    /**
     * 将long类型转换为Date类型
     *
     * @param currentTime
     * @return
     */
    public static Date parseDate(long currentTime) {
        String formatStr = YYYY_MM_DD_HH_MM_SS;
        String dateStr = parseDateStr(currentTime, formatStr);
        if (!StringUtil.isEmpty(dateStr)) {
            SimpleDateFormat dateFormat = new SimpleDateFormat(formatStr);

            return parseDate(dateStr, formatStr);
        }
        return null;
    }


    /**
     * 将long类型转换为Date类型
     *
     * @param currentTime
     * @param formatStr
     * @return
     */
    public static Date parseDate(long currentTime, String formatStr) {
        String dateStr = parseDateStr(currentTime, formatStr);
        if (!StringUtil.isEmpty(dateStr)) {
            SimpleDateFormat dateFormat = new SimpleDateFormat(formatStr);

            return parseDate(dateStr, formatStr);
        }
        return null;
    }


    /**
     * 将long类型转化为时间字符串
     *
     * @param currentTime
     * @param formatStr
     * @return
     */
    public static String parseDateStr(long currentTime, String formatStr) {
        Date date = new Date(currentTime); // 根据long类型的毫秒数生命一个date类型的时间
        if (date != null) {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat(formatStr);
                return dateFormat.format(date);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    /**
     * 按照指定格式化样式格式化指定的日期
     *
     * @param date   待格式化的日期
     * @param format 日期格式
     * @return 日期字符串
     */
    public static String formatDate(Date date, String format) {
        if (date == null) {
            date = new Date();
        }
        if (format == null) {
            format = YYYY_MM_DD_HH_MM_SS;
        }
        SimpleDateFormat dateFormat = new java.text.SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    /**
     * <p>检查所给的年份是否是闰年</p>
     *
     * @param year 年
     * @return 检查结果: true - 是闰年; false - 是平年
     */
    public static boolean isLeapYear(int year) {
        if (year / 4 * 4 != year) {
            return false; //不能被4整除
        } else if (year / 100 * 100 != year) {
            return true; //能被4整除，不能被100整除
        } else if (year / 400 * 400 != year) {
            return false; //能被100整除，不能被400整除
        } else {
            return true; //能被400整除
        }
    }

    /**
     * 按照默认格式化样式格式化当前系统时间
     *
     * @return 日期字符串
     */
    public static String getCurrentTime() {
        return formatDate(new Date(), YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * 按照默认格式化样式格式化当前系统时间
     *
     * @param format String 日期格式化标准
     * @return String 日期字符串。
     */
    public static String getCurrentTime(String format) {
        return formatDate(new Date(), format);
    }

}
