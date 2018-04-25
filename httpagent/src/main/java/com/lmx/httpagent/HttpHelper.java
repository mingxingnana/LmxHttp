package com.lmx.httpagent;

import android.widget.ImageView;

import java.io.File;
import java.util.Map;

/**
 * Created by Administrator on 2018\4\11 0011.
 */

public class HttpHelper implements IHttpProcessor {
    private static HttpHelper ourInstance;

    public static HttpHelper obtion() {
        synchronized (HttpHelper.class) {
            if (ourInstance == null) {
                ourInstance = new HttpHelper();
            }
        }
        return ourInstance;
    }

    private HttpHelper() {
    }

    private static IHttpProcessor mIhttpProcessor = null;

    protected static void init(IHttpProcessor m) {
        mIhttpProcessor = m;
    }

    @Override
    public void post(String url, Map<String, Object> parmas, ICallback iCallback) {

        if (parmas != null && !parmas.isEmpty()) {
            url = url + "?";
            for (Map.Entry<String, Object> entry : parmas.entrySet()) {
                System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
                url = url + "&" + entry.getKey() + "=" + entry.getValue();
            }
        }
        if (mIhttpProcessor != null)
            mIhttpProcessor.post(url, parmas, iCallback);
    }

    @Override
    public void uploadFile(File file, String url, ICallback iCallback) {
        if (mIhttpProcessor != null)
            mIhttpProcessor.uploadFile(file, url, iCallback);
    }

    @Override
    public void downloadFile(String path, String filename, String url, ICallback iCallback) {
        if (mIhttpProcessor != null)
            mIhttpProcessor.downloadFile(path, filename, url, iCallback);

    }

    @Override
    public void downloadImage(String path, ImageView imageView) {
        if (mIhttpProcessor != null)
            mIhttpProcessor.downloadImage(path, imageView);
    }
}
