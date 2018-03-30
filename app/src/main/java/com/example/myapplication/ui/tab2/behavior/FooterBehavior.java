package com.example.myapplication.ui.tab2.behavior;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.example.myapplication.R;

/**
 * Created by xieH on 2018/3/19 0019.
 */
public class FooterBehavior extends CoordinatorLayout.Behavior<View> {
    private ObjectAnimator mAnimator;
    private FooterBehaviorDelegate mDelegate;
    private int mDeltaY;
    private final int mHideThreshold;
    private boolean mIsShown = true;
    private final int mShowThreshold;
    private FooterVisibleDelegate mVisibleDelegate;

    public interface FooterBehaviorDelegate {
        boolean isFooterBehaviorEnable();
    }

    public interface FooterVisibleDelegate {
        void onFooterVisibilityChanged(boolean isVisible);
    }

    public FooterBehavior(Context pContext, AttributeSet pAttributeSet) {
        super(pContext, pAttributeSet);
        this.mShowThreshold = -pContext.getResources().getDimensionPixelSize(R.dimen.tab_show_scroll_threshold);
        this.mHideThreshold = pContext.getResources().getDimensionPixelSize(R.dimen.tab_hide_scroll_threshold);
    }

    public void setFooterBehaviorDelegate(FooterBehaviorDelegate pDelegate) {
        this.mDelegate = pDelegate;
    }

    public void setFooterVisibleDelegate(FooterVisibleDelegate delegate) {
        this.mVisibleDelegate = delegate;
    }

    public void reset(boolean isShown) {
        this.mIsShown = isShown;
        this.mDeltaY = 0;
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int axes) {
        if ((this.mDelegate == null || this.mDelegate.isFooterBehaviorEnable()) && (axes & 2) != 0) {
            return true;
        }
        return false;
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dx, int dy, int[] consumed) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);
        if (this.mDelegate == null || this.mDelegate.isFooterBehaviorEnable()) {
            this.mDeltaY += dy;
            if ((dy > 0 && this.mDeltaY < 0) || (dy < 0 && this.mDeltaY > 0)) {
                this.mDeltaY = dy;
            }
            if (this.mDeltaY < this.mShowThreshold || this.mDeltaY > this.mHideThreshold) {
                if ((this.mDeltaY < 0) != this.mIsShown) {
                    boolean isVisible;
                    if (this.mAnimator != null && this.mAnimator.isRunning()) {
                        this.mAnimator.cancel();
                    }
                    if (this.mDeltaY < 0) {
                        isVisible = true;
                    } else {
                        isVisible = false;
                    }
                    this.mIsShown = isVisible;
                    if (this.mVisibleDelegate != null) {
                        this.mVisibleDelegate.onFooterVisibilityChanged(this.mIsShown);
                    }
                    Property property = View.TRANSLATION_Y;
                    float[] fArr = new float[2];
                    fArr[0] = child.getTranslationY();
                    fArr[1] = this.mIsShown ? 0.0f : (float) child.getHeight();
                    this.mAnimator = ObjectAnimator.ofFloat(child, property, fArr);
                    this.mAnimator.setInterpolator(new DecelerateInterpolator());
                    this.mAnimator.start();
                    this.mDeltaY = 0;
                }
            }
        }
    }

    @Override
    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, View child, View target) {
        super.onStopNestedScroll(coordinatorLayout, child, target);
        this.mDeltaY = 0;
    }

    @Override
    public boolean onNestedFling(CoordinatorLayout coordinatorLayout, View child, View target, float velocityX, float velocityY, boolean consumed) {
        if (this.mDelegate == null || this.mDelegate.isFooterBehaviorEnable()) {
            if ((this.mDeltaY < 0) != this.mIsShown) {
                boolean isVisible;
                if (this.mAnimator != null && this.mAnimator.isRunning()) {
                    if (velocityY < 0.0f) {
                        isVisible = true;
                    } else {
                        isVisible = false;
                    }
                    if (isVisible != this.mIsShown) {
                        this.mAnimator.cancel();
                    }
                }
                if (this.mDeltaY < 0) {
                    isVisible = true;
                } else {
                    isVisible = false;
                }
                this.mIsShown = isVisible;
                if (this.mVisibleDelegate != null) {
                    this.mVisibleDelegate.onFooterVisibilityChanged(this.mIsShown);
                }
                Property property = View.TRANSLATION_Y;
                float[] fArr = new float[2];
                fArr[0] = child.getTranslationY();
                fArr[1] = this.mIsShown ? 0.0f : (float) child.getHeight();
                this.mAnimator = ObjectAnimator.ofFloat(child, property, fArr);
                this.mAnimator.setInterpolator(new DecelerateInterpolator());
                this.mAnimator.start();
                this.mDeltaY = 0;
            }
        }
        return false;
    }

}
