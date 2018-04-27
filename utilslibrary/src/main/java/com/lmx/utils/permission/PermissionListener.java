package com.lmx.utils.permission;

import java.util.List;

/**
 * Created by Administrator on 2018/4/27 0027.
 */

public interface PermissionListener {
    void onSucceed(int requestCode, List<String> grantPermissions);

    void onFailed(int requestCode, List<String> deniedPermissions);
}
