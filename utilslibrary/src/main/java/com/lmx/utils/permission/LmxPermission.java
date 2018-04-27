package com.lmx.utils.permission;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/4/27 0027.
 */

public class LmxPermission {
    public static boolean hasPermission(@NonNull Context context, @NonNull String... permissions) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return true;

        for (String permission : permissions) {
            boolean hasPermission = (ContextCompat.checkSelfPermission(context, permission) == PackageManager
                    .PERMISSION_GRANTED);
            if (!hasPermission) return false;
        }
        return true;
    }

    public static
    @NonNull
    Permission with(@NonNull Activity activity) {
        return new DefaultPermission(activity);
    }

    public static
    @NonNull
    Permission with(@NonNull Fragment fragment) {
        return new DefaultPermission(fragment);
    }

    public static void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults, PermissionListener listener) {
        List<String> grantedList = new ArrayList<>();
        List<String> deniedList = new ArrayList<>();
        for (int i = 0; i < permissions.length; i++) {
            if (grantResults[i] == PackageManager.PERMISSION_GRANTED)
                grantedList.add(permissions[i]);
            else
                deniedList.add(permissions[i]);
        }
        if (deniedList.isEmpty())
            listener.onSucceed(requestCode, grantedList);
        else
            listener.onFailed(requestCode, deniedList);
    }
}
