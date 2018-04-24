package com.lmx.http;

/**
 * Created by Administrator on 2018\4\8 0008.
 * <p>
 * 返回结果处理
 */

public interface IDataListener<M> {

    void onSuccess(M m);

    void onFailure();

}
