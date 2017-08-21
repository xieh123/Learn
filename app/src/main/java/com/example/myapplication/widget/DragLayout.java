package com.example.myapplication.widget;

import android.content.Context;
import android.graphics.Point;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by xieH on 2017/3/8 0008.
 */
public class DragLayout extends FrameLayout {
    private ViewDragHelper mDragHelper;

    private View mDragView, mDragView11;
    private View mAutoView;

    private Point mAutoOriginPos = new Point();

    public DragLayout(Context context) {
        this(context, null);
    }

    public DragLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mDragView = getChildAt(0);
        mDragView11 = getChildAt(1);
        mAutoView = getChildAt(2);
    }

    public void init() {

        // 第一个参数为this，表示该类生成的对象; 1.0f是敏感度参数参数越大越敏感
        mDragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {

            /**
             * 通过DragHelperCallback的tryCaptureView方法的返回值可以决定一个parentView中哪个子view可以拖动
             *
             */
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return child == mDragView || child == mDragView11 || child == mAutoView;
            }

            /**
             * 在DragHelperCallback中实现clampViewPositionHorizontal方法， 并且返回一个适当的数值就能实现横向拖动效果，
             * clampViewPositionHorizontal的第二个参数是指当前拖动子view应该到达的x坐标。所以按照常理这个方法原封返回第二个参数就可以了，
             * 但为了让被拖动的view遇到边界之后就不在拖动，对返回的值做了更多的考虑
             */
            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {

                final int leftBound = getPaddingLeft();
                final int rightBound = getWidth() - child.getWidth();
                final int newLeft = Math.min(Math.max(left, leftBound), rightBound);

                return newLeft;
            }

            /**
             * 同上，处理纵向的拖动
             */
            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {

                final int topBound = getPaddingTop();
                final int bottomBound = getHeight() - child.getHeight();
                final int newTop = Math.min(Math.max(top, topBound), bottomBound);
                return newTop;
            }

            /**
             * 手指释放的时候回调
             */
            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                // mAutoView 手指释放时可以自动回去
                if (releasedChild == mAutoView) {
                    mDragHelper.settleCapturedViewAt(mAutoOriginPos.x, mAutoOriginPos.y);
                    invalidate();
                }
            }


        });

        // 设置所有边缘触发
//        mDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_ALL);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        mAutoOriginPos.x = mAutoView.getLeft();
        mAutoOriginPos.y = mAutoView.getTop();
    }


    /**
     * 要让ViewDragHelper能够处理拖动需要将触摸事件传递给ViewDragHelper
     *
     * @param event
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        final int action = MotionEventCompat.getActionMasked(event);
        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            mDragHelper.cancel();
            return false;
        }
        return mDragHelper.shouldInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
        if (mDragHelper.continueSettling(true)) {
            postInvalidate();
        }
    }
}
