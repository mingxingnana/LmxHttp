package com.lmx.http;

/**
 * Created by Administrator on 2018/4/9 0009.
 */

public class dataclass {
    int code;
    String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "dataclass{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
