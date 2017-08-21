package com.example.myapplication.ui.tab2;

import android.animation.ValueAnimator;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.myapplication.R;

/**
 * Created by xieH on 2017/8/3 0003.
 */
public class DrawableActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mImageView;

    private ImageView mCDBoxIv, mHandIv;

    private Button mPlayBtn, mPauseBtn;

    private Handler handler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawable);

        initView();

    }

    private void initView() {
        mImageView = (ImageView) findViewById(android.R.id.progress);

        mCDBoxIv = (ImageView) findViewById(R.id.imageView11);
        mHandIv = (ImageView) findViewById(R.id.imageView22);

        mPlayBtn = (Button) findViewById(R.id.play_bt);
        mPauseBtn = (Button) findViewById(R.id.pause_bt);

        mPlayBtn.setOnClickListener(this);
        mPauseBtn.setOnClickListener(this);

        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, 100);
        valueAnimator.setDuration(10000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int value = (int) valueAnimator.getAnimatedValue();
                setLevel(value);
            }
        });
        valueAnimator.start();




    }

    /**
     * level的取值区间在0~10000之间
     *
     * @param level
     */
    public void setLevel(int level) {
        Drawable drawable = mImageView.getDrawable();
        drawable.setLevel(3000 + 6000 * level / 100);
    }


    ////////////////////////////////////
    private void play() {
        ValueAnimator animator = ValueAnimator.ofInt(0, 10000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int level = (int) animation.getAnimatedValue();
                mHandIv.getDrawable().setLevel(level);
            }
        });

        animator.setDuration(1000);
        animator.start();

        handler.postDelayed(mRunnable, 50);
    }

    /**
     * 暂停动画
     */
    private void pause() {
        ValueAnimator animator = ValueAnimator.ofInt(10000, 0);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int level = (int) animation.getAnimatedValue();
                mHandIv.getDrawable().setLevel(level);
            }
        });

        animator.setDuration(1000);
        animator.start();
    }


    Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            int level = mCDBoxIv.getDrawable().getLevel();
            level = level + 200;
            if (level > 10000) {
                level = level - 10000;
            }
            mCDBoxIv.getDrawable().setLevel(level);

            handler.postDelayed(mRunnable, 50);
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.play_bt:
                play();
                break;
            case R.id.pause_bt:
                pause();
                handler.removeCallbacks(mRunnable);
                break;
            default:
                break;
        }
    }
}
