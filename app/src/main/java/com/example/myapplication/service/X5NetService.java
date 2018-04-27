//package com.example.myapplication.service;
//
//import android.app.IntentService;
//import android.content.Intent;
//import android.support.annotation.Nullable;
//
///**
// * 开启线程异步初始化X5内核
// * <p>
// * Created by xieH on 2018/4/24 0024.
// */
//public class X5NetService extends IntentService {
//
//    public X5NetService() {
//        super(TAG);
//    }
//
//    public X5NetService(String name) {
//        super(TAG);
//    }
//
//    @Override
//    public void onHandleIntent(@Nullable Intent intent) {
//        initX5Web();
//    }
//
//    public void initX5Web() {
//        if (!QbSdk.isTbsCoreInited()) {
//            QbSdk.preInit(getApplicationContext(), null);// 设置X5初始化完成的回调接口
//        }
//        QbSdk.initX5Environment(getApplicationContext(), cb);
//
//    }
//
//    QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {
//        @Override
//        public void onViewInitFinished(boolean arg0) {
//            // TODO Auto-generated method stub
//        }
//
//        @Override
//        public void onCoreInitFinished() {
//            // TODO Auto-generated method stub
//        }
//
//    };
//}
