package com.example.myapplication.ui.main;

import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.example.myapplication.R;

/**
 * Created by xieH on 2017/1/6 0006.
 */
public class SVGHandWritingActivity extends AppCompatActivity {


    private ImageView mImageView;

    private Animatable cursiveAvd;


    private ImageView mTranslateIv;

    private Animatable mTranslateAvb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hand_writing);

        initView();
    }

    public void initView() {

        mImageView = (ImageView) findViewById(R.id.hand_writing_iv);

        // 动态设置SVG背景
//        Drawable drawable = VectorDrawableCompat.create(getResources(), R.drawable.avd_handwriting_16, null);
//        mImageView.setBackground(drawable);

        // 动态设置SVG图标
//        mImageView.setImageResource(R.drawable.avd_handwriting_16);


        cursiveAvd = (Animatable) mImageView.getDrawable();
        cursiveAvd.start();





        mTranslateIv = (ImageView) findViewById(R.id.hand_writing_translate_iv);
        mTranslateAvb = (Animatable)mTranslateIv.getDrawable();
        mTranslateAvb.start();



    }
}
