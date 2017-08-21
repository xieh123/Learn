package com.example.myapplication.widget;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.example.myapplication.R;


/**
 * Created by xieH on 2017/5/23 0023.
 */
public class VideoSeekBarView extends View {

    private Context mContext;

    /**
     * 控件宽高
     */
    private int mViewHeight;
    private int mViewWidth;
    private int mInitLeftAndRightMark;

    private int mBorderWidth;
    private int mButtonWidth;
    private int mMarkWidth;
    private int mMarkRadius;
    private int mFrameWidth;
    private int mFrame = -1;
    private int mRightLimit = -1;

    private Paint mPaintBorder;
    private Paint mPaintLeft;
    private Paint mPaintRight;
    private Paint mPaintMask;
    private Paint mPaintMark;

    private Bitmap mLeftBitmap;
    private Bitmap mRightBitmap;

    /**
     * 左边黑色边框到控件左部距离 也就是左边透明蒙层宽度
     */
    private int mLeftMarkWidth;

    /**
     * 右边黑色边框到控件右部距离 也就是右边透明蒙层宽度
     */
    private int mRightMarkWidth;

    /**
     * 滑杆所在的位置
     */
    private int mPlayMark;


    /**
     * 标识左右滑动
     */
    private boolean mIsLeftMove = false;
    private boolean mIsRightMove = false;

    private boolean mIsShowPlayMark = true;

    private OnMarkMoveListener mOnMarkMoveListener;
    private ValueAnimator mValueAnimator;
    private float mAnimatorDuration = 5 * 1000;
    private boolean mIsSmallVideo;
    private int FRAME_WIDTH;
    private int MARGIN_WIDTH;

    /**
     * 绘制缩略图画笔
     */
    private Paint thumbPaint;

    /**
     * 需要显示的缩略图数量
     */
    private int thumbCount = 7;

    /**
     * 视频时长
     */
    private int mVideoDuration;

    /**
     * 缩略图Bitmap
     */
    private Bitmap[] mThumbBitmaps;

    /**
     * 是否清空内存 - 销毁资源
     */
    private boolean isClearMemory = true;

    public interface OnMarkMoveListener {
        void onMarkMoveListener(int leftMark, int rightMark, int total);
    }

    public VideoSeekBarView(Context context) {
        this(context, null, 0);
    }

    public VideoSeekBarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VideoSeekBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;

