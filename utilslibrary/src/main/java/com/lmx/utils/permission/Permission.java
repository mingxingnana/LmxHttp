package com.lmx.utils.permission;

/**
 * Created by Administrator on 2018/4/27 0027.
 */

import android.support.annotation.NonNull;

public interface Permission {

    @NonNull
    Permission permission(String... permissions);

    @NonNull
    Permission requestCode(int requestCode);

    void send();

}
