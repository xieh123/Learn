package com.example.myapplication;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by xieH on 2017/6/15 0015.
 */
public class Tab4Activity extends AppCompatActivity {


    private Handler mHandler = new Handler();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab4);


//        getSupportFragmentManager().beginTransaction()
//                .replace()
//                .addToBackStack(null); // 添加到回退栈里面


        mHandler.sendEmptyMessage(1);


        initView();
    }


    private void initView(){



    }
}
