package com.lmx.http;

import android.widget.ImageView;

import com.lmx.httpagent.HttpHelper;
import com.lmx.httpagent.ICallback;
import com.lmx.httpagent.xUtilsProcessor;

import java.io.File;
import java.util.Map;

/**
 * Created by Administrator on 2018/5/8 0008.
 */

public class HttpUtils extends xUtilsProcessor {
    public HttpUtils() {
        super();
    }

    @Override
    public void post(String url, Map<String, Object> parmas, ICallback iCallback) {
        super.post(url, parmas, iCallback);
    }

    @Override
    public void downloadImage(String path, ImageView imageView) {
        super.downloadImage(path, imageView);
    }

    @Override
    public void downloadFile(String path, String filename, String url, ICallback iCallback) {
        super.downloadFile(path, filename, url, iCallback);
    }

    @Override
    public void uploadFile(File file, String url, ICallback iCallback) {
        super.uploadFile(file, url, iCallback);
    }
}
