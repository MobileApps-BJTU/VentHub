package com.vxpai.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by 俊成 on 2015/3/27.
 */
public class HttpUtil {
    static HttpClient httpClient = null;
    public static JSONObject Post(String baseURL, List<NameValuePair> pairList){
        try {
            HttpEntity requestHttpEntity = new UrlEncodedFormEntity(
                    pairList);
            // URL使用基本URL即可，其中不需要加参数
            HttpPost httpPost = new HttpPost(baseURL);
            // 将请求体内容加入请求中
            httpPost.setEntity(requestHttpEntity);
            // 需要客户端对象来发送请求

            HttpParams params = new BasicHttpParams();
            HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
            HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

            SchemeRegistry registry = new SchemeRegistry();
            registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
            httpClient = new DefaultHttpClient(new ThreadSafeClientConnManager(params, registry), params);
            // 发送请求

            int res = 0;
            HttpResponse httpResponse = httpClient.execute(httpPost);
            res = httpResponse.getStatusLine().getStatusCode();
            if (res == 200) {
                StringBuilder builder = new StringBuilder();
                BufferedReader bufferedReader2 = new BufferedReader(
                        new InputStreamReader(httpResponse.getEntity().getContent()));
                String str2 = "";
                for (String s = bufferedReader2.readLine(); s != null; s = bufferedReader2
                        .readLine()) {
                    builder.append(s);
                }
                return new JSONObject(builder.toString());
            }else{
                return null;
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static JSONArray PostArray(String baseURL, List<NameValuePair> pairList){
        try {
            HttpEntity requestHttpEntity = new UrlEncodedFormEntity(
                    pairList);
            // URL使用基本URL即可，其中不需要加参数
            HttpPost httpPost = new HttpPost(baseURL);
            // 将请求体内容加入请求中
            httpPost.setEntity(requestHttpEntity);
            // 需要客户端对象来发送请求

            HttpParams params = new BasicHttpParams();
            HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
            HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

            SchemeRegistry registry = new SchemeRegistry();
            registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
            httpClient = new DefaultHttpClient(new ThreadSafeClientConnManager(params, registry), params);
            // 发送请求

            int res = 0;
            HttpResponse httpResponse = httpClient.execute(httpPost);
            res = httpResponse.getStatusLine().getStatusCode();
            if (res == 200) {
                StringBuilder builder = new StringBuilder();
                BufferedReader bufferedReader2 = new BufferedReader(
                        new InputStreamReader(httpResponse.getEntity().getContent()));
                String str2 = "";
                for (String s = bufferedReader2.readLine(); s != null; s = bufferedReader2
                        .readLine()) {
                    builder.append(s);
                }
                return new JSONArray(builder.toString());
            }else{
                return null;
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
