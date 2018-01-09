package com.example.myapplication.ui.main;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.myapplication.R;

/**
 * Created by xieH on 2016/12/24 0024.
 */
public class LoadingActivity extends AppCompatActivity {

    LinearLayout loadingLl;

    ImageView[] mImageViews;

    AnimatorSet mAnimatorSetIn, mAnimatorSetOut;

    int index = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        initView();
    }

    public void initView() {

        loadingLl = (LinearLayout) findViewById(R.id.loading_ll);

        mImageViews = new ImageView[loadingLl.getChildCount()];

        for (int i = 0; i < loadingLl.getChildCount(); i++) {
            mImageViews[i] = (ImageView) loadingLl.getChildAt(i);
        }

        mAnimatorSetOut = createAnimatorSet(mImageViews[0]);
        mAnimatorSetOut.setInterpolator(new ReverseInterpolator());

        startAnimator(mImageViews[0]);
    }

    public void startAnimator(ImageView view) {

        mAnimatorSetIn = createAnimatorSet(view);
        mAnimatorSetIn.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {

                mAnimatorSetOut.setTarget(mImageViews[index % loadingLl.getChildCount()]);
                mAnimatorSetOut.start();

                index++;
                startAnimator(mImageViews[index % loadingLl.getChildCount()]);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });


        mAnimatorSetIn.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationRepeat(Animator animation) {
                super.onAnimationRepeat(animation);
            }
        });

        mAnimatorSetIn.start();
    }

    public AnimatorSet createAnimatorSet(final ImageView view) {
        AnimatorSet mAnimatorSet = new AnimatorSet();

        ObjectAnimator alpha = ObjectAnimator.ofFloat(view, "alpha", 0.2f, 1.0f);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1.0f, 1.8f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1.0f, 1.8f);

        mAnimatorSet.play(alpha).with(scaleX).with(scaleY);
        mAnimatorSet.setDuration(1000);

        return mAnimatorSet;
    }


    /**
     * 相反
     */
    private class ReverseInterpolator implements Interpolator {
        @Override
        public float getInterpolation(float value) {
            return Math.abs(1.0f - value);
        }
    }

    public int dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    @Override
    protected void onPause() {
        super.onPause();

        System.out.println("b--------onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();

        System.out.println("b--------onResume");
    }

    @Override
    protected void onStop() {
        super.onStop();

        System.out.println("b--------onStop");
    }


    @Override
    protected void onDestroy() {

        if (mAnimatorSetIn != null && mAnimatorSetIn.isRunning()){
            mAnimatorSetIn.cancel();
        }

        if (mAnimatorSetOut != null && mAnimatorSetOut.isRunning()){
            mAnimatorSetOut.cancel();
        }

        super.onDestroy();
    }
}
