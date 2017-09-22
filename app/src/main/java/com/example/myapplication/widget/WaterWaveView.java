package com.example.myapplication.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by xieH on 2017/4/24 0024.
 */
public class WaterWaveView extends View {


    /**
     * 水波纹的高度
     */
    private int mWaveHeight;

    /**
     * 绘制水波纹的画笔
     */
    private Paint mPaint;

    /**
     * 绘制水波纹的路径
     */
    private Path mPath;

    /**
     * 屏幕宽度
     */
    private int mScreenWidth;

    private int mFu;

    /**
     * 水波纹移动的偏移值
     */
    private int mOffset;


    /**
     * 文字画笔
     */
    private Paint mTextPaint;

    /**
     * 进度值文字
     */
    private String mText = "1%";

    public WaterWaveView(Context context) {
        this(context, null);
    }

    public WaterWaveView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaterWaveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    public void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.parseColor("#5DCEC6"));
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        mFu = 50;  // 波浪的振幅

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mTextPaint.setTextSize(75);

        mOffset = 0;

        mWaveHeight = 100; // 进度值，也是水波纹的高度

        mPath = new Path();


        ValueAnimator animator = ValueAnimator.ofInt(0, mScreenWidth); // 设置移动范围为一个屏幕宽度
        animator.setDuration(2000);
        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int value = (int) valueAnimator.getAnimatedValue();
                mOffset = value; // 修改偏移量
                postInvalidate();
            }
        });

        animator.start();

        ValueAnimator animatorHeight = ValueAnimator.ofInt(0, getHeight() + mFu);
        animatorHeight.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mWaveHeight = (int) valueAnimator.getAnimatedValue();
                mText = String.valueOf((int) ((double) mWaveHeight / (double) (getHeight() + mFu) * 100)) + "%";
                if (mWaveHeight == getHeight() + mFu) { // 当水位高于屏幕高度，停止水波纹移动效果
                    valueAnimator.cancel();
                }

                if (mWaveHeight > getHeight() / 2) { // 当水位高于文字的时候，字体为白色，否则为浅蓝色
                    mTextPaint.setColor(Color.WHITE);

                } else {
                    mTextPaint.setColor(Color.parseColor("#5DCEC6"));
                }

                postInvalidate();
            }
        });

        animatorHeight.setDuration(30000); // 持续30秒
        animatorHeight.setInterpolator(new LinearInterpolator());
        animatorHeight.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.WHITE);
        mScreenWidth = getWidth();

        mPath.reset();

//        final int sc = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);
//
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.qq_icon);
//        canvas.drawBitmap(bitmap, getWidth() / 2 - bitmap.getWidth() / 2, getHeight() / 2 - bitmap.getHeight() / 2, null); // 绘制 bitmap
//
//        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN)); // 设置特效画笔
//        mPaint.setColor(Color.parseColor("#5DCEC6"));

        mPath.moveTo(mScreenWidth + mOffset, getHeight() - mWaveHeight); // 因为 y 轴正方向是向下的，所以减去水波纹的高度
        mPath.lineTo(mScreenWidth + mOffset, getHeight()); // 绘制右边的线段
        mPath.lineTo(-mScreenWidth + mOffset, getHeight()); // 绘制底部的线段
        mPath.lineTo(-mScreenWidth + mOffset, getHeight() - mWaveHeight); // 绘制左边的线段

        mPath.quadTo((-mScreenWidth * 3 / 4) + mOffset, getHeight() - mWaveHeight + mFu, (-mScreenWidth / 2) + mOffset, getHeight() - mWaveHeight); // 画出第一段波纹的第一条曲线
        mPath.quadTo((-mScreenWidth / 4) + mOffset, getHeight() - mWaveHeight - mFu, 0 + mOffset, getHeight() - mWaveHeight); // 画出第一段波纹的第二条曲线
        mPath.quadTo((mScreenWidth / 4) + mOffset, getHeight() - mWaveHeight + mFu, (mScreenWidth / 2) + mOffset, getHeight() - mWaveHeight); // 画出第二段波纹的第一条曲线
        mPath.quadTo((mScreenWidth * 3 / 4) + mOffset, getHeight() - mWaveHeight - mFu, mScreenWidth + mOffset, getHeight() - mWaveHeight); // 画出第二段波纹的第二条曲线
        mPath.close();

        canvas.drawPath(mPath, mPaint);

//        canvas.drawText(mText, getWidth() / 2 - mTextPaint.measureText(mText) / 2, getHeight() / 2, mTextPaint);
//        mPaint.setXfermode(null);
//        canvas.restoreToCount(sc); // 还原画布
    }
}
