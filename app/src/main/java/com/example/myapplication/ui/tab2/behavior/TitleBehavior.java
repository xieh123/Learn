package com.example.myapplication.ui.tab2.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by xieH on 2018/3/19 0019.
 */
public class TitleBehavior extends CoordinatorLayout.Behavior<View> {

    protected TitleBehaviorAnim mTitleBehaviorAnim;

    private boolean isHide;
    private boolean canScroll = true;
    private int mTotalScrollY;

    /**
     * 防止new Anim导致的parent 和child坐标变化
     */
    protected boolean isInit = false;

    /**
     * 触发滑动动画最小距离
     */
    private int minScrollY = 5;

    /**
     * 设置最小滑动距离
     */
    private int scrollYDistance = 40;

    public TitleBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return super.layoutDependsOn(parent, child, dependency);
    }

    /**
     * 判断垂直滑动
     *
     * @param coordinatorLayout
     * @param child
     * @param directTargetChild
     * @param target
     * @param nestedScrollAxes
     * @return
     */
    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child,
                                       View directTargetChild, View target, int nestedScrollAxes) {
        if (!isInit) {
            mTitleBehaviorAnim = new TitleBehaviorAnim(child);
            isInit = true;
        }
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    /**
     * 滑动嵌套滚动时触发的方法
     *
     * @param coordinatorLayout coordinatorLayout父布局
     * @param child             使用Behavior的子View
     * @param target            触发滑动嵌套的View
     * @param dxConsumed        TargetView消费的X轴距离
     * @param dyConsumed        TargetView消费的Y轴距离
     * @param dxUnconsumed      未被TargetView消费的X轴距离
     * @param dyUnconsumed      未被TargetView消费的Y轴距离(如RecyclerView已经到达顶部或底部，而用户继续滑动，此时dyUnconsumed的值不为0，可处理一些越界事件)
     */
    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, View child, View target,
                               int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
        if (canScroll) {
            mTotalScrollY += dyConsumed;
            if (Math.abs(dyConsumed) > minScrollY || Math.abs(mTotalScrollY) > scrollYDistance) {
                if (dyConsumed < 0) {
                    if (isHide) {
                        mTitleBehaviorAnim.show();
                        isHide = false;
                    }
                } else if (dyConsumed > 0) {
                    if (!isHide) {
                        mTitleBehaviorAnim.hide();
                        isHide = true;
                    }
                }
                mTotalScrollY = 0;
            }
        }
    }

    @Override
    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, View child, final View target) {
        super.onStopNestedScroll(coordinatorLayout, child, target);
    }
}
