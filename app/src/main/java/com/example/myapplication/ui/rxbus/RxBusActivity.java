package com.example.myapplication.ui.rxbus;

import android.content.Intent;
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
public class RxBusActivity extends AppCompatActivity {

    private TextView textTv;

    private Subscription mSubscription;

    private int index = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxbus);

        intView();

        initRxBus();
    }

    public void intView() {

        textTv = (TextView) findViewById(R.id.rxbus_tv);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rxbus_fl, new TestFragment())
                .commit();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (!mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }


    public void next(View v) {
        Intent intent = new Intent(this, RxBus11Activity.class);
        startActivity(intent);
    }

    public void onPost(View v) {
        index++;
        RxBus.getInstance().post(new User(1, "xieH" + index));
    }
}
