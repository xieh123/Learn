package com.example.myapplication.ui.main;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;

import com.example.myapplication.R;

/**
 * Created by Administrator on 2016/11/23 0023.
 */
public class UserInfoActivity extends AppCompatActivity {

    private FrameLayout bgFl, bgFl11;

    private int[] res = new int[]{R.drawable.bg_record, R.drawable.bg_user, R.drawable.bg_11};
    private int index = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        initView();
    }

    public void initView() {

        bgFl = (FrameLayout) findViewById(R.id.bg_fl);
        bgFl11 = (FrameLayout) findViewById(R.id.bg_fl11);

        alphaAnimator(bgFl);
        alphaNextAnimator(bgFl11);
    }

    public void alphaAnimator(final View view) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "alpha", 1f, 0f);
        objectAnimator.setDuration(3000);
        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                view.setBackgroundResource(res[index % res.length]);

                index++;
                if (index > res.length - 1) {
                    index = 0;
                }

                alphaNextAnimator(view);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

        objectAnimator.start();

    }

    public void alphaNextAnimator(final View view) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f);
        objectAnimator.setDuration(3000);

        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                alphaAnimator(view);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

        objectAnimator.start();

    }
}
