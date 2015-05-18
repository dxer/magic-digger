package org.digger.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.log4j.Logger;

/**
 * 字符串工具类
 * 
 * @class StringUtil
 * @author linghf
 * @version 1.0
 * @since 2015年3月10日
 */
public class StringUtil {

    private static Random rnd = new Random();

    private static Logger log = Logger.getLogger(StringUtil.class);

    private static final String DEFAULT_CHARSET = "utf-8";

    private static final String ALGORITHM_MD5 = "MD5";

    private static final String ALGORITHM_SHA1 = "SHA1";

    private static final String ALGORITHM_HMACSHA1 = "HmacSHA1";

    private final static char[] digits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e',
                    'f'};

    private final static String digitsBase36 = "0123456789abcdefghijklmnopqrstuvwxyz";

    // 定义script的正则表达式：<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>
    private final static Pattern htmlScriptPattern = Pattern.compile("<script[^>]*?>[\\s\\S]*?<\\/script>",
        Pattern.CASE_INSENSITIVE);

    // 定义style的正则表达式:
    // <[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>
    private final static Pattern htmlStylePattern = Pattern.compile("<style[^>]*?>[\\s\\S]*?<\\/style>",
        Pattern.CASE_INSENSITIVE);

    // 定义HTML标签的正则表达式
    private final static Pattern htmlTagPattern = Pattern.compile("<[^>]+>", Pattern.CASE_INSENSITIVE);

    // 定义HTML中的转义字符,一些特殊字符处理，主要是&开头;结尾
    private final static Pattern htmlSpecialCharPattern = Pattern.compile("&[a-z]+;", Pattern.CASE_INSENSITIVE);

    /**
     * 判断一个字符串是否为null或是空字符串
     * <p>
     * 
     * @param str The string for checking
     * @return true if the string is neither null nor empty string
     */
    public static boolean isEmpty(String str) {
        return str == null || (str.trim().length() == 0);
    }

    /**
     * byte数组转化为16进制的String
     * <p>
     * 
     * @param byteData byte[] 字节数组
     * @return String
     *         <p>
     *         把字节数组转换成可视字符串
     *         </p>
     */
    public static String toHex(byte byteData[]) {
        return toHex(byteData, 0, byteData.length);
    }

    /**
     * 将字符串data按照encode转化为byte数组，然后转化为16进制的String
     * 
     * @param data 源字符串
     * @param encode 字符编码
     * @return 把字节数组转换成可视字符串
     */
    public static String toHex(String data, String encode) {
        try {
            return toHex(data.getBytes(encode));
        } catch (Exception e) {
            log.error("toHex:" + data + ",encode:" + encode);
        }
        return "";
    }

    /**
     * byte转化为16进制的String
     * 
     * @param b
     * @return 16进制的String
     */
    public static String toHex(byte b) {
        byte[] buf = {b};
        return toHex(buf);
    }

    /* public static String toHex2(byte byteData[], int off, int len) { //等效于下面的toHex，但速度慢10倍以上 StringBuffer buf = new
     * StringBuffer(len * 2); int i;
     * 
     * for (i = off; i < len; i++) { if ( ( (int) byteData[i] & 0xff) < 0x10) { buf.append("0"); }
     * buf.append(Integer.toString( (int) byteData[i] & 0xff, 16)); } return buf.toString(); } */
    /**
     * byte数组的部分字节转化为16进制的String
     * 
     * @param byteData 待转换的byte数组
     * @param offset 开始位置
     * @param len 字节数
     * @return 16进制的String
     */
    public static String toHex(byte byteData[], int offset, int len) {
        char buf[] = new char[len * 2];
        int k = 0;
        for (int i = offset; i < len; i++) {
            buf[k++] = digits[((int) byteData[i] & 0xff) >> 4];
            buf[k++] = digits[((int) byteData[i] & 0xff) % 16];
        }
        return new String(buf);
    }

    /* public static byte[] hex2Bytes( String hex ) { //等效于下面的hex2Bytes，但速度慢8倍以上 if( isEmpty(hex) || hex.length() %2> 0
     * ) { log.error("hex2Bytes: invalid HEX string:" + hex ); return null; } int len = hex.length() /2; byte[] ret =
     * new byte[ len ]; int k = 0; for (int i = 0; i < len; i++) { ret[i] = (byte)Integer.parseInt(
     * hex.substring(k,k+2), 16 ); k += 2; } return ret; } */
    /**
     * 将16进制的字符串转换为byte数组，是toHex的逆运算
     * 
     * @param hex 16进制的字符串
     * @return byte数组
     */
    public static byte[] hex2Bytes(String hex) {
        if (isEmpty(hex) || hex.length() % 2 > 0) {
            log.error("hex2Bytes: invalid HEX string:" + hex);
            return null;
        }
        int len = hex.length() / 2;
        byte[] ret = new byte[len];
        int k = 0;
        for (int i = 0; i < len; i++) {
            int c = hex.charAt(k++);
            if (c >= '0' && c <= '9')
                c = c - '0';
            else if (c >= 'a' && c <= 'f')
                c = c - 'a' + 10;
            else if (c >= 'A' && c <= 'F')
                c = c - 'A' + 10;
            else {
                log.error("hex2Bytes: invalid HEX string:" + hex);
                return null;
            }
            ret[i] = (byte) (c << 4);
            c = hex.charAt(k++);
            if (c >= '0' && c <= '9')
                c = c - '0';
            else if (c >= 'a' && c <= 'f')
                c = c - 'a' + 10;
            else if (c >= 'A' && c <= 'F')
                c = c - 'A' + 10;
            else {
                log.error("hex2Bytes: invalid HEX string:" + hex);
                return null;
            }
            ret[i] += (byte) c;
        }
        return ret;
    }

    /**
     * 字符编码转换
     * 
     * @param str 源字符串
     * @param srcCharset 源字符串的编码方式
     * @param dstCharset 目标字符串的编码方式（字节真正的编码方式）
     * @return 转换后的字符串
     */
    public static String charsetConvert(String str, String srcCharset, String dstCharset) {
        if (isEmpty(str))
            return "";
        try {
            return new String(str.getBytes(srcCharset), dstCharset);
        } catch (Exception e) {
            log.error("charsetConvert:" + e);
        }
        return str;
    }

    /**
     * 将字符串从ISO格式转换为UTF-8格式
     * <p>
     * <p>
     * 
     * @param str
     * @return String
     */
    public static String iso2utf8(String str) {
        return charsetConvert(str, "ISO-8859-1", "UTF-8");
    }

    /**
     * 将字符串从UTF-8格式转换为ISO格式
     * <p>
     * <p>
     * 
     * @param str
     * @return String
     */
    public static String utf82iso(String str) {
        return charsetConvert(str, "UTF-8", "ISO-8859-1");
    }

    /**
     * 将字符串从ISO格式转换为gb2312格式
     * <p>
     * <p>
     * 
     * @param str
     * @return String
     */
    public static String iso2gbk(String str) {
        return charsetConvert(str, "ISO-8859-1", "GBK");
    }

    /**
     * 将字符串从gb2312格式转换为ISO格式
     * <p>
     * <p>
     * 
     * @param str
     * @return String
     */
    public static String gbk2iso(String str) {
        return charsetConvert(str, "GBK", "ISO-8859-1");
    }

    /**
     * 将字符串从UTF-8格式转换为gbk格式
     * <p>
     * <p>
     * 
     * @param str
     * @return String
     */
    public static String utf82gbk(String str) {
        return charsetConvert(str, "UTF-8", "GBK");
    }

    /**
     * 将字符串从gb2312格式转换为UTF-8格式
     * <p>
     * <p>
     * 
     * @param str
     * @return String
     */
    public static String gbk2utf8(String str) {
        return charsetConvert(str, "GBK", "UTF-8");
    }

    /**
     * 在字符串的左边添加多个字符pad，直到字符串的长度达到length为止，如果原始长度已经大于length，直接返回源串
     * 
     * @param str 源字符串
     * @param pad 新加的站位符，通常是空格或0等参数
     * @param length 目标长度
     * @return 长度大于或等于length的新字符串
     */
    public static String leftPadString(String str, char pad, int length) {
        if (str.length() >= length)
            return str;
        StringBuffer sb = new StringBuffer();
        while (sb.length() < length - str.length())
            sb.append(pad);
        sb.append(str);
        return sb.toString();
    }

    /**
     * 在字符串的右边添加多个字符pad，直到字符串的长度达到length为止，如果原始长度已经大于length，直接返回源串
     * 
     * @param str 源字符串
     * @param pad 新加的站位符，通常是空格或0等参数
     * @param length 目标长度
     * @return 长度大于或等于length的新字符串
     */
    public static String rightPadString(String str, char pad, int length) {
        if (str.length() >= length)
            return str;
        StringBuffer sb = new StringBuffer(str);
        while (sb.length() < length)
            sb.append(pad);
        return sb.toString();
    }

    /**
     * 为int类型的数字限定位数，不足则前边补零，
     * 
     * @param num 源数字
     * @param strLen 限定位数
     * @return String 结果数字的字符串形式 若strLen<=0 返回int对应的字符串
     */
    public static String intPadString(int num, int strLen) {
        return leftPadString(String.valueOf(num), '0', strLen);
    }

    /**
     * 为long类型的数字限定位数，不足则前边补零，
     * 
     * @param num 源数字
     * @param strLen 限定位数
     * @return String 结果数字的字符串形式 若strLen<=0 返回long对应的字符串
     */
    public static String longPadString(long num, int strLen) {
        return leftPadString(String.valueOf(num), '0', strLen);
    }

    /**
     * 根据字数截取字符串
     * 
     * @param str 需要处理的字符串
     * @param len 需要截取的长度（字数）
     * @param suffix 省略符号
     * @return 经过截取的字符串
     */
    public static String cutString(String str, int len) {
        return cutString(str, len, "");
    }

    public static String cutString(String str, int len, String suffix) {
        if (StringUtil.isEmpty(str) || len <= 0) {
            return "";
        }
        if (len >= str.length())
            return str + suffix;
        return str.substring(0, len) + suffix;
    }

    /**
     * 从src的搜索出介于begin和end之间的字符串， 如substring("user=admin&passwd=123&code=888","passwd=","&")将返回"123"
     * 
     * @param src
     * @param begin
     * @param end
     * @return
     */
    public static String subString(String src, String begin, String end) {
        return subString(src, 0, begin, end);
    }

    /**
     * 从src的offset位置开始搜索出介于begin和end之间的字符串， 如subString("user=admin&passwd=123&code=888",0,"passwd=","&")将返回"123"
     * 
     * @param src
     * @param offset
     * @param begin
     * @param end
     * @return
     */
    public static String subString(String src, int offset, String begin, String end) {
        if (isEmpty(src) || offset >= src.length())
            return "";
        int b = offset;
        int e = src.length();
        if (!isEmpty(begin)) {
            b = src.indexOf(begin, offset);
            if (b < 0)
                return "";
            b += begin.length();
        }
        if (!isEmpty(end) && b < e) {
            e = src.indexOf(end, b);
            if (e < 0)
                e = src.length();
        }
        return src.substring(b, e);
    }

    /**
     * 获取一个随机数字符串，限定位数，不足则前边补零，
     * 
     * @param maxValue 可能的最大随机数
     * @param strLen 限定位数
     * @return String 结果数字的字符串形式 若strLen<=0 返回int对应的字符串
     */
    public static String getRandomNumberString(int maxValue, int strLen) {
        return intPadString(rnd.nextInt(maxValue), strLen);
    }

    public static String getRandomNumberStringBase36(int strLen) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < strLen; i++) {
            sb.append(digitsBase36.charAt(rnd.nextInt(36)));
        }
        return sb.toString();
    }

    /**
     * 获取一个随机数
     * 
     * @param maxValue 可能的最大随机数
     * @return 不大于maxValue的整型数
     */
    public static int getRandomNumber(int maxValue) {
        return rnd.nextInt(maxValue);
    }

    /**
     * 计算字符串的md5的摘要信息
     * 
     * @param s 源字符串
     * @return 32字节的16进制的字符串
     */
    public static String md5(String s) {
        return md5(s, null);
    }

    public static String md5(byte[] data) {
        return digest(data, null, ALGORITHM_MD5);
    }

    public static String md5(byte[] data, byte[] key) {
        return digest(data, key, ALGORITHM_MD5);
    }

    public static byte[] md5Bytes(byte[] data) {
        return digestBytes(data, null, ALGORITHM_MD5);
    }

    public static byte[] md5Bytes(byte[] data, byte[] key) {
        return digestBytes(data, key, ALGORITHM_MD5);
    }

    /**
     * 计算字符串的md5的摘要信息
     * 
     * @param data 源字符串
     * @param key salt字符串，
     * @return 32字节的16进制的字符串
     */
    public static String md5(String data, String key) {
        return digest(data, key, ALGORITHM_MD5);
    }

    /**
     * 计算字符串的SHA1的摘要信息
     * 
     * @param s 源字符串
     * @return 32字节的16进制的字符串
     */
    public static String sha1(String s) {
        return sha1(s, null);
    }

    public static byte[] sha1Bytes(byte[] data) {
        return digestBytes(data, null, ALGORITHM_SHA1);
    }

    public static byte[] sha1Bytes(byte[] data, byte[] key) {
        return digestBytes(data, key, ALGORITHM_SHA1);
    }

    /**
     * 计算字符串的SHA1的摘要信息
     * 
     * @param data 源字符串
     * @param key salt字符串，
     * @return 32字节的16进制的字符串
     */
    public static String sha1(String data, String key) {
        return digest(data, key, ALGORITHM_SHA1);
    }

    /**
     * 计算字符串的摘要信息
     * 
     * @param data 源字符串
     * @param key salt字符串，
     * @param digestName 摘要算法名称，可以是MD5，SHA1等
     * @return 32字节的16进制的字符串
     */
    public static String digest(String data, String key, String digestName) {
        String ret = "";
        if (isEmpty(data))
            return ret;

        try {
            byte[] keybytes = null;
            if (!isEmpty(key))
                keybytes = key.getBytes(DEFAULT_CHARSET);
            return digest(data.getBytes(DEFAULT_CHARSET), keybytes, digestName);
        } catch (Exception e) {
            log.error("digest error:" + e);
        }
        return ret;
    }

    /**
     * 计算字符串的摘要信息
     * 
     * @param data 源字节
     * @param key salt，
     * @param digestName 摘要算法名称，可以是MD5，SHA1等
     * @return 32字节的16进制的字符串
     */
    public static String digest(byte[] data, byte[] key, String digestName) {
        byte[] bytes = digestBytes(data, key, digestName);
        if (bytes == null)
            return "";
        return toHex(bytes, 0, bytes.length);
    }

    public static byte[] digestBytes(byte[] data, byte[] key, String digestName) {
        if (data == null || data.length == 0)
            return null;

        try {
            MessageDigest mgd = MessageDigest.getInstance(digestName);
            mgd.update(data);
            byte[] bytes = null;
            if (key == null || key.length == 0) {
                bytes = mgd.digest();
            } else {
                bytes = mgd.digest(key);
            }
            mgd.reset();
            return bytes;
        } catch (Exception e) {
            log.error("digest error:" + e);
        }
        return null;
    }

    /**
     * hmacSHA1
     * 
     * @param data
     * @param key
     * @return
     */
    public static String hmacSHA1(String data, String key) {
        String ret = "";
        if (isEmpty(data))
            return ret;

        try {
            SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(DEFAULT_CHARSET), ALGORITHM_HMACSHA1);
            Mac mac = Mac.getInstance(ALGORITHM_HMACSHA1);
            mac.init(signingKey);
            byte[] bytes = mac.doFinal(data.getBytes(DEFAULT_CHARSET));
            ret = toHex(bytes, 0, bytes.length);
            mac.reset();
        } catch (Exception ex) {
            log.error("hmacSHA1 error:", ex);
        }
        return ret;
    }

    /**
     * 对html中的特殊字符进行转义，如&，<, >, ", ', 空格，回车，换行等
     * 
     * @param content 原始的html代码
     * @return 转义后的html代码
     */
    public static String fixHtml(String content) {
        String ret = content;
        ret = ret.replace("&", "&amp;");
        ret = ret.replace("<", "&lt;");
        ret = ret.replace(">", "&gt;");
        ret = ret.replace("\r\n", "\n");
        ret = ret.replace("\n", "<br>");
        ret = ret.replace("\t", "    ");
        ret = ret.replace(" ", "&nbsp;");
        ret = ret.replace("\"", "&quot;");
        ret = ret.replace("'", "&#39;");
        return ret;
    }

    /**
     * 对html中的特殊字符转义进行还原，如&，<, >, ", ', 空格，回车，换行等
     * 
     * @param content 转义后的html代码
     * @return 原始的html代码
     */
    public static String unfixHtml(String content) {
        String ret = content;
        ret = ret.replace("&lt;", "<");
        ret = ret.replace("&gt;", ">");
        ret = ret.replace("&nbsp;", " ");
        ret = ret.replace("&quot;", "\"");
        ret = ret.replace("&#39;", "'");
        ret = ret.replace("&#34;", "\"");
        ret = ret.replace("&amp;", "&");
        return ret;
    }

    /**
     * 对文本中的HTML标签和转义字符进行过滤，替换成指定的字符串
     * 
     * @param htmlStr
     * @param replaceStr
     * @return
     */
    public static String html2Text(String htmlStr, String replaceStr) {
        String textStr = "";
        try {
            Matcher matcher = htmlScriptPattern.matcher(htmlStr);
            textStr = matcher.replaceAll(replaceStr); // 过滤script标签

            matcher = htmlStylePattern.matcher(textStr);
            textStr = matcher.replaceAll(replaceStr); // 过滤style标签

            matcher = htmlTagPattern.matcher(textStr);
            textStr = matcher.replaceAll(replaceStr); // 过滤html标签

            matcher = htmlSpecialCharPattern.matcher(textStr);
            textStr = matcher.replaceAll(replaceStr); // 过滤特殊字符标签
        } catch (Exception e) {
            log.error("html2Text error:" + e);
            e.printStackTrace();
        }
        return textStr;
    }

    /**
     * 判断email地址是否符合规范
     * 
     * @param email 待检查的email地址
     * @return 符合返回true，否则返回false
     */
    public static boolean isEmail(String email) {
        if (isEmpty(email))
            return false;
        return Pattern.matches("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$", email);
    }

    /**
     * 判断是否是Json格式的字符串
     * 
     * @param s
     * @return
     */
    public static boolean isJsonObject(String s) {
        if (isEmpty(s))
            return false;
        if ((s.startsWith("{")) || (s.startsWith("[")) || (s.equals("true")) || (s.equals("false")) ||
            (s.equals("null"))) {
            return true;
        }
        return isNumber(s);
    }

    /**
     * 判断是否是手机号
     * 
     * @param email 待检查的电话号码串
     * @return 符合返回true，否则返回false
     */
    public static boolean isMobilePhone(String s) {
        if (isEmpty(s))
            return false;
        return Pattern.matches("^(13|14|15|18)\\d{9}$", s);
    }

    public static boolean isPhoneNumber(String s) {
        if (isEmpty(s))
            return false;
        return Pattern.matches("^[0-9\\-\\(\\)\\ ]+$", s);
    }

    public static boolean isDate(String s) {
        if (isEmpty(s))
            return false;
        return Pattern.matches("^[0-9]{4}\\-[0-9]{1,2}\\-[0-9]{1,2}$", s);
    }

    public static boolean isNumber(String s) {
        if (isEmpty(s))
            return false;
        return Pattern.matches("^[-]*[0-9\\.]+$", s);
    }

    public static boolean isOnlyLetter(String s) {
        if (isEmpty(s))
            return false;
        return Pattern.matches("^[a-zA-Z\\ \\']+$", s);
    }

    public static boolean isImageFile(String s) {
        if (isEmpty(s))
            return false;
        return Pattern.matches("(.*)\\.(jpeg|jpg|bmp|gif|png)$", s);
    }

    public static boolean isOnlyChinese(String s) {
        if (isEmpty(s))
            return false;
        return Pattern.matches("[^u4e00-u9fa5]+$", s);
    }

    public static boolean isUrl(String s) {
        if (isEmpty(s))
            return false;
        boolean ret = Pattern.matches("^(https|http|ftp|rtsp|mms)?:\\/\\/[^\\s]*$", s);
        if (!ret)
            ret = Pattern.matches("^[\\.\\/\\?#a-zA-Z0-9-_=&;,%]*$", s);
        return ret;
    }

    /**
     * 可识别的windows GUID字符串转换为16位byte数组
     * 
     * @param guid GUID字符串
     * @return 16位byte数组
     */
    public static byte[] guid2bytes(String guid) {
        // windows guid:75B22630-668E-11CF-A6D9-00AA0062CE6C ==>
        // 3026B2758E66CF11A6D900AA0062CE6C
        StringBuffer sb = new StringBuffer(guid);
        sb.replace(0, 2, guid.substring(6, 8)).replace(2, 4, guid.substring(4, 6)).replace(4, 6, guid.substring(2, 4))
                        .replace(6, 8, guid.substring(0, 2));
        sb.replace(9, 11, guid.substring(11, 13)).replace(11, 13, guid.substring(9, 11));
        sb.replace(14, 16, guid.substring(16, 18)).replace(16, 18, guid.substring(14, 16));
        return StringUtil.hex2Bytes(sb.toString().replace("-", ""));
    }

    /**
     * 转换16位byte数组为可识别的windows GUID字符串
     * 
     * @param buf 16位byte数组
     * @return GUID字符串
     */
    public static String bytes2Guid(byte[] buf) {
        return bytes2Guid(buf, 0);
    }

    /**
     * 转换16位byte数组为可识别的windows GUID字符串
     * 
     * @param buf byte数组
     * @param offset 数组的开始位置
     * @return GUID字符串
     */
    public static String bytes2Guid(byte[] buf, int offset) {
        // 3026B2758E66CF11A6D900AA0062CE6C ==>
        // 75B22630-668E-11CF-A6D9-00AA0062CE6C
        final int guidSize = 16;
        if (buf == null || offset < 0 || (offset + guidSize > buf.length))
            return "";

        String hex = StringUtil.toHex(buf, offset, guidSize);
        StringBuffer sb = new StringBuffer();
        sb.append(hex.subSequence(6, 8)).append(hex.subSequence(4, 6)).append(hex.subSequence(2, 4))
                        .append(hex.subSequence(0, 2));
        sb.append("-").append(hex.subSequence(10, 12)).append(hex.subSequence(8, 10));
        sb.append("-").append(hex.subSequence(14, 16)).append(hex.subSequence(12, 14));
        sb.append("-").append(hex.subSequence(16, 20));
        sb.append("-").append(hex.substring(20));
        return sb.toString().toUpperCase();
    }

    /**
     * 删除字符串的第一个和最后一个字符
     * 
     * @param str
     * @return 去除首字符和最后一个字符后的字符串
     */
    public static String truncateFirstEnd(String str) {
        if (isEmpty(str))
            return str;

        String tmp = str.substring(1);
        return tmp.substring(0, tmp.length() - 1);
    }

    /**
     * 将ipv4的地址串转换成一个long型整数
     * 
     * @param ip ipv4格式的地址串
     * @return long型整数
     */
    public static long valueOfIpv4(String ip) {
        long ret = 0;
        String[] p = ip.split("\\.");
        ret += Long.parseLong(p[0]) << 24;
        ret += Long.parseLong(p[1]) << 16;
        ret += Long.parseLong(p[2]) << 8;
        ret += Long.parseLong(p[3]);
        return ret;
    }

    /**
     * 将一个ipv4对应的long型整数转换成一个ipv4的地址串
     * 
     * @param ip ipv4对应的long型整数
     * @return ipv4的地址串
     */
    public static String ipv4(long ip) {
        StringBuilder ret = new StringBuilder();
        ret.insert(0, ip % 256).insert(0, ".");
        ip >>= 8;
        ret.insert(0, ip % 256).insert(0, ".");
        ip >>= 8;
        ret.insert(0, ip % 256).insert(0, ".");
        ip >>= 8;
        ret.insert(0, ip);
        return ret.toString();
    }

    /* 对特殊字符进行UNICODE转码,如 */
    public static String convertSpecialChar2Unicode(String s) {
        if (null == s)
            return null;

        StringBuilder sb = new StringBuilder();
        char[] cs = s.toCharArray();

        for (char c: cs) {
            switch (c) {
                case '"':
                case '\'':
                case '\t':
                    sb.append("\\u00").append(Integer.toHexString(c).toUpperCase());
                    break;
                case '\n':
                case '\r':
                    sb.append("\\u000").append(Integer.toHexString(c).toUpperCase());
                    break;
                default:
                    sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 统计字符串字节数（中文和全角字符算2个字节长度,半角字符算1个字节长度）
     * 
     * @param str
     * @return
     */
    public static int getByteLength4FontWidth(String str) {
        if (isEmpty(str))
            return 0;

        int n = 0;

        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c < 256) {
                n++;
            } else if ((c >= 0xFF61) && (c <= 0xFF9F)) { // 半角的日语字符
                n++;
            } else if ((c >= 0xFFA0) && (c <= 0xFFBE)) { // 半角的韩语字符
                n++;
            } else if ((c >= 0xFFC2) && (c <= 0xFFC7)) { // 半角的韩语字符
                n++;
            } else if ((c >= 0xFFCA) && (c <= 0xFFCF)) { // 半角的韩语字符
                n++;
            } else if ((c >= 0xFFD2) && (c <= 0xFFD7)) { // 半角的韩语字符
                n++;
            } else if ((c >= 0xFFDA) && (c <= 0xFFDC)) { // 半角的韩语字符
                n++;
            } else if ((0xFFE8 <= c) && (c <= 0xFFEE)) { // 半角的特殊符号
                n++;
            } else {
                n += 2;
            }
        }
        return n;
    }

    /**
     * 测试主函数
     * 
     * @param args
     */
    static public void main(String[] args) {
        // byte [] a = {32, 35};

        /* String[] strs = StringUtil.splitStr("abcdfbad", "b"); for (int i = 0; i < strs.length; i++) {
         * System.out.println( "StringUtil.splitStr() = " + strs[i]); } */
        // System.out.println( Byte.toString( a[0] ));
        // WRs-oqHGibX9iNVZ_v4IAQ==
        // s9d_RWrSXzkYL9mpgg5nxQ==/
        // System.out.println( new
        // String(hex2Bytes(toHex("Rg_bRbiIa_P0Bm7LsnqdFA==".getBytes()))));
        // System.out.println(base64Decode("s9d_RWrSXzkYL9mpgg5nxQ=="));
        System.out.println(isMobilePhone("138123456789"));
        System.out.println(subString("user=admin&passwd=123&code=888", 0, "user=", "&"));
        System.out.println(subString("user=admin&passwd=123&code=888", 10, "passwd=", "&"));
        System.out.println(subString("user=admin&passwd=123&code=888", 0, "code=", ""));

        System.out.println("url:" + isUrl(""));

        byte[] buf = null;
        try {
            buf = "Stringtestsafaasdfasdfasdfasdfasdfasdf中文".getBytes("gbk");
        } catch (UnsupportedEncodingException e) {
            // TODO 自动生成 catch 块
            e.printStackTrace();
        }
        long s;
        String hex1 = "";
        s = System.currentTimeMillis();

        for (int i = 0; i < 10; i++) {
            hex1 = toHex(buf, 0, buf.length);
        }
        System.out.println("cost1:  " + (System.currentTimeMillis() - s) + " ," + hex1);

        byte[] buf1 = null;
        s = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            buf1 = hex2Bytes(hex1);
        }
        System.out.println("cost3: " + (System.currentTimeMillis() - s) + " ," + new String(buf1));
    }

}
