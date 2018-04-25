package com.lmx.httpagent;

import android.widget.ImageView;

import java.io.File;
import java.util.Map;

/**
 * Created by Administrator on 2018\4\11 0011.
 */

public interface IHttpProcessor {
    /**
     * get post请求
     */
    void post(String url, Map<String, Object> parmas, ICallback iCallback);

    /**
     * 上传文件
     */
    void uploadFile(File file, String url, ICallback iCallback);

    /**
     * 下载文件
     *
     * @param path     保存文件路径"/sdcard/Lmx/"
     * @param filename 保存文件名称"lmx.apk"
     */
    void downloadFile(String path, String filename, String url, ICallback iCallback);

    /**
     * 下载图片
     */

    void downloadImage(String path, ImageView imageView);
}
