package com.example.myapplication.widget;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.myapplication.R;


/**
 * 加载
 * <p>
 * Created by xieH on 2015/12/5.
 */
public class LoadingDialog extends Dialog {

    private LinearLayout mDotsLl;
    private ImageView[] mDotsIvs;

    private AnimatorSet mAnimatorSetIn, mAnimatorSetOut;
    private int index = 0;

    public LoadingDialog(Context context) {
        super(context, R.style.WinDialog);
        setContentView(R.layout.dialog_loading);

        // 设置全屏
        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT; // 宽度持平
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);

//        // 屏蔽返回键
//        this.setCancelable(false);

        initView();
    }

    public void initView() {

        mDotsLl = (LinearLayout) findViewById(R.id.loading_dots_ll);

        mDotsIvs = new ImageView[mDotsLl.getChildCount()];

        for (int i = 0; i < mDotsLl.getChildCount(); i++) {
            mDotsIvs[i] = (ImageView) mDotsLl.getChildAt(i);
        }
    }

    public void startAnimator(View view) {

        mAnimatorSetIn = createAnimatorSet(view);
        mAnimatorSetIn.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                mAnimatorSetOut.setTarget(mDotsIvs[index % mDotsLl.getChildCount()]);
                mAnimatorSetOut.start();

                index++;
                startAnimator(mDotsIvs[index % mDotsLl.getChildCount()]);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

        mAnimatorSetIn.start();
    }

    public AnimatorSet createAnimatorSet(View view) {
        AnimatorSet mAnimatorSet = new AnimatorSet();

        ObjectAnimator alpha = ObjectAnimator.ofFloat(view, "alpha", 0.2f, 1.0f);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1.0f, 1.5f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1.0f, 1.5f);

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


    @Override
    public void show() {

        mAnimatorSetOut = createAnimatorSet(mDotsIvs[0]);
        mAnimatorSetOut.setInterpolator(new ReverseInterpolator());

        startAnimator(mDotsIvs[0]);

        super.show();
    }

    @Override
    public void dismiss() {

        if (mAnimatorSetIn != null && mAnimatorSetIn.isRunning()) {
            mAnimatorSetIn.cancel();
        }

        if (mAnimatorSetOut != null && mAnimatorSetOut.isRunning()) {
            mAnimatorSetOut.cancel();
        }

        super.dismiss();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            return false;
        }
        return super.onTouchEvent(event);
    }

}