        setUpParams();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mViewHeight == 0 && mViewWidth == 0) {
            mViewWidth = getMeasuredWidth();
            mViewHeight = getMeasuredHeight();
        }
    }

    private void setUpParams() {
        int mColorBlack = 0xff000000;
        int mColorGreen = 0xff32dca3;
        int mColorMask = 0xE6FFFFFF;

        MARGIN_WIDTH = dp2px(mContext, 0);
        FRAME_WIDTH = dp2px(mContext, 56);
        mBorderWidth = dp2px(mContext, 2);
        mMarkRadius = dp2px(mContext, 4);
        mMarkWidth = dp2px(mContext, 4);
        mButtonWidth = dp2px(mContext, 20);

        mInitLeftAndRightMark = dp2px(mContext, 0);
        mLeftMarkWidth = mInitLeftAndRightMark;
        mRightMarkWidth = mInitLeftAndRightMark;
        mFrameWidth = FRAME_WIDTH;

        thumbPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        mPaintBorder = new Paint();
        mPaintBorder.setColor(mColorBlack);
        mPaintBorder.setStyle(Paint.Style.FILL);
        mPaintBorder.setStrokeWidth(mBorderWidth);
        mPaintBorder.setAntiAlias(true);

        mLeftBitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.video_seek_bar_left);
        mPaintLeft = new Paint();

        mRightBitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.video_seek_bar_right);
        mPaintRight = new Paint();

        mPaintMark = new Paint();
        mPaintMark.setColor(mColorGreen);

        mPaintMask = new Paint();
        mPaintMask.setColor(mColorMask);

        mPlayMark = mLeftMarkWidth + mButtonWidth;
        initRightMaskAndDuration();
        //   animatePlay();
        invalidate();
    }

    /**
     * 初始化右边距与滑杆时长
     */
    private void initRightMaskAndDuration() {
        if (mFrame != -1) {
            if (mFrame < 5) {
                int rightMask = mViewWidth - mLeftMarkWidth - 2 * mButtonWidth - mFrame * mFrameWidth;
                if (rightMask < mInitLeftAndRightMark) {
                    mRightMarkWidth = mInitLeftAndRightMark;
                } else {
                    mRightMarkWidth = rightMask;
                    mRightLimit = rightMask;
                }
            } else {
                mRightMarkWidth = mInitLeftAndRightMark;
            }
        }
        if (mIsSmallVideo) {
            mAnimatorDuration = mFrame * 1000;
        } else {
            mAnimatorDuration = mFrame * 1000 * 2;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 绘制缩略图 - 防止进行销毁中,导致触发
        if (!isClearMemory && mThumbBitmaps != null) {
            // 遍历缩略图数量
            for (int i = 0; i < thumbCount; i++) {
                if (isClearMemory) { // 如果正在回收中,则跳出方法
                    break;
                }
                if (mThumbBitmaps[i] != null) {
                    try {
                        // 绘制缩略图
                        canvas.drawBitmap(mThumbBitmaps[i], i * mThumbBitmaps[i].getWidth(), 0, thumbPaint);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        /////////////////////////////////////////
        // 画左边的按钮
        canvas.drawBitmap(mLeftBitmap, mLeftMarkWidth, MARGIN_WIDTH, mPaintLeft);
        // 画右边按钮
        canvas.drawBitmap(mRightBitmap, mViewWidth - mRightMarkWidth - mButtonWidth, MARGIN_WIDTH, mPaintRight);
        // 画中间两条线
        canvas.drawLine(mLeftMarkWidth + mButtonWidth,
                MARGIN_WIDTH + mBorderWidth / 2,
                mViewWidth - mRightMarkWidth - mButtonWidth,
                MARGIN_WIDTH + mBorderWidth / 2,
                mPaintBorder);
        canvas.drawLine(mLeftMarkWidth + mButtonWidth,
                mViewHeight - MARGIN_WIDTH + mBorderWidth / 2,
                mViewWidth - mRightMarkWidth - mButtonWidth,
                mViewHeight - MARGIN_WIDTH + mBorderWidth / 2,
                mPaintBorder);

        // 画滑杆
        if (mIsShowPlayMark) {
            canvas.drawRect(mPlayMark, dp2px(mContext, 1), mPlayMark + mMarkWidth, mViewHeight, mPaintMark);
        }
        // 画左右蒙层
        canvas.drawRect(0, MARGIN_WIDTH, mLeftMarkWidth, mViewHeight - MARGIN_WIDTH, mPaintMask);
        canvas.drawRect(mViewWidth - mRightMarkWidth, MARGIN_WIDTH, mViewWidth, mViewHeight - MARGIN_WIDTH, mPaintMask);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (isActionInLeftButton(event.getX(), event.getY())) {
                    mIsLeftMove = true;
                    mIsRightMove = false;
                    return true;
                } else if (isActionInRightButton(event.getX(), event.getY())) {
                    mIsRightMove = true;
                    mIsLeftMove = false;
                    return true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (mIsLeftMove && !mIsRightMove) {
                    // 获取左侧边距
                    mLeftMarkWidth = limitLeftMark((int) event.getX());
                    if (null != mOnMarkMoveListener) {
                        mOnMarkMoveListener.onMarkMoveListener(mLeftMarkWidth + mButtonWidth,
                                mViewWidth - mButtonWidth - mRightMarkWidth, mViewWidth);
                    }
                    invalidate();
                    return true;
                } else if (mIsRightMove && !mIsLeftMove) {
                    // 获取右侧边距
                    mRightMarkWidth = limitRightMark((int) event.getX());
                    if (null != mOnMarkMoveListener) {
                        mOnMarkMoveListener.onMarkMoveListener(mLeftMarkWidth + mButtonWidth,
                                mViewWidth - mButtonWidth - mRightMarkWidth, mViewWidth);
                    }
                    invalidate();
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onTouchEvent(event);
    }

    private boolean isActionInLeftButton(float x, float y) {
        if (x <= mLeftMarkWidth + mButtonWidth && x >= mLeftMarkWidth) {
            if (0 <= y && y <= mViewHeight) {
                return true;
            }
        }
        return false;
    }

    private boolean isActionInRightButton(float x, float y) {
        if (x <= mViewWidth - mRightMarkWidth && x >= mViewWidth - mRightMarkWidth - mButtonWidth) {
            if (0 <= y && y <= mViewHeight) {
                return true;
            }
        }
        return false;
    }

    public void startAnimatePlay() {
        mValueAnimator = ValueAnimator.ofFloat(0, 1.0f);
        mValueAnimator.setDuration((int) mAnimatorDuration);
        mValueAnimator.setTarget(this);
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                mPlayMark = calculatePlayIndex(value);
                invalidate();
            }
        });
        mValueAnimator.setInterpolator(new LinearInterpolator());
        mValueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mValueAnimator.start();
    }

    private int calculatePlayIndex(float value) {
        int sum = (mViewWidth - mRightMarkWidth - mButtonWidth) - (mLeftMarkWidth + mButtonWidth);
        return mLeftMarkWidth + mButtonWidth + (int) (sum * value);
    }

    /**
     * 限定左侧侧seek最长最短边距
     *
     * @param leftMark
     * @return
     */
    private int limitLeftMark(int leftMark) {
        int resetMark;
        if (leftMark < mInitLeftAndRightMark) {
            resetMark = mInitLeftAndRightMark;
        } else if (leftMark > mViewWidth - mRightMarkWidth - mButtonWidth - 2 * mInitLeftAndRightMark) {
            resetMark = mViewWidth - mRightMarkWidth - mButtonWidth - 2 * mInitLeftAndRightMark;
        } else {
            resetMark = leftMark;
        }
        return resetMark;
    }

    /**
     * 限定右侧seek最长最短边距
     *
     * @param rightMark
     * @return
     */
    private int limitRightMark(int rightMark) {
        int resetMark;
        if (mRightLimit != -1) {
            if (rightMark > mViewWidth - mRightLimit) {
                return mRightMarkWidth;
            }
        }
        if (rightMark > mViewWidth - mInitLeftAndRightMark) {
            resetMark = mInitLeftAndRightMark;
        } else if (rightMark < mLeftMarkWidth + mButtonWidth + 2 * mInitLeftAndRightMark) {
            resetMark = mViewWidth - (mLeftMarkWidth + mButtonWidth + 2 * mInitLeftAndRightMark);
        } else {
            resetMark = mViewWidth - rightMark;
        }
        return resetMark;
    }

    /**
     * 回调左右seek的x坐标，用于上层计算选定时长
     *
     * @param onMarkMoveListener
     */
    public void setOnMarkMoveListener(OnMarkMoveListener onMarkMoveListener) {
        mOnMarkMoveListener = onMarkMoveListener;
    }

    /**
     * 根据播放状态设置滑杆
     *
     * @param isPlay
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void changePlayMarkState(boolean isPlay) {
        if (isPlay) {
            mValueAnimator.resume();
            mIsShowPlayMark = true;
        } else {
            mValueAnimator.pause();
            mIsShowPlayMark = false;
        }
        startAnimatePlay();
        invalidate();
    }

    /**
     * 根据视频时间设置滑杆动画时长
     * 小视频每帧1秒，大视频每帧2秒
     *
     * @param duration
     */
    public void setPlayMarkDuration(float duration) {
//        mValueAnimator.cancel();
        if (mIsSmallVideo) {
            mAnimatorDuration = duration * 1000;
        } else {
            mAnimatorDuration = duration * 1000 * 2;
        }
    }

    /**
     * 初始化缩略帧
     *
     * @param frame
     * @param isSmallVideo 是否为5秒以下的小视频
     */
    public void initVideoFrame(int frame, boolean isSmallVideo) {
        mFrame = frame - 1;
        mIsSmallVideo = isSmallVideo;
    }

    public int dp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
