package com.lmx.http;

import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2018\4\8 0008.
 */

public class JsonHttpService implements IHttpService {
    private String url;
    private byte[] requestData;
    private IHttpListener iHttpListener;

    @Override
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public void setRequestData(byte[] requestData) {
        this.requestData = requestData;
    }

    @Override
    public void execute() {
        httpUrlconn();
    }

    @Override
    public void setHttpCallBack(IHttpListener httpListener) {
        iHttpListener = httpListener;
    }

    HttpURLConnection urlConnection;

    private void httpUrlconn() {
        URL url = null;
        try {
            url = new URL(this.url);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(6000);//超时
            urlConnection.setUseCaches(false);//不使用缓存
            urlConnection.setInstanceFollowRedirects(true);//是成员函数
            urlConnection.setReadTimeout(3000);//响应超时时间
            urlConnection.setDoInput(true);//设置这个是否可以写入数据
            urlConnection.setDoOutput(true);//设置这个是否可以输出数据
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            if (requestData != null) {
                urlConnection.setRequestProperty("Content-Length", String.valueOf(requestData.length));
            }
            urlConnection.connect();


            OutputStream out = urlConnection.getOutputStream();
            BufferedOutputStream bos = new BufferedOutputStream(out);
            if (requestData != null) {
                bos.write(requestData);
            }
            bos.flush();
            out.close();
            bos.close();
            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = urlConnection.getInputStream();
                iHttpListener.onSuccess(inputStream);
            } else {
                iHttpListener.onFailure();
            }
        } catch (Exception e) {
            e.printStackTrace();
            iHttpListener.onFailure();
        } finally {
            urlConnection.disconnect();
        }

    }

}
