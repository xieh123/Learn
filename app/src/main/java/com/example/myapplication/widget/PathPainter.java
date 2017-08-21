package com.example.myapplication.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by xieH on 2017/1/4 0004.
 */
public class PathPainter extends View {

    private Path mPath;
    private Paint mPaint;
    private PathMeasure mPathMeasure;
    private float mAnimatorValue;
    private Path mDst;
    private float mLength;

    public PathPainter(Context context) {
        this(context, null);
    }

    public PathPainter(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PathPainter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    public void init() {
        mPathMeasure = new PathMeasure();

        // 构建Paint时直接加上去锯齿属性
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
//        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(5);

        mPath = new Path();
        mPath.addCircle(100, 60, 50, Path.Direction.CW);

        mPathMeasure.setPath(mPath, true);

        mLength = mPathMeasure.getLength();
        mDst = new Path();

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mAnimatorValue = (float) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.setDuration(3000);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mDst.reset();
        // 硬件加速的BUG
        mDst.lineTo(0, 0);

        float stop = mLength * mAnimatorValue;
//        mPathMeasure.getSegment(0, stop, mDst, true);

        float start = (float) (stop - ((0.5 - Math.abs(mAnimatorValue - 0.5)) * mLength));
        mPathMeasure.getSegment(start, stop, mDst, true);

        canvas.drawPath(mDst, mPaint);
    }
}
