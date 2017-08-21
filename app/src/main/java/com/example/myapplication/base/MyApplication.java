package com.example.myapplication.base;

import android.app.Application;

/**
 * xpkUISDK自定义Application
 *
 */
public class MyApplication extends Application {

    private static MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;


    }

    public static MyApplication getInstance() {
        return instance;
    }

}
