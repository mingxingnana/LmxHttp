package com.lmx.http;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Administrator on 2018\4\8 0008.
 */

public class JsonHttpListener<M> implements IHttpListener {
    private IDataListener<M> dataListener;
    Class<M> responceClass;
    /*
    * handle用户线程的切换
    * **/
    Handler handler = new Handler(Looper.getMainLooper());


    public JsonHttpListener(IDataListener<M> dataListener, Class<M> responceClass) {
        this.dataListener = dataListener;
        this.responceClass = responceClass;
    }


    @Override
    public void onSuccess(InputStream inputStream) {
        final String cpntent = getContent(inputStream);
        final M responce = new Gson().fromJson(cpntent, responceClass);
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (dataListener != null)
                    dataListener.onSuccess(responce);
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
