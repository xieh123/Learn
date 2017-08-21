package com.example.myapplication.ui.tab2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.myapplication.R;

/**
 * Created by xieH on 2017/6/20 0020.
 */
public class BehaviorActivity extends AppCompatActivity {

    private TextView mDependentTv, mDependent11Tv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_behavior);


        initView();
    }

    public void initView() {
        mDependentTv = (TextView) findViewById(R.id.dependent);
        mDependent11Tv = (TextView) findViewById(R.id.dependent11);

        mDependentTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewCompat.offsetTopAndBottom(v, 5);
            }
        });

        mDependent11Tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewCompat.offsetTopAndBottom(v, 5);
            }
        });
    }
}
