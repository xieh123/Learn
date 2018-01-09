package com.example.myapplication.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.myapplication.R;
import com.example.myapplication.util.DensityUtils;

/**
 * Created by xieH on 2017/10/17 0017.
 */
public class SlideLayout extends RelativeLayout {

    private Context mContext;

    private CheckBox mCheckBox;
    private LinearLayout mContentLl;

    /**
     * 偏离的宽度，即 CheckBox 的宽度
     */
    private int mOffset = 30;

    public SlideLayout(Context context) {
        this(context, null);
    }

    public SlideLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.mContext = context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mCheckBox = (CheckBox) findViewById(R.id.checkbox);
        mContentLl = (LinearLayout) findViewById(R.id.item_content_ll);

        mOffset = DensityUtils.dp2px(mContext, mOffset);
    }


    public void openAnimation() {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, 1);
        valueAnimator.setDuration(300);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float fraction = valueAnimator.getAnimatedFraction();
                int endX = (int) (-mOffset * fraction);
                doAnimationSet(endX, fraction);
            }
        });
        valueAnimator.start();
    }

    public void closeAnimation() {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, 1);
        valueAnimator.setDuration(150);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float fraction = valueAnimator.getAnimatedFraction();
                int endX = (int) (-mOffset * (1 - fraction));
                doAnimationSet(endX, (1 - fraction));
            }
        });
        valueAnimator.start();
    }

    private void doAnimationSet(int dx, float fraction) {
        mContentLl.scrollTo(dx, 0);
        mCheckBox.setScaleX(fraction);
        mCheckBox.setScaleY(fraction);
        mCheckBox.setAlpha(fraction * 255);
    }

    public void open() {
        mContentLl.scrollTo(-mOffset, 0);
    }

    public void close() {
        mContentLl.scrollTo(0, 0);
    }
}
