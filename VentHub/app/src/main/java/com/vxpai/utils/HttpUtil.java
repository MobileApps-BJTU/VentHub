package com.vxpai.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by 俊成 on 2015/3/27.
 */
public class HttpUtil {

    public static JSONObject Post(String baseURL, List<NameValuePair> pairList){
        try {
            HttpEntity requestHttpEntity = new UrlEncodedFormEntity(
                    pairList);
            // URL使用基本URL即可，其中不需要加参数
            HttpPost httpPost = new HttpPost(baseURL);
            // 将请求体内容加入请求中
            httpPost.setEntity(requestHttpEntity);
            // 需要客户端对象来发送请求
            HttpClient httpClient = new DefaultHttpClient();
            // 发送请求

            int res = 0;
            res = httpClient.execute(httpPost).getStatusLine().getStatusCode();
            if (res == 200) {

                HttpResponse httpResponse = httpClient.execute(httpPost);
                StringBuilder builder = new StringBuilder();
                BufferedReader bufferedReader2 = new BufferedReader(
                        new InputStreamReader(httpResponse.getEntity().getContent()));
                String str2 = "";
                for (String s = bufferedReader2.readLine(); s != null; s = bufferedReader2
                        .readLine()) {
                    builder.append(s);
                }
                return  new JSONObject(builder.toString());
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
