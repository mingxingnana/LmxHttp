package com.lmx.http;

import android.os.Handler;
import android.os.Looper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * Created by Administrator on 2018\4\8 0008.
 * <p>
 * //下载图片
 */

public class DownloadFileHttpListener<M> implements IHttpListener {

    protected Handler handler = new Handler(Looper.getMainLooper());

    protected String filename, path;
    protected IDataListener<M> dataListener;

    public DownloadFileHttpListener(String path, String filename, IDataListener<M> dataListener) {
        this.filename = filename;
        this.path = path;
        this.dataListener = dataListener;

    }


    @Override
    public void onSuccess(InputStream inputStream) {

        try {
            File file = new File(path);
            if (!file.exists()) {
                file.mkdir();
            }
            final String apkFile = path + filename;
            File ApkFile = new File(apkFile);
            FileOutputStream outStream = new FileOutputStream(ApkFile);
            int count = 0;
            byte buf[] = new byte[1024];
            do {
                int numread = inputStream.read(buf);
                count += numread;
                if (numread <= 0) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            dataListener.onSuccess((M) apkFile);
                        }
                    });

                    break;
                }
                outStream.write(buf, 0, numread);
            } while (true);
            outStream.close();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    dataListener.onFailure();
                }
            });
        }


    }

    @Override
    public void onFailure() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                dataListener.onFailure();
            }
        });
    }
}
