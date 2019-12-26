package com.yezi.ssocommon.util;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Author: yezi
 * @Date: 2019/12/24 15:08
 */
public class HttpRequestUtil {

    private final static String APPKEY = "c266b31404aee7c5658fef3bb22d48d1";
    private final static String URL = "https://way.jd.com/jisuapi/query4";

    public static String doRequest(String requesUrl, String requestMethod, Map<String, String> params) throws Exception {
        URL url = new URL(requesUrl);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setDoOutput(true);
        urlConnection.setRequestMethod(requestMethod);
        OutputStream urlConnectionOutputStream = urlConnection.getOutputStream();
        if (null != params && params.size() > 0) {
            StringBuilder sb = new StringBuilder();
            for (String key : params.keySet()) {
                sb.append(key + "=" + params.get(key) + "&");
            }
            urlConnectionOutputStream.write(sb.toString().getBytes());
        }
        urlConnectionOutputStream.flush();
        urlConnectionOutputStream.close();
        InputStream urlConnectionInputStream = urlConnection.getInputStream();
        byte[] data = new byte[1024];
        StringBuffer sb1 = new StringBuffer();
        int length = 0;
        while ((length = urlConnectionInputStream.read(data)) != -1) {
            String s = new String(data, Charset.forName("utf-8"));
            sb1.append(s);
        }
        urlConnectionInputStream.close();
        urlConnection.disconnect();
        return sb1.toString().trim();
    }


    /**
     * 模拟浏览器的请求
     */
    public static void sendHttpRequest(String httpURL,String jesssionId) throws Exception{
        //建立URL连接对象
        URL url = new URL(httpURL);
        //创建连接
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        //设置请求的方式(需要是大写的)
        conn.setRequestMethod("POST");
        //设置需要输出
        conn.setDoOutput(true);
        conn.addRequestProperty("Cookie","JSESSIONID="+jesssionId);
        //发送请求到服务器
        conn.connect();

        if (conn.getResponseCode()>400){
            conn.getErrorStream();
        }else {
            conn.getInputStream();
        }


        conn.disconnect();
    }

    public static void main(String[] args) throws Exception {
        HashMap<String, String> params = new HashMap<>();
        params.put("shouji", "15023663421");
        params.put("appkey", APPKEY);
        String result = doRequest(URL, "POST", params);
        System.out.println(result);
    }
}
