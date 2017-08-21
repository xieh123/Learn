package com.example.myapplication.ui.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.widget.camera.MySurfaceView;

/**
 * Created by xieH on 2016/12/30 0030.
 */
public class SurfaceViewActivity extends AppCompatActivity{


    private MySurfaceView mSurfaceView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surfaceview);

        initView();
    }


    public void initView() {
        mSurfaceView = (MySurfaceView) findViewById(R.id.surfaceview_sfv);

    }


    @Override
    protected void onStart() {
        super.onStart();
        if (mSurfaceView != null) {
            mSurfaceView.onStart();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mSurfaceView != null) {
            mSurfaceView.onStop();
        }
    }



}
