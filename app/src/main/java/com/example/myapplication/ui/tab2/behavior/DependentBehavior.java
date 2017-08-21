package com.example.myapplication.ui.tab2.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

/**
 * Created by xieH on 2017/6/20 0020.
 */
public class DependentBehavior extends CoordinatorLayout.Behavior<View> {

    public DependentBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    /**
     * @param parent
     * @param child      设置这个Behavior的View
     * @param dependency 关心的那个View
     * @return
     */
    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency instanceof TextView; // 返回需要关注的View
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        int offset = dependency.getTop() - child.getTop();
        ViewCompat.offsetTopAndBottom(child, offset);
        return true;
    }
}
