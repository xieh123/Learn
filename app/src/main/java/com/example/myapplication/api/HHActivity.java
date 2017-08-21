package com.example.myapplication.api;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by xieH on 2016/12/2 0002.
 */
public class HHActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();

    }

    public void initView() {


//        RtHttp.with(this) //设置Context
//                .setShowWaitingDialog(true) //设置显示网络加载动画
//           //     .setObservable(MobileApi.response(map, ProtocolUtils.PROTOCOL_MSG_ID_LOGIN))
//                .subscriber(new ApiSubscriber<jsonobject>() { //设置Subscriber，ApiSubscriber封装Subscriber;返回JSONObject仅是因为适配替换成Retrofit前的老代码
//                    @Override
//                    public void onNext(JSONObject result) {    //只实现OnNext方法
//                        //具体业务逻辑
//                    }
//                });


    }
}
