package com.lmx.http;

import android.os.Handler;
import android.os.Looper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Administrator on 2018\4\8 0008.
 */

public class JsonHttpListener implements IHttpListener {
    private IDataListener dataListener;
    /*
    * handle用户线程的切换
    * **/
    Handler handler = new Handler(Looper.getMainLooper());


    public JsonHttpListener(IDataListener dataListener) {
        this.dataListener = dataListener;
    }


    @Override
    public void onSuccess(InputStream inputStream) {
        final String cpntent = getContent(inputStream);
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (dataListener != null)
                    dataListener.onSuccess(cpntent);
            }
        });

    }

    @Override
    public void onFailure() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (dataListener != null)
                    dataListener.onFailure();
            }
        });
    }

    private String getContent(InputStream inputStream) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        StringBuilder sb = new StringBuilder();

        String line = null;

        try {
            while ((line = reader.readLine()) != null) {

                sb.append(line);
            }

        } catch (IOException e) {

            e.printStackTrace();

        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {

                e.printStackTrace();
            }
        }
        return sb.toString();
    }

}
