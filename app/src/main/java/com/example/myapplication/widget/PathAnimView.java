package com.example.myapplication.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by xieH on 2017/1/4 0004.
 */
public class PathAnimView extends View {

    private Context mContext;

    private static final int DURATION = 1500; // 默认动画时间

    /**
     * 源路径
     */
    private Path mSourcePath;

    /**
     * 动画路径
     */
    private Path mAnimPath;

    /**
     * 路径辅助类
     */
    private PathMeasure pathMeasure;

    private Paint mPaint;

    /**
     * 背景色
     */
    private int colorBg = Color.GRAY;

    /**
     * 前景色，填充色
     */
    private int colorFg = Color.WHITE;

    protected int mPaddingLeft, mPaddingTop;

    private ValueAnimator animator;

    /**
     * 无限循环，默认开启
     */
    private boolean isInfinite = true;

    public PathAnimView(Context context) {
        this(context, null);
    }

    public PathAnimView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PathAnimView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;

        init();
    }

    public void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true); // 设置抗边缘锯齿
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(3);  // 设置宽度

        mAnimPath = new Path();
    }

    /**
     * 设置动画路径
     *
     * @param sourcePath
     */
    public void setSourcePath(Path sourcePath) {
        this.mSourcePath = sourcePath;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mPaddingLeft = getPaddingLeft();
        mPaddingTop = getPaddingTop();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 平移
        canvas.translate(mPaddingLeft, mPaddingTop);

        // 先绘制底(背景)
        mPaint.setColor(colorBg);
        canvas.drawPath(mSourcePath, mPaint);

        // 再绘制前景，mAnimPath不断变化，不断重绘View的话，就有动画了。
        mPaint.setColor(colorFg);
        canvas.drawPath(mAnimPath, mPaint);
    }

    /**
     * 开始动画
     */
    public void startAnim() {
        startAnim(DURATION);
    }

    /**
     * 开始动画
     *
     * @param totalDuration 整个动画的时间
     */
    public void startAnim(int totalDuration) {

        if (mSourcePath == null)
            return;

        stopAnim();

        pathMeasure = new PathMeasure();

        // 先重置一下显示动画的 Path
        mAnimPath.reset();
        mAnimPath.lineTo(0, 0);

        pathMeasure.setPath(mSourcePath, false);

        // 这里仅仅为了计算一下每一段Path的 duration
        int count = 0;
        while (pathMeasure.getLength() != 0) {
            pathMeasure.nextContour();
            count++;
        }

        // 经过上面这段计算duration代码的折腾，需要重新初始化pathMeasure
        pathMeasure.setPath(mSourcePath, false);

        // 每段Path动画的时间
        int duration = totalDuration / count;

        animator = ValueAnimator.ofFloat(0.0f, 1.0f);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(duration);
        animator.setRepeatCount(ValueAnimator.INFINITE); // 无限循环

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

                // 获取Path的一个段落
                float value = (float) valueAnimator.getAnimatedValue();
                pathMeasure.getSegment(0, pathMeasure.getLength() * value, mAnimPath, true);

                invalidate(); // 刷新View
            }
        });

        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationRepeat(Animator animation) {
                super.onAnimationRepeat(animation);

                /**
                 * boolean getSegment (float startD, float stopD, Path dst, boolean startWithMoveTo)
                 * 这个API用于截取整个Path的片段，通过参数startD和stopD来控制截取的长度，并将截取的Path保存到dst中，
                 * 最后一个参数startWithMoveTo表示起始点是否使用moveTo方法，通常为True，保证每次截取的Path片段都是正常的、完整的。
                 *
                 * dst中保存的Path是被不断添加的，而不是每次被覆盖。
                 */
                pathMeasure.getSegment(0, pathMeasure.getLength(), mAnimPath, true);

                pathMeasure.nextContour();

                // 长度为0，说明一次循环结束
                if (pathMeasure.getLength() == 0) {
                    if (isInfinite) { // 如果需要循环动画
                        mAnimPath.reset();
                        mAnimPath.lineTo(0, 0);
                        pathMeasure.setPath(mSourcePath, false);
                    } else {
                        animator.cancel();
                    }
                }
            }
        });

        animator.start();
    }

    /**
     * 停止动画
     */
    public void stopAnim() {
        if (animator != null && animator.isRunning()) {
            animator.cancel();
        }
    }

    /**
     * 是否需要无限循环动画
     *
     * @param infinite
     */
    public void setInfinite(boolean infinite) {
        isInfinite = infinite;
    }
}
