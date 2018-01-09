package com.example.myapplication.ui.main;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.R;

/**
 * Created by xieH on 2016/11/25 0025.
 */
public class AnimatorActivity extends AppCompatActivity {

    private LinearLayout backgroundLl, background11Ll, background22Ll;

    private TextView mTextView;

    private Button mPlayBtn;

    private ImageView mImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animator);

        initView();
    }

    public void initView() {
        backgroundLl = (LinearLayout) findViewById(R.id.animator_ll);
        background11Ll = (LinearLayout) findViewById(R.id.animator_11_ll);
        background22Ll = (LinearLayout) findViewById(R.id.animator_22_ll);

        mTextView = (TextView) findViewById(R.id.animator_tv);
        mPlayBtn = (Button) findViewById(R.id.animator_play_bt);
        mImageView = (ImageView) findViewById(R.id.animator_iv);

        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mImageView.animate()
                        .translationX(300)
                        .setDuration(2000);
            }
        });

    }

    /**
     * 属性动画
     */
    public void test() {
        mTextView.animate()
                .translationX(500);

        mTextView.animate()
                .translationX(500)
                .setDuration(2000);

        mTextView.animate()
                .alpha(1)
                .scaleX(1)
                .scaleY(1)
                .translationX(500)
                .setInterpolator(new LinearInterpolator())
                .setUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {

                    }
                });
    }

    /**
     * 背景颜色不断渐变
     *
     * @param v
     */
    public void add(View v) {
        ObjectAnimator translationUp = ObjectAnimator.ofInt(backgroundLl, "backgroundColor",
                Color.RED, Color.BLUE, Color.GRAY, Color.GREEN);

        /**
         * AccelerateDecelerateInterpolator: 在动画开始的地方速率改变比较慢，在中间的时侯加速
         * AccelerateInterpolator: 在动画开始的地方速率改变比较慢，然后开始加速
         * CycleInterpolator: 动画循环播放特定的次数，速率改变沿着正弦曲线
         * DecelerateInterpolator: 在动画开始的地方速率改变比较慢，然后开始减速
         * LinearInterpolator: 在动画的以均匀的速率改变
         */
        translationUp.setInterpolator(new LinearInterpolator());
        translationUp.setDuration(10000);
        translationUp.setRepeatCount(-1);
        translationUp.setRepeatMode(Animation.REVERSE);

        /*
         * ArgbEvaluator：这种评估者可以用来执行类型之间的插值整数值代表ARGB颜色。
         * FloatEvaluator：这种评估者可以用来执行浮点值之间的插值。
         * IntEvaluator：这种评估者可以用来执行类型int值之间的插值。
         * RectEvaluator：这种评估者可以用来执行类型之间的插值矩形值。
         *
         * 由于本例是改变View的backgroundColor属性的背景颜色所以此处使用ArgbEvaluator
         */
        translationUp.setEvaluator(new ArgbEvaluator());
        translationUp.start();
    }


    /**
     * 根据百分比来显示不同的颜色
     *
     * @param v
     */
    public void add11(View v) {
        //定义了下控制颜色变化的长度，这设了100，好展示。
        int decimalPlaces = 100;

        int colors[] = new int[]{Color.parseColor("#00aaee"), Color.parseColor("#00eeaa")};

        //颜色估值器
        ArgbEvaluator evaluator = new ArgbEvaluator();

        //得到 当前所占百分比的渐变值
        float result = (float) 10 / decimalPlaces;

        // ArgbEvaluator.evaluate(float fraction, Object startValue, Object endValue); // 根据一个起始颜色值和一个结束颜色值以及一个偏移量生成一个新的颜色,可实现颜色渐变效果

        //得到背景渐变色
        int evaluate = (int) evaluator.evaluate(result, colors[0], colors[1]);

        background11Ll.setBackgroundColor(evaluate);


//        ValueAnimator animator = ValueAnimator.ofInt(0xffff0000, 0xff0000ff);
//        animator.setEvaluator(new ArgbEvaluator());
//        animator.setDuration(10000);
//        animator.setRepeatCount(-1);
//
//        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                int curValue = (int) animation.getAnimatedValue();
//                background11Ll.setBackgroundColor(curValue);
//            }
//        });
//
//        animator.start();

    }

    public void add22(View v) {


    }

    public void add02(View v) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0f, 126512.36f);
        valueAnimator.setDuration(2000);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float money = (float) animation.getAnimatedValue();
                mTextView.setText(String.format("%.2f", money));
            }
        });
        valueAnimator.start();
    }

    public void play(View v) {
        ValueAnimator mAnimator = ValueAnimator.ofInt(100, 500);
        mAnimator.setInterpolator(new LinearInterpolator());
        mAnimator.setDuration(10000);
        //  mAnimator.setRepeatCount(ValueAnimator.INFINITE);

        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int value = (int) valueAnimator.getAnimatedValue();
                mPlayBtn.getLayoutParams().width = value;
                mPlayBtn.requestLayout();
            }
        });

        mAnimator.start();
    }

    // ValueAnimator.reverse() // 顺畅的取消动画效果


    public void translate(View v) {
        /**
         * 参数1: 操纵的控件
         * 参数2: 操纵的属性, 常见的有偏移translationX、translationY, 绝对值x、y, 3D旋转rotation、
         *     水平\竖直方向旋转rotationX\rotationY, 水平\竖直方向缩放scaleX\scaleX，透明度alpha
         * 参数3,4: 变化范围
         * setDuration: 设置显示时长
         */
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mImageView, "translationX", 0f, 200f);
        objectAnimator.setDuration(3000);
        objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

            }
        });
        objectAnimator.start();
    }


    //    /**
//     * 背景颜色不断渐变
//     *
//     * @param view
//     */
//    public void gradient(View view) {
//        ObjectAnimator gradientAnimator = ObjectAnimator.ofInt(view, "backgroundColor",
//                ContextCompat.getColor(getContext(), R.color.user_top_color_0),
//                ContextCompat.getColor(getContext(), R.color.user_top_color_2),
//                ContextCompat.getColor(getContext(), R.color.user_top_color_4),
//                ContextCompat.getColor(getContext(), R.color.user_top_color_6),
//                ContextCompat.getColor(getContext(), R.color.user_top_color_8),
//                ContextCompat.getColor(getContext(), R.color.user_top_color_10));
//
//        gradientAnimator.setInterpolator(new LinearInterpolator());
//        gradientAnimator.setDuration(18000);
//        gradientAnimator.setRepeatCount(-1);
//        gradientAnimator.setRepeatMode(Animation.REVERSE);
//
//        gradientAnimator.setEvaluator(new ArgbEvaluator());
//        gradientAnimator.start();
//    }
}
