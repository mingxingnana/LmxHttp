package com.example.httplibrary;

/**
 * Created by Administrator on 2018\4\8 0008.
 * <p>
 * http的请求
 */

public interface IHttpService {
    void setUrl(String url);

    void setRequestData(byte[] requestData);

    void execute();

    void setHttpCallBack(IHttpListener httpListener);
}
