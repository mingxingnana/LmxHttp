package com.lmx.utils.permission;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import java.lang.annotation.Target;

/**
 * Created by Administrator on 2018/4/27 0027.
 */

public class DefaultPermission implements Permission {

    private Object mObject;
    private String[] permissions;
    private int requestCode;

    public DefaultPermission(Object o) {
        mObject = o;
    }

    @NonNull
    @Override
    public Permission permission(String... permissions) {
        this.permissions = permissions;
        return this;
    }

    @NonNull
    @Override
    public Permission requestCode(int requestCode) {
        this.requestCode = requestCode;
        return this;
    }

    @Override
    public void send() {
        requestPermissions();
    }

    @TargetApi(Build.VERSION_CODES.M)
    protected void requestPermissions() {
        if (mObject instanceof Activity)
            ActivityCompat.requestPermissions(((Activity) mObject), permissions, requestCode);
        else if (mObject instanceof android.support.v4.app.Fragment)
            ((android.support.v4.app.Fragment) mObject).requestPermissions(permissions, requestCode);
        else if (mObject instanceof android.app.Fragment) {
            ((android.app.Fragment) mObject).requestPermissions(permissions, requestCode);
        }
    }

}
