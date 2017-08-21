package com.example.myapplication.api.base;

/**
 * Created by xieH on 2016/12/2 0002.
 */
public class ApiException extends Exception {

    int code;

    public ApiException(int code, String s) {
        super(s);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
