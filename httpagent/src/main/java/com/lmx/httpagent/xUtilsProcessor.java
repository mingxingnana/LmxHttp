package com.lmx.httpagent;

import android.app.Application;


import java.util.Map;

/**
 * Created by Administrator on 2018\4\11 0011.
 * <p>
 * 代理类，
 * <p>
 * <p>
 * 自己实现代理 继承IHttpProcessor，在初始化里面做网络操作的初始化
 */

public class xUtilsProcessor implements IHttpProcessor {

    public xUtilsProcessor(Application context) {
        HttpHelper.init(this);
    }

    @Override
    public void post(String url, Map<String, Object> parmas, final ICallback iCallback) {


    }
}
