package com.example.myapplication.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

import com.example.myapplication.R;

/**
 * Created by xieH on 2017/7/17 0017.
 */
public class StateTextView extends TextView {

    private Context mContext;

    private GradientDrawable mBackground = new GradientDrawable();

    private int mBackgroundColor;

    private int mCornerRadius;

    private int mStrokeWidth;

    private int mStrokeColor;

    private boolean isRadiusHalfHeight;

    private boolean isWidthHeightEqual;

    public StateTextView(Context mContext) {
        this(mContext, null);
    }

    public StateTextView(Context mContext, AttributeSet attrs) {
        this(mContext, attrs, 0);
    }

    public StateTextView(Context mContext, AttributeSet attrs, int defStyleAttr) {
        super(mContext, attrs, defStyleAttr);
        this.mContext = mContext;
        init(mContext, attrs);
    }


    private void init(Context mContext, AttributeSet attrs) {
        TypedArray ta = mContext.obtainStyledAttributes(attrs, R.styleable.StateTextView);
        mBackgroundColor = ta.getColor(R.styleable.StateTextView_normalBackgroundColor, Color.TRANSPARENT);
        mCornerRadius = ta.getDimensionPixelSize(R.styleable.StateTextView_cornerRadius, 0);
        mStrokeWidth = ta.getDimensionPixelSize(R.styleable.StateTextView_strokeWidth, 0);
        mStrokeColor = ta.getColor(R.styleable.StateTextView_strokeColor, Color.TRANSPARENT);
        isRadiusHalfHeight = ta.getBoolean(R.styleable.StateTextView_isRadiusHalfHeight, false);
        isWidthHeightEqual = ta.getBoolean(R.styleable.StateTextView_isWidthHeightEqual, false);

        ta.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (isWidthHeightEqual() && getWidth() > 0 && getHeight() > 0) {
            int max = Math.max(getWidth(), getHeight());
            int measureSpec = MeasureSpec.makeMeasureSpec(max, MeasureSpec.EXACTLY);
            super.onMeasure(measureSpec, measureSpec);
            return;
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (isRadiusHalfHeight()) {
            setCornerRadius(getHeight() / 2);
        } else {
            setBgSelector();
        }
    }

    @Override
    public void setBackgroundColor(int mBackgroundColor) {
        this.mBackgroundColor = mBackgroundColor;
        setBgSelector();
    }

    public void setCornerRadius(int mCornerRadius) {
        this.mCornerRadius = dp2px(mCornerRadius);
        setBgSelector();
    }

    public void setStrokeWidth(int mStrokeWidth) {
        this.mStrokeWidth = dp2px(mStrokeWidth);
        setBgSelector();
    }

    public void setStrokeColor(int mStrokeColor) {
        this.mStrokeColor = mStrokeColor;
        setBgSelector();
    }

    public void setIsRadiusHalfHeight(boolean isRadiusHalfHeight) {
        this.isRadiusHalfHeight = isRadiusHalfHeight;
        setBgSelector();
    }

    public void setIsWidthHeightEqual(boolean isWidthHeightEqual) {
        this.isWidthHeightEqual = isWidthHeightEqual;
        setBgSelector();
    }

    public int getBackgroundColor() {
        return mBackgroundColor;
    }

    public int getCornerRadius() {
        return mCornerRadius;
    }

    public int getStrokeWidth() {
        return mStrokeWidth;
    }

    public int getStrokeColor() {
        return mStrokeColor;
    }

    public boolean isRadiusHalfHeight() {
        return isRadiusHalfHeight;
    }

    public boolean isWidthHeightEqual() {
        return isWidthHeightEqual;
    }

    protected int dp2px(float dp) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    protected int sp2px(float sp) {
        final float scale = this.mContext.getResources().getDisplayMetrics().scaledDensity;
        return (int) (sp * scale + 0.5f);
    }

    private void setDrawable(GradientDrawable gd, int color, int mStrokeColor) {
        gd.setColor(color);
        gd.setCornerRadius(mCornerRadius);
        gd.setStroke(mStrokeWidth, mStrokeColor);
    }

    public void setBgSelector() {
        StateListDrawable bg = new StateListDrawable();

        setDrawable(mBackground, mBackgroundColor, mStrokeColor);
        bg.addState(new int[]{-android.R.attr.state_pressed}, mBackground);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) { //16
            setBackground(bg);
        } else {
            //noinspection deprecation
            setBackgroundDrawable(bg);
        }
    }
}
