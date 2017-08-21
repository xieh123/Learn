package com.example.myapplication.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.example.myapplication.R;

/**
 * Created by xieH on 2017/4/7 0007.
 */
public class SwitchButton extends View implements View.OnClickListener {

    private static final int DEF_H = 60;
    private static final int DEF_W = 120;

    private int mBackgroundColor;

    private int mNormalColor;

    private int mSelectColor;

    private int mBallColor;

    private Paint mBallPaint;

    private Paint mBackgroundPaint;

    private int mStrokeRadius;

    private float mSolidRadius;

    private RectF mBackgroundStrokeRectF;

    private int BALL_X_RIGHT;

    private State mCurrentState;

    private float mSwitchBallX;

    public SwitchButton(Context context) {
        this(context, null);
    }

    public SwitchButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwitchButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SwitchView);

        mNormalColor = typedArray.getColor(R.styleable.SwitchView_normal_color, Color.GRAY);
        mSelectColor = typedArray.getColor(R.styleable.SwitchView_select_color, Color.GREEN);
        mBallColor = typedArray.getColor(R.styleable.SwitchView_ball_color, Color.WHITE);

        typedArray.recycle();

        init();
    }

    public void init() {
        mBackgroundColor = mNormalColor;

        mBallPaint = createPaint(mBallColor, 0, Paint.Style.FILL, 0);
        mBackgroundPaint = createPaint(mBackgroundColor, 0, Paint.Style.FILL, 0);

        mCurrentState = State.CLOSE;

        setOnClickListener(this);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        int mViewHeight = h;
        int mViewWidth = w;

        // 默认描边宽度是控件宽度的1/30
        float mStrokeWidth = w * 1.0f / 30;

        mStrokeRadius = mViewHeight / 2;
        mSolidRadius = (mViewHeight - 2 * mStrokeWidth) / 2;
        BALL_X_RIGHT = mViewWidth - mStrokeRadius;

        mSwitchBallX = mStrokeRadius;
        mBackgroundStrokeRectF = new RectF(0, 0, mViewWidth, mViewHeight);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int measureWidth;
        int measureHeight;

        switch (widthMode) {
            case MeasureSpec.UNSPECIFIED:
                break;
            case MeasureSpec.AT_MOST: // wrap_content
                measureWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEF_W, getResources().getDisplayMetrics());
                widthMeasureSpec = MeasureSpec.makeMeasureSpec(measureWidth, MeasureSpec.EXACTLY);
                break;
            case MeasureSpec.EXACTLY:
                break;
            default:
                break;
        }

        switch (heightMode) {
            case MeasureSpec.UNSPECIFIED: // wrap_content
                measureHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEF_H, getResources().getDisplayMetrics());
                heightMeasureSpec = MeasureSpec.makeMeasureSpec(measureHeight, MeasureSpec.EXACTLY);
                break;
            case MeasureSpec.AT_MOST:
                break;
            case MeasureSpec.EXACTLY:
                break;
            default:
                break;
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawSwitchBackground(canvas);
        drawSwitchBall(canvas);
    }

    private void drawSwitchBackground(Canvas canvas) {
        if (mCurrentState == State.OPEN) {
            mBackgroundPaint.setColor(mSelectColor);
        } else {
            mBackgroundPaint.setColor(mNormalColor);
        }
        canvas.drawRoundRect(mBackgroundStrokeRectF, mStrokeRadius, mStrokeRadius, mBackgroundPaint);
    }

    private void drawSwitchBall(Canvas canvas) {
        if (mCurrentState == State.OPEN) {
            mSwitchBallX = BALL_X_RIGHT;
        }
        canvas.drawCircle(mSwitchBallX, mStrokeRadius, mSolidRadius, mBallPaint);
    }


    private Paint createPaint(int paintColor, int textSize, Paint.Style style, int lineWidth) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(paintColor);
        paint.setStrokeWidth(lineWidth);
        paint.setDither(true); // 设置防抖动
        paint.setTextSize(textSize);
        paint.setStyle(style);
        paint.setStrokeCap(Paint.Cap.ROUND); // 设置圆角的笔触
        paint.setStrokeJoin(Paint.Join.ROUND);
        return paint;
    }

    private enum State {
        OPEN, CLOSE
    }

    @Override
    public void onClick(View view) {
        mCurrentState = (mCurrentState == State.CLOSE ? State.OPEN : State.CLOSE);

        if (mCurrentState == State.CLOSE) {
            animate(BALL_X_RIGHT, mStrokeRadius, mSelectColor, mNormalColor);
        } else {
            animate(mStrokeRadius, BALL_X_RIGHT, mNormalColor, mSelectColor);
        }

        if (onCheckedChangeListener != null) {
            if (mCurrentState == State.OPEN) {
                onCheckedChangeListener.onCheckedChange(this, true);
            } else {
                onCheckedChangeListener.onCheckedChange(this, false);
            }
        }
    }

    private void animate(int from, int to, int startColor, int endColor) {
        ValueAnimator translate = ValueAnimator.ofFloat(from, to);
        translate.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mSwitchBallX = ((float) animation.getAnimatedValue());
                postInvalidate();
            }
        });

        ValueAnimator color = ValueAnimator.ofObject(new ColorEvaluator(), startColor, endColor);
        color.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mBackgroundColor = ((int) animation.getAnimatedValue());
                mBackgroundPaint.setColor(mBackgroundColor);
                postInvalidate();
            }
        });

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(translate, color);
        animatorSet.setDuration(200);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                setClickable(false);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                setClickable(true);
            }
        });
        animatorSet.start();
    }

    public void setSelected(boolean isSelect) {
        if (isSelect) {
            mCurrentState = State.OPEN;
        } else {
            mCurrentState = State.CLOSE;
        }

        invalidate();
    }


    public interface onCheckedChangeListener {
        void onCheckedChange(SwitchButton view, boolean isChecked);
    }

    private onCheckedChangeListener onCheckedChangeListener;

    public void setOnCheckedChangeListener(onCheckedChangeListener onCheckedChangeListener) {
        this.onCheckedChangeListener = onCheckedChangeListener;
    }

}
