package org.digger.conf;

import org.digger.utils.StringUtil;

import java.io.InputStream;
import java.util.Properties;

/**
 * Created by linghf on 2015/5/30.
 */
public class Config {

    private static Properties prop = new Properties();

    static {
        // 生成输入流
        InputStream ins = Config.class.getResourceAsStream("../../../digger.conf");

        try {
            prop.load(ins);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int fetcherThreadNum = 3;

    private static int parserThreadNum = 3;

    private static int storerThreadNum = 3;

    public static void setFetcherThreadNum(int fetcherThreadNum) {
        Config.fetcherThreadNum = fetcherThreadNum;
    }

    public static void setParserThreadNum(int parserThreadNum) {
        Config.parserThreadNum = parserThreadNum;
    }

    public static void setStorerThreadNum(int storerThreadNum) {
        Config.storerThreadNum = storerThreadNum;
    }

    public static int getFetcherThreadNum() {
        String str = prop.getProperty(Constants.FETCHER_THREAD_NUM);
        if (!StringUtil.isEmpty(str)) {
            try {
                fetcherThreadNum = Integer.parseInt(str);
            } catch (NumberFormatException e) {
            }
        }
        return fetcherThreadNum;
    }

    public static int getParserThreadNum() {
        int threadNum = 3;
        String str = prop.getProperty(Constants.PARSER_THREAD_NUM);
        if (!StringUtil.isEmpty(str)) {
            try {
                threadNum = Integer.parseInt(str);
            } catch (NumberFormatException e) {
            }
        }
        return threadNum;
    }


    public static int getStorerThreadNum() {
        int threadNum = 3;
        String str = prop.getProperty(Constants.STORER_THREAD_NUM);
        if (!StringUtil.isEmpty(str)) {
            try {
                threadNum = Integer.parseInt(str);
            } catch (NumberFormatException e) {
            }
        }
        return threadNum;
    }

    public static String getSavePath() {
        String path = prop.getProperty(Constants.STORER_SAVE_PATH);
        return path;
    }

    public static boolean isMonitorEnable() {
        boolean enable = false;
        String str = prop.getProperty(Constants.MONITOR_ENABLE);
        if (!StringUtil.isEmpty(str)) {
            if (str.toLowerCase().contains("y") || str.toLowerCase().contains("yes")) {
                enable = true;
            }
        }
        return enable;
    }

    public static String getUrl() {
        String url = prop.getProperty(Constants.STORER_MYSQL_URL);
        if (!StringUtil.isEmpty(url)) {
            return url;
        }

        return null;

    }

}
