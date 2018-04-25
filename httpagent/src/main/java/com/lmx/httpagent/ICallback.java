package com.lmx.httpagent;

/**
 * Created by Administrator on 2018\4\11 0011.
 */

public interface ICallback {
    void onSucess(String object);

    void onFailure(String error);
}
