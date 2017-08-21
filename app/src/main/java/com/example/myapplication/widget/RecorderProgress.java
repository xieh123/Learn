package com.example.myapplication.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;


/**
 * Created by xieh on 2016/9/13.
 */

public class RecorderProgress extends View {

    private Paint mPaint = new Paint();

    private volatile State mState = State.PAUSE;

    private float maxTime = 10 * 1000.0f;  // 10s
    private float minTime = 2 * 1000.0f;   // 2s

    private float recordTime = 0.0f;

    /**
     * 取消状态为红色bar，反之为绿色bar
     */
    private boolean isCancel = false;

    private long curTime;

    private Context mContext;

    private OnProgressEndListener onProgressEndListener;

    public RecorderProgress(Context context) {
        super(context);
        init(context);
    }


    public RecorderProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public RecorderProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    private void init(Context context) {
        this.mContext = context;
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.GREEN);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        long time = System.currentTimeMillis();

        if (mState == State.START) {
            int measuredWidth = getMeasuredWidth();
            float left = measuredWidth / 2.0f / maxTime;

            recordTime = (time - curTime);

//            if (recordTime >= minTime) {
//                mPaint.setColor(Color.RED);
//            }

            if (isCancel) {
                mPaint.setColor(Color.RED);
            } else {
                mPaint.setColor(Color.GREEN);
            }

            left *= recordTime;

            if (left < measuredWidth / 2.0f) {
                canvas.drawRect(left, 0.0f, measuredWidth - left, getMeasuredHeight(), mPaint);
                invalidate();
            } else {
                if (onProgressEndListener != null) {
                    onProgressEndListener.onProgressEndListener();
                }
            }
        } else {
            canvas.drawColor(Color.argb(0, 0, 0, 0));
        }
    }

    public void setMinTime(float minTime) {
        this.minTime = minTime;
    }

    public void setMaxTime(float maxTime) {
        this.maxTime = maxTime;
    }

    public float getRecordTime() {
        return recordTime;
    }

    public void setCancel(boolean isCancel) {
        this.isCancel = isCancel;
    }

    public void startAnimation() {
        if (mState != State.START) {
            mState = State.START;
            this.curTime = System.currentTimeMillis();
            invalidate();
            setVisibility(VISIBLE);
        }
    }

    public void stopAnimation() {
        if (mState != State.PAUSE) {
            mState = State.PAUSE;
            setVisibility(INVISIBLE);
        }
    }


    enum State {
        START(1, "开始"),
        PAUSE(2, "暂停");

        State(int code, String message) {
            this.code = code;
            this.message = message;
        }

        private int code;
        private String message;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    public interface OnProgressEndListener {
        void onProgressEndListener();
    }

    public void setOnProgressEndListener(OnProgressEndListener onProgressEndListener) {
        this.onProgressEndListener = onProgressEndListener;
    }
}
