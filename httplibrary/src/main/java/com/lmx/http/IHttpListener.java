package com.lmx.http;

import java.io.InputStream;

/**
 * Created by Administrator on 2018\4\8 0008.
 * <p>
 * http的响应
 */

public interface IHttpListener {

    void onSuccess(InputStream inputStream);

    void onFailure();

}
