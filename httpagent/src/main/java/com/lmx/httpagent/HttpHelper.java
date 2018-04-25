package com.lmx.httpagent;

import java.util.Map;

/**
 * Created by Administrator on 2018\4\11 0011.
 */

public class HttpHelper implements IHttpProcessor {
    private static HttpHelper ourInstance;

    public static HttpHelper obtion() {
        if (ourInstance == null) {
            synchronized (HttpHelper.class) {
                if (ourInstance == null) {
                    ourInstance = new HttpHelper();
                }
            }
        }
        return ourInstance;
    }

    private HttpHelper() {
    }

    public static IHttpProcessor mIhttpProcessor = null;

    public static void init(IHttpProcessor m) {
        mIhttpProcessor = m;
    }

    @Override
    public void post(String url, Map<String, Object> parmas, ICallback iCallback) {

        if (parmas != null && !parmas.isEmpty()) {
            url = url + "?";
            for (Map.Entry<String, Object> entry : parmas.entrySet()) {
                System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
                url = url + "&" + entry.getKey() + "=" + entry.getValue();
            }
        }
        if (mIhttpProcessor == null)
            throw new RuntimeException("your IHttpProcessor is not initialized,please in your appcaction initialized");
        mIhttpProcessor.post(url, parmas, iCallback);
    }
}
