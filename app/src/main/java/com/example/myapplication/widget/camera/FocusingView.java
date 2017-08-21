package com.example.myapplication.widget.camera;

import android.content.Context;
import android.graphics.Point;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.myapplication.R;

/**
 * Created by xieH on 2016/12/30 0030.
 */
public class FocusingView  extends ImageView {

    private Animation mAnimation;
    private Handler mHandler;

    public FocusingView(Context context) {
        this(context, null);
    }

    public FocusingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FocusingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    public void init() {
        mAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.focusview_show);
        setVisibility(View.GONE);
        mHandler = new Handler();
    }

    /**
     * 显示聚焦图案
     *
     * @param point
     */
    public void startFocus(Point point) {

        // 根据触摸的坐标设置聚焦图案的位置
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) getLayoutParams();
        params.topMargin = point.y - getMeasuredHeight() / 2;
        params.leftMargin = point.x - getMeasuredWidth() / 2;
        setLayoutParams(params);
        // 设置控件可见，并开始动画
        setVisibility(View.VISIBLE);
        setImageResource(R.drawable.ms_video_focus_icon);
        startAnimation(mAnimation);
        // 3秒后隐藏View。在此处设置是由于可能聚焦事件可能不触发。
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                setVisibility(View.GONE);
            }
        }, 3500);
    }

    /**
     * 聚焦成功回调
     */
    public void onFocusSuccess() {
        setImageResource(R.drawable.ms_video_focus_icon);
        // 移除在startFocus中设置的callback，1秒后隐藏该控件
        mHandler.removeCallbacks(null, null);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                setVisibility(View.GONE);
            }
        }, 1000);
    }

    /**
     * 聚焦失败回调
     */
    public void onFocusFailed() {
        setImageResource(R.drawable.ms_video_focus_icon);
        // 移除在startFocus中设置的callback，1秒后隐藏该控件
        mHandler.removeCallbacks(null, null);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                setVisibility(View.GONE);
            }
        }, 1000);
    }
}
