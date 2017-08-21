package com.example.myapplication.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by xieH on 2016/12/24 0024.
 */
public class VideoProgressBar extends View {


    private static final String TAG = "VideoProgressBar";

    private Context mContext;

    /**
     * 取消状态为红色bar，反之为绿色bar
     */
    private boolean isCancel = false;

    /**
     * 正在录制的画笔
     */
    private Paint mRecordPaint;

    /**
     * 上滑取消时的画笔
     */
    private Paint mCancelPaint;

    /**
     * 是否显示
     */
    private int mVisibility;

    /**
     * 当前进度
     */
    private int progress;

    private OnProgressEndListener onProgressEndListener;


    public VideoProgressBar(Context context) {
        super(context);
    }

    public VideoProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.mContext = context;
        init();
    }


    private void init() {
        mVisibility = INVISIBLE;
        mRecordPaint = new Paint();
        mRecordPaint.setColor(Color.GREEN);
        mCancelPaint = new Paint();
        mCancelPaint.setColor(Color.RED);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mVisibility == VISIBLE) {
            int height = getHeight();
            int width = getWidth();

            int mid = width / 2;

            // 画出进度条
            if (progress < mid) {
                canvas.drawRect(progress, 0, width - progress, height, isCancel ? mCancelPaint : mRecordPaint);
            } else {
                if (onProgressEndListener != null) {
                    onProgressEndListener.onProgressEndListener();
                }
            }
        } else {
            canvas.drawColor(Color.argb(0, 0, 0, 0));
        }
    }


    /**
     * 设置进度
     *
     * @param progress
     */
    public void setProgress(int progress) {
        this.progress = progress;
        invalidate();
    }

    public void setCancel(boolean isCancel) {
        this.isCancel = isCancel;
        invalidate();
    }

    @Override
    public void setVisibility(int visibility) {
        this.mVisibility = visibility;
        invalidate();
    }

    public interface OnProgressEndListener {
        void onProgressEndListener();
    }

    public void setOnProgressEndListener(OnProgressEndListener onProgressEndListener) {
        this.onProgressEndListener = onProgressEndListener;
    }
}
