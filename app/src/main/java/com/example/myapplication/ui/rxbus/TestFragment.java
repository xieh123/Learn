package com.example.myapplication.ui.rxbus;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.utils.RxBus;

import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by xieH on 2017/1/11 0011.
 */
public class TestFragment extends Fragment {

    private Subscription mSubscription;

    private TextView textTv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_rxbus, null);

        textTv = (TextView) view.findViewById(R.id.rxbus_text_tv);

        initRxBus();

        return view;
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
}
