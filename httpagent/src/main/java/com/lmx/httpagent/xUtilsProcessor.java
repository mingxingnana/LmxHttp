package com.lmx.httpagent;

import android.widget.ImageView;


import com.lmx.http.IDataListener;
import com.lmx.http.Lmx;

import java.io.File;
import java.util.Map;

/**
 * Created by Administrator on 2018\4\11 0011.
 */

public class xUtilsProcessor implements IHttpProcessor {

    public xUtilsProcessor() {
        HttpHelper.init(this);
    }

    @Override
    public void post(String url, Map<String, Object> parmas, final ICallback iCallback) {

        Lmx.sendJsonRequest(parmas, url, new IDataListener() {
            @Override
            public void onSuccess(String result) {
                iCallback.onSucess(result);
            }

            @Override
            public void onFailure() {
                iCallback.onFailure("请求失败");
            }
        });
    }

    @Override
    public void uploadFile(File file, String url, final ICallback iCallback) {
        Lmx.sendJsonRequest2(file, url, new IDataListener() {
            @Override
            public void onSuccess(String data) {

                iCallback.onSucess(data);
            }

            @Override
            public void onFailure() {
                iCallback.onFailure("请求失败");
            }
        });
    }

    @Override
    public void downloadFile(String path, String filename, String url, final ICallback iCallback) {
        Lmx.sendJsonRequest(path, filename, url, new IDataListener() {
            @Override
            public void onSuccess(String s) {

                iCallback.onSucess(s);
            }

            @Override
            public void onFailure() {
                iCallback.onFailure("请求失败");
            }
        });
    }

    @Override
    public void downloadImage(String path, ImageView imageView) {
        Lmx.sendJsonRequest(path, imageView);
    }
}
