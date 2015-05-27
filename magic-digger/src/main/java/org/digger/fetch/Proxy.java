package org.digger.fetch;

import java.util.Random;

/**
 * 
 * @class Proxy
 * @author linghf
 * @version 1.0
 * @since 2015年5月18日
 */
public class Proxy {

    public static String getRandomIP() {
        int number1 = new Random().nextInt(250) + 1;
        int number2 = new Random().nextInt(250) + 1;
        int number3 = new Random().nextInt(250) + 1;
        String ip = "183." + number1 + "." + number2 + "." + number3;
        return ip;
    }
    
    /**
     * 设置User-Agent
     * @return
     */
    public static String getRandomUa() {
        String[] uas = {
                        "Baiduspider",
                        "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1)",
                        "Dalvik/1.6.0 (Linux; U; Android 4.1.2; HS-E956Q Build/JZO54K)",
                        "Mozilla/5.0 (iPhone; U; CPU iPhone OS 4_2_6 like Mac OS X; zh-cn) AppleWebKit/533.17.9 (KHTML, like Gecko) Version/5.0.2 Mobile/8E200 Safari/6533.18.5 RPT-HTTPClient/0.3-3",
                        "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; .NET CLR 2.0.50727; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729; .NET4.0C; .NET4.0E) ",
                        "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; Trident/4.0; .NET CLR 2.0.50727; 360space)",
                        "aibang.com", "Googlebot", "yahoo-slurp",
                        "Dalvik/1.6.0 (Linux; U; Android 4.1.2; HUAWEI A199 Build/HuaweiA199)"};
        return uas[new Random().nextInt(10)];
    }
}
