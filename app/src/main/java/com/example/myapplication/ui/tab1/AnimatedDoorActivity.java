package com.example.myapplication.ui.tab1;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Property;
import android.view.View;
import android.widget.FrameLayout;

import com.example.myapplication.widget.AnimatedDoorLayout;

/**
 * Created by xieH on 2017/3/8 0008.
 */
public abstract class AnimatedDoorActivity extends AppCompatActivity {

    private AnimatedDoorLayout mAnimatedDoorLayout;
    protected int mDoorType;

    protected abstract int getLayoutId();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        FrameLayout activityRoot = (FrameLayout) findViewById(android.R.id.content);
        View parent = activityRoot.getChildAt(0);

        // better way ?
        mAnimatedDoorLayout = new AnimatedDoorLayout(this);
        activityRoot.removeView(parent);
        activityRoot.addView(mAnimatedDoorLayout, parent.getLayoutParams());
        mAnimatedDoorLayout.addView(parent);

        mDoorType = getIntent().getIntExtra("door_type", AnimatedDoorLayout.HORIZONTAL_DOOR);
        mAnimatedDoorLayout.setDoorType(mDoorType);

        ObjectAnimator animator = ObjectAnimator.ofFloat(mAnimatedDoorLayout, ANIMATED_DOOR_LAYOUT_FLOAT_PROPERTY, 1);
        animator.setDuration(600);
        animator.start();
    }

    private static final Property<AnimatedDoorLayout, Float> ANIMATED_DOOR_LAYOUT_FLOAT_PROPERTY = new Property<AnimatedDoorLayout, Float>(Float.class, "ANIMATED_DOOR_LAYOUT_FLOAT_PROPERTY") {

        @Override
        public void set(AnimatedDoorLayout layout, Float value) {
            layout.setProgress(value);
        }

        @Override
        public Float get(AnimatedDoorLayout layout) {
            return layout.getProgress();
        }
    };

    @Override
    public void onBackPressed() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(mAnimatedDoorLayout, ANIMATED_DOOR_LAYOUT_FLOAT_PROPERTY, 0);
        animator.setDuration(600);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                finish();
            }
        });
        animator.start();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }
}
