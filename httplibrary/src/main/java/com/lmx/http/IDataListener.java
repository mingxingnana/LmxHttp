package com.lmx.http;

/**
 * Created by Administrator on 2018\4\8 0008.
 * <p>
 * 返回结果处理
 */

public interface IDataListener {

    void onSuccess(String m);

    void onFailure();

}
