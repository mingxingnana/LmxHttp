package com.lmx.httpagent;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by Administrator on 2018\4\11 0011.
 */

public abstract class HttpCallback<Result> implements ICallback {
    @Override
    public void onSucess(String result) {

        Gson gson = new Gson();
        Class<?> clz = any(this);
        Result obresult = (Result) gson.fromJson(result, clz);
        onSucess(obresult);
    }

    protected Class<?> any(Object object) {
        Type genericSuperclass = object.getClass().getGenericSuperclass();
        Type[] actualTypeArguments = ((ParameterizedType) genericSuperclass).getActualTypeArguments();

        return (Class<?>) actualTypeArguments[0];
    }


    public abstract void onSucess(Result result);

    public abstract void onFailure();

    @Override
    public void onFailure(String error) {
        onFailure();
    }
}
