package com.example.myapplication.widget;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by xieH on 2017/3/20 0020.
 */
public class GuideLayout extends FrameLayout implements View.OnClickListener {

    private Context mContext;

    private View mCustomView;
    private View mExitView;

    private OnExitClickListener onExitClickListener;

    public GuideLayout(Context context) {
        this(context, null);
    }

    public GuideLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GuideLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;

        init();
    }

    public void init() {


    }

    public void setCustomView(View view) {
        if (view != null) {
            this.mCustomView = view;
            this.addView(view);
        }
    }

    public void show() {
        this.setBackgroundResource(android.R.color.transparent);
        ((FrameLayout) ((Activity) mContext).getWindow().getDecorView()).addView(this);
    }

    public void hide() {
        ((FrameLayout) ((Activity) mContext).getWindow().getDecorView()).removeView(this);
    }

    @Override
    public void onClick(View view) {
        if (onExitClickListener != null) {
            onExitClickListener.onClick();
        }
    }

    public interface OnExitClickListener {
        void onClick();

    }

    /**
     * 设置监听
     *
     * @param resId               需要监听的View
     * @param onExitClickListener
     */
    public void setOnExitClickListener(int resId, OnExitClickListener onExitClickListener) {
        this.onExitClickListener = onExitClickListener;

        mExitView = mCustomView.findViewById(resId);
        if (mExitView != null) {
            mExitView.setOnClickListener(this);
        }
    }
}
