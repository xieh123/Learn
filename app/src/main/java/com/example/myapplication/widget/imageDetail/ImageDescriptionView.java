package com.example.myapplication.widget.imagedetail;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.myapplication.R;

/**
 * Created by xieH on 2017/2/21 0021.
 */
public class ImageDescriptionView extends FrameLayout {

    private static final String TAG = ImageDescriptionView.class.getSimpleName();

    private Context mContext;

    private ScrollView mScrollView;
    private TextView mDescTv;

    /**
     * 滑动最小距离
     */
    private static final float FLING_MIN_DISTANCE = 0.5f;

    /**
     * View最大的高度
     */
    private int mViewMaxHeight = 0;

    /**
     * View最小的高度
     */
    private int mViewMinHeight = 0;

    private ViewGroup.LayoutParams mLayoutParams;

    /**
     * 是否改变
     */
    private boolean isChange;

    private int mDownY;

    private int widthMeasureSpec;
    private int heightMeasureSpec;

    public ImageDescriptionView(Context context) {
        this(context, null, 0);
    }

    public ImageDescriptionView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImageDescriptionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;

        init();
    }

    private void init() {
        inflate(mContext, R.layout.view_image_description, this);

        mScrollView = (ScrollView) findViewById(R.id.image_description_sv);
        mDescTv = (TextView) findViewById(R.id.image_description_desc_tv);

        mViewMaxHeight = getScreenHeight() / 3;

        mLayoutParams = mScrollView.getLayoutParams();

        getViewMinHeight();
    }

    /**
     * 计算 View的最小高度
     */
    public void getViewMinHeight() {
        int width = mDescTv.getMeasuredWidth();

        widthMeasureSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(1200, MeasureSpec.AT_MOST);

        TextView localTextView = new TextView(mContext);
        localTextView.setEllipsize(TextUtils.TruncateAt.END);
        localTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14.0f);
        localTextView.setText("设置\n足够\n长的\n字符\n串看\n看最\n大4行\n是多\n高。宽度要小，高度要大");

        localTextView.setMaxLines(1);
        localTextView.measure(widthMeasureSpec, heightMeasureSpec);

        mViewMinHeight = localTextView.getMeasuredHeight() * 4;  // 4行的高度
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        if (mLayoutParams.height >= mViewMaxHeight) {  // 达到最高的高度
            mScrollView.requestDisallowInterceptTouchEvent(false);  // ScrollView拦截事件

            if (mScrollView.getScrollY() > 0) {  // scrollview不在顶部时，需要重新获取原点坐标
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    isChange = true;
                }
                return super.dispatchTouchEvent(event); // 继续传递下去
            } else {
                if (isChange) {  // 原点已发生改变，需要重新获取坐标
                    mDownY = (int) event.getY();
                    isChange = false;
                }
            }
        } else {
            mScrollView.requestDisallowInterceptTouchEvent(true);  // ScrollView不拦截事件
        }

        int mCurrentY = (int) event.getY();
        switch (MotionEventCompat.getActionMasked(event)) {
            case MotionEvent.ACTION_DOWN:
                mDownY = mCurrentY;
                break;
            case MotionEvent.ACTION_MOVE:
                int dy = mCurrentY - mDownY;

                if (mCurrentY - mDownY < 0 && Math.abs(mCurrentY - mDownY) > FLING_MIN_DISTANCE) {  // 向上滑动
                    int height = mLayoutParams.height + Math.abs(dy);    // 控件的高度 + 滑动的距离

                    if (height >= mViewMaxHeight) {
                        adjustViewHeight(mViewMaxHeight);
                    } else {
                        adjustViewHeight(height);
                    }
                } else if (mCurrentY - mDownY > 0 && Math.abs(mCurrentY - mDownY) > FLING_MIN_DISTANCE) {  // 向下滑动
                    int height = mScrollView.getHeight() - dy;   // 控件的高度 - 滑动的距离

                    if (height <= mViewMinHeight) { //
                        adjustViewHeight(mViewMinHeight);
                    } else {
                        adjustViewHeight(height);
                    }

                    return true;  // 拦截事件
                }
                break;
        }

        return super.dispatchTouchEvent(event);
    }

    /**
     * 动态设置 View的高度
     *
     * @param viewHeight
     */
    private void adjustViewHeight(int viewHeight) {

        int height = mDescTv.getMeasuredHeight();
        height = Math.min(height, viewHeight);

        mLayoutParams.height = height;
        mScrollView.setLayoutParams(mLayoutParams);
    }

    /**
     * 设置文本
     *
     * @param text
     */
    public void setText(final String text) {

        post(new Runnable() {
            @Override
            public void run() {
                mDescTv.setText(text);

                adjustView();
            }
        });
    }

    /**
     * 调整 View的高度
     */
    protected void adjustView() {
        mDescTv.measure(0, 0);
        int height = mDescTv.getMeasuredHeight();

        mLayoutParams.height = height;

        if (height > mViewMinHeight + 2) {
            mLayoutParams.height = (mViewMinHeight + 2);

            mScrollView.setLayoutParams(mLayoutParams);
            mScrollView.fullScroll(ScrollView.FOCUS_UP);

            invalidate();
        }
    }

    /**
     * 获取屏幕高度
     *
     * @return
     */
    private int getScreenHeight() {
        return getResources().getDisplayMetrics().heightPixels;
    }

}

