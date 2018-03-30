package com.example.myapplication.ui.tab3;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.myapplication.R;
import com.example.myapplication.util.ShareUtils;
import com.xieh.imagepicker.SelectImageActivity;
import com.xieh.imagepicker.config.SelectOptions;

import java.util.ArrayList;

/**
 * Created by xieH on 2018/2/26 0026.
 */
public class SampleRecyclerViewActivity extends AppCompatActivity {

    private ImageView mImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_recycler);

        initView();
    }

    private void initView() {
        mImageView = (ImageView) findViewById(R.id.imageView);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Animation animation = AnimationUtils.loadAnimation(SampleRecyclerViewActivity.this, R.anim.translate_anim);
                mImageView.startAnimation(animation);
            }
        }, 200);
    }

    public void share(View v) {
        SelectImageActivity.show(this, new SelectOptions.Builder()
                .setHasCam(true)
//                .setCrop(300, 300)
                .setSelectCount(5)
//                .setSelectedImages(new String[2])
                .setCallback(new SelectOptions.Callback() {
                    @Override
                    public void doSelected(ArrayList<String> images) {
                        ShareUtils.shareImages(SampleRecyclerViewActivity.this, images);
                    }
                }).build());
    }


    /**
     * 基于坐标对View进行模拟点击事件
     *
     * @param view
     * @param x
     * @param y
     */
    private void simulateTouchEvent(View view, Float x, Float y) {
        long downTime = SystemClock.uptimeMillis();
        long eventTime = SystemClock.uptimeMillis() + 100;

        int metaState = 0;
        MotionEvent downEvent = MotionEvent.obtain(downTime, eventTime,
                MotionEvent.ACTION_DOWN, x, y, metaState);
        view.dispatchTouchEvent(downEvent);

        MotionEvent upEvent = MotionEvent.obtain(downTime + 1000, eventTime + 1000,
                MotionEvent.ACTION_UP, x, y, metaState);
        view.dispatchTouchEvent(upEvent);
    }
}
