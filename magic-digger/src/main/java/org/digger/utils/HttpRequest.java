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
 * 1.0		2015年5月18日	linghf		created.
 * </pre>
 */
package org.digger.utils;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * HttpRequest 工具类
 * 
 * @class HttpRequest
 * @author linghf
 * @version 1.0
 * @since 2015年5月18日
 */
public class HttpRequest {

    public static final String HTTP_PREFIX = "http://";

    public static final String HTTPS_PREFIX = "https://";

    public static final String ACCEPT_ENCODING = "Accept-Encoding";

    public static final String USER_AGENT = "User-Agent";

    public static final String CONTENT_TYPE = "Content-Type";

    private HttpURLConnection httpURLConnection = null;

    private static TrustManager myX509TrustManager = new X509TrustManager(){

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {}

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
    };

    /**
     * 构造函数
     * 
     * @param httpURLConnection
     */
    public HttpRequest(HttpURLConnection httpURLConnection){
        this.httpURLConnection = httpURLConnection;
    }

    /**
     * 判断是否是http请求
     * 
     * @param uri
     * @return
     */
    public static boolean isHttpSsl(String uri) {
        if (!StringUtil.isEmpty(uri)) {
            if (uri.startsWith(HTTPS_PREFIX)) {
                return true;
            }
        }
        return false;
    }

