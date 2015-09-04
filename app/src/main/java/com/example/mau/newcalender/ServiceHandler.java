package com.example.mau.newcalender;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;


/**
 * Created by mau on 9/1/2015.
 */
public class ServiceHandler {

    static String response = null;
    public final static int GET = 1;
    public final static int POST = 2;

    public String makeServiceCall(String url, int method){
        return this.makeServiceCall(url, method, null);
    }

    private String makeServiceCall(String url, int method, List<NameValuePair> params) {

        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpEntity httpEntity = null;
        HttpResponse httpResponse = null;
        try {
            if (method == POST) {
                HttpPost httpPost = new HttpPost(url);

                if (params != null) {
                    httpPost.setEntity(new UrlEncodedFormEntity(params));
                }

                httpResponse = httpClient.execute(httpPost);
            } else if (method == GET){
                // appending params to url
                if (params != null){
                    String paramString = URLEncodedUtils.format(params, "utf-8");
                    url += "?" + paramString;
                }

                HttpGet httpGet = new HttpGet(url);

                httpResponse = httpClient.execute(httpGet);
            }

            httpEntity = httpResponse.getEntity();
            response = EntityUtils.toString(httpEntity);
        } catch (UnsupportedEncodingException e){

        } catch (IOException e){

        }
        return response;
    }
}
