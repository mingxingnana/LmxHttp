package com.lmx.http;

import android.widget.ImageView;

import java.io.File;

/**
 * Created by Administrator on 2018\4\8 0008.
 */

public class Lmx {
    /**
     * post get请求
     *
     * @param requestinfo  请求参数
     * @param url          请求的url
     * @param dataListener 返回结果的监听事件
     */
    public static <T> void sendJsonRequest(T requestinfo, String url, IDataListener dataListener) {
        IHttpListener httpListener = new JsonHttpListener(dataListener);
        IHttpService httpService = new JsonHttpService();
        HttpTast<T> httpTast = new HttpTast<>(requestinfo, url, httpService, httpListener);
        ThreadPoolManager.getInstance().execute(httpTast);
    }


    /**
     * 下载图片
     */
    public static <T> DownloadImageHttpListener sendJsonRequest(String url, ImageView imageView) {
        DownloadImageHttpListener httpListener = new DownloadImageHttpListener(imageView);
        IHttpService httpService = new JsonHttpService();
        HttpTast<T> httpTast = new HttpTast<>(null, url, httpService, httpListener);
        ThreadPoolManager.getInstance().execute(httpTast);
        return httpListener;
    }


    /**
     * 下载文件
     */
    public static <T> void sendJsonRequest(String path, String filename, String url, IDataListener dataListener) {
        IHttpListener httpListener = new DownloadFileHttpListener(path, filename, dataListener);
        IHttpService httpService = new JsonHttpService();
        HttpTast<T> httpTast = new HttpTast<>(null, url, httpService, httpListener);
        ThreadPoolManager.getInstance().execute(httpTast);
    }

    /**
     * 上传文件
     */
    public static <T> void sendJsonRequest2(File file, String url, IDataListener dataListener) {
        IHttpListener httpListener = new JsonHttpListener(dataListener);
        IHttpService httpService = new UploadImageHttpService(file);
        HttpTast<T> httpTast = new HttpTast<>(null, url, httpService, httpListener);
        ThreadPoolManager.getInstance().execute(httpTast);
    }
}
