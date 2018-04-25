package com.lmx.httpagent;

import java.util.Map;

/**
 * Created by Administrator on 2018\4\11 0011.
 */

public interface IHttpProcessor {
    void post(String url, Map<String, Object> parmas, ICallback iCallback);
}
