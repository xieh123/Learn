package com.example.myapplication.ui.rxbus;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.util.RxBus;

import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by xieH on 2017/1/11 0011.
 */
public class RxBus11Activity extends AppCompatActivity {

    private TextView textTv;

    private Subscription mSubscription;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxbus_11);

        intView();

        initRxBus();
    }

    public void intView() {

        textTv = (TextView) findViewById(R.id.rxbus11_tv);


//        Observable.just(sharedPreferences.getString("cookie", ""))
//                .subscribe(new Action1<String>() {
//                    @Override
//                    public void call(String cookie) {
//                        //添加cookie
//                        builder.addHeader("Cookie", cookie);
//                    }
//                });

    }

    private void initRxBus() {
        mSubscription = RxBus.getInstance().toObservable(User.class)
                .subscribe(new Action1<User>() {
                               @Override
                               public void call(User user) {
                                   long id = user.getId();
                                   String name = user.getName();

                                   textTv.setText(name);
                               }
                           },
                        new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                // TODO: 处理异常
                            }
                        });
    }

    public void onPost(View v) {
        RxBus.getInstance().post(new User(1, "xieH"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }
}
