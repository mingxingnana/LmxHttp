package com.lmx.http;


import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;

/**
 * Created by Administrator on 2018\4\8 0008.
 * <p>
 * 执行网络请求
 */

public class HttpTast<T> implements Runnable {
    private IHttpService httpService;
    private IHttpListener httpListener;

    protected HttpTast(T requestinfo, String url, IHttpService httpService, IHttpListener httpListener) {
        this.httpListener = httpListener;
        this.httpService = httpService;

        httpService.setUrl(url);
        httpService.setHttpCallBack(this.httpListener);

        /**
         * 如果是post请求，就要设置参数
         * */
        if (requestinfo != null) {
            if (requestinfo instanceof JSONObject) {
                try {
                    httpService.setRequestData(getRequestData((JSONObject) requestinfo, "UTF-8").getBytes("UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            } else {
                String request = new Gson().toJson(requestinfo);
                try {
                    httpService.setRequestData(getRequestData(request, "UTF-8").getBytes("UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    protected String getRequestData(JSONObject jsonObject, String encode) {
        return getDataString(jsonObject, encode);
    }

    protected String getRequestData(String request, String encode) {
        try {
            JSONObject jsonObject = new JSONObject(request);
            return getDataString(jsonObject, encode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getDataString(JSONObject jsonObject, String encode) {
        StringBuffer stringBuffer = new StringBuffer();
        try {
            Iterator<?> iterator = jsonObject.keys();

            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                stringBuffer.append(key)
                        .append("=")
                        .append(URLEncoder.encode(jsonObject.getString(key), encode))
                        .append("&");
            }
            stringBuffer.deleteCharAt(stringBuffer.length() - 1);    //删除最后的一个"&"
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringBuffer.toString();
    }

    @Override
    public void run() {
        httpService.execute();
    }
}