    /**
     * http post请求
     * 
     * @param uri
     * @return
     */
    public static HttpRequest Post(String uri) {
        if (!StringUtil.isEmpty(uri)) {
            boolean isSSL = isHttpSsl(uri);

            URL url = null;
            HttpsURLConnection urlConnection = null;
            try {

                url = new URL(uri);
                urlConnection = (HttpsURLConnection) url.openConnection();
                urlConnection.setDoOutput(true);
                urlConnection.setDoInput(true); // 设置是否从httpUrlConnection读入，默认情况下是true;
                // urlConnection.setUseCaches(false); // Post 请求不能使用缓存
                urlConnection.setRequestMethod("POST");

                //
                if (isSSL) {
                    SSLContext sslcontext = SSLContext.getInstance("TLS");
                    sslcontext.init(null, new TrustManager[] {myX509TrustManager}, null);

                    urlConnection.setSSLSocketFactory(sslcontext.getSocketFactory());

                    HostnameVerifier hv = new HostnameVerifier(){
                        public boolean verify(String urlHostName, SSLSession session) {
                            return urlHostName.equals(session.getPeerHost());
                        }
                    };
                    urlConnection.setHostnameVerifier(hv);
                }

                return new HttpRequest(urlConnection);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    /**
     * 
     * @param uri
     * @param isSSL
     * @return
     */
    public static HttpRequest Post(String uri, boolean isSSL) {
        if (isSSL) {
            URL url = null;
            HttpsURLConnection urlConnection = null;
            try {
                SSLContext sslcontext = SSLContext.getInstance("TLS");
                sslcontext.init(null, new TrustManager[] {myX509TrustManager}, null);

                url = new URL(uri);
                urlConnection = (HttpsURLConnection) url.openConnection();
                urlConnection.setDoOutput(true);
                // 设置是否从httpUrlConnection读入，默认情况下是true;
                urlConnection.setDoInput(true);
                // Post 请求不能使用缓存
                // urlConnection.setUseCaches(false);
                urlConnection.setRequestMethod("POST");

                urlConnection.setSSLSocketFactory(sslcontext.getSocketFactory());

                HostnameVerifier hv = new HostnameVerifier(){
                    public boolean verify(String urlHostName, SSLSession session) {
                        return urlHostName.equals(session.getPeerHost());
                    }
                };
                urlConnection.setHostnameVerifier(hv);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return new HttpRequest(urlConnection);
        } else {
            return Post(uri);
        }
    }

    /**
     * get请求
     * 
     * @param uri
     * @return
     */
    public static HttpRequest Get(String uri) {
        URL url = null;
        HttpURLConnection urlConnection = null;
        try {
            url = new URL(uri);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("GET");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new HttpRequest(urlConnection);
    }

    /**
     * 添加header
     * 
     * @param key
     * @param value
     * @return
     */
    public HttpRequest addHeader(String key, String value) {
        this.httpURLConnection.setRequestProperty(key, value);
        return this;
    }

    /**
     * 添加head列表
     * 
     * @param headers
     * @return
     */
    public HttpRequest addHeaders(Map<String, String> headers) {
        if (headers != null && headers.size() > 0) {
            for (String key: headers.keySet()) {
                this.httpURLConnection.setRequestProperty(key, headers.get(key));
            }
        }
        return this;
    }

    /**
     * 删除header
     * 
     * @param name
     * @param value
     * @return
     */
    public HttpRequest removeHeader(String name, String value) {
        Map<String, List<String>> headers = httpURLConnection.getRequestProperties();
        if (headers.containsKey(name)) {
            List<String> values = headers.get(name);
            if (values != null && values.size() > 0) {
                values.remove(value);
            }
        }
        return this;
    }

    /**
     * 删除headers列表
     * 
     * @param name
     * @return
     */
    public HttpRequest removeHeaders(String name) {
        Map<String, List<String>> headers = httpURLConnection.getRequestProperties();
        if (headers.containsKey(name)) {
            headers.remove(name);
        }
        return this;
    }

    /**
     * 设置代理
     * 
     * @param agent
     * @return
     */
    public HttpRequest userAgent(String agent) {
        this.httpURLConnection.setRequestProperty(USER_AGENT, agent);
        return this;
    }

    /**
     * 设置contentType
     * 
     * @param contentType
     * @return
     */
    public HttpRequest setContentType(String contentType) {
        this.httpURLConnection.setRequestProperty(CONTENT_TYPE, contentType);
        return this;
    }

    /**
     * 设置acceptEncoding
     * 
     * @param acceptEncoding
     * @return
     */
    public HttpRequest setAcceptEncoding(String acceptEncoding) {
        this.httpURLConnection.setRequestProperty(ACCEPT_ENCODING, acceptEncoding);
        return this;
    }

    public HttpURLConnection getConnection() {
        return httpURLConnection;
    }

    /**
     * 执行http访问
     * 
     * @param s
     * @return
     */
    public HttpRequest body(String s) {
        DataOutputStream out = null;
        if (this.httpURLConnection.getRequestMethod().equals("POST") && s != null && s.length() > 0) {
            try {
                out = new DataOutputStream(this.httpURLConnection.getOutputStream());
                out.write(s.getBytes("UTF-8"));
                out.flush();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                    }
                }
            }
        }

        return this;
    }

    /**
     * 执行http访问
     * 
     * @param params 参数
     * @param encode 编码类型
     * @return
     */
    public HttpRequest body(Map<String, String> params, String encode) {
        try {
            StringBuilder paramBuilder = new StringBuilder();
            if (params != null && params.size() > 0) {
                for (String key: params.keySet()) {
                    String value = params.get(key);
                    if (paramBuilder.toString().length() == 0) {
                        paramBuilder.append(key).append("=").append(URLEncoder.encode(value, encode));
                    } else {
                        paramBuilder.append("&").append(key).append("=").append(URLEncoder.encode(value, "utf-8"));
                    }
                }
            }
            if (paramBuilder.toString().length() > 0) {
                body(paramBuilder.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }

    /**
     * 执行http访问，不带参数
     * 
     * @return
     */
    public String execute() {
        InputStream in = null;
        String response = null;
        try {
            in = this.httpURLConnection.getInputStream();
            if (this.httpURLConnection.getResponseCode() == 200) {
                response = new String(readInputStream(in));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    private static byte[] readInputStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        byte[] data = outStream.toByteArray();// 网页的二进制数据
        outStream.close();
        inStream.close();
        return data;
    }

    /**
     * 连接超时时间
     * 
     * @param connectTimeout
     * @return
     */
    public HttpRequest connectTimeout(int connectTimeout) {
        httpURLConnection.setConnectTimeout(connectTimeout);
        return this;
    }

    /**
     * socket超时时间
     * 
     * @param socketTimeout
     * @return
     */
    public HttpRequest socketTimeout(int socketTimeout) {
        httpURLConnection.setReadTimeout(socketTimeout);
        return this;
    }

}
