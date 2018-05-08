package com.lmx.http;

import android.app.Application;

/**
 * Created by Administrator on 2018/5/8 0008.
 */

public class UtileAppcation extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        new HttpUtils();
    }
}
