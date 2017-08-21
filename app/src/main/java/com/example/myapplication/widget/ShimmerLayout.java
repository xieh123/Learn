package com.example.myapplication.widget;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.os.Build;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

import com.example.myapplication.R;

/**
 * Created by xieH on 2017/8/1 0001.
 */
public class ShimmerLayout extends FrameLayout {

    private Paint maskPaint;

    private ValueAnimator maskAnimator;

    private Bitmap localAvailableBitmap;
    private Bitmap localMaskBitmap;

    private Bitmap destinationBitmap;
    private Bitmap sourceMaskBitmap;
    private Canvas canvasForRendering;

    private int maskOffsetX;

    private boolean isAnimationStarted;

    private boolean autoStart;

    private int shimmerAnimationDuration;

    private int shimmerColor;

    public ShimmerLayout(Context context) {
        this(context, null);
    }

    public ShimmerLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShimmerLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        setWillNotDraw(false);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ShimmerLayout, 0, 0);
        try {
            shimmerAnimationDuration = a.getInteger(R.styleable.ShimmerLayout_shimmer_animation_duration, 1500);
            shimmerColor = a.getColor(R.styleable.ShimmerLayout_shimmer_color, getColor(R.color.shimmer_color));
            autoStart = a.getBoolean(R.styleable.ShimmerLayout_shimmer_auto_start, false);
        } finally {
            a.recycle();
        }

        init();
    }

    private void init() {
        maskPaint = new Paint();
        maskPaint.setAntiAlias(true);
        maskPaint.setDither(true);
        maskPaint.setFilterBitmap(true);
        maskPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        if (autoStart && getVisibility() == VISIBLE) {
            startShimmerAnimation();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        resetShimmering();
        super.onDetachedFromWindow();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        if (!isAnimationStarted || getWidth() <= 0 || getHeight() <= 0) {
            super.dispatchDraw(canvas);
        } else {
            dispatchDrawUsingBitmap(canvas);
        }
    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        if (visibility == VISIBLE) {
            if (autoStart) {
                startShimmerAnimation();
            }
        } else {
            stopShimmerAnimation();
        }
    }

    public void startShimmerAnimation() {
        if (isAnimationStarted) {
            return;
        }

        if (getWidth() == 0) {
            getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    removeGlobalLayoutListener(this);
                    startShimmerAnimation();
                }
            });

            return;
        }

        Animator animator = getShimmerAnimation();
        animator.start();
        isAnimationStarted = true;
    }

    public void stopShimmerAnimation() {
        resetShimmering();
    }

    public void setShimmerColor(int shimmerColor) {
        this.shimmerColor = shimmerColor;
        resetIfStarted();
    }

    public void setShimmerAnimationDuration(int durationMillis) {
        this.shimmerAnimationDuration = durationMillis;
        resetIfStarted();
    }

    private void resetIfStarted() {
        if (isAnimationStarted) {
            resetShimmering();
            startShimmerAnimation();
        }
    }

    private void dispatchDrawUsingBitmap(Canvas canvas) {
        super.dispatchDraw(canvas);

        localAvailableBitmap = getDestinationBitmap();
        if (localAvailableBitmap == null) {
            return;
        }

        if (canvasForRendering == null) {
            canvasForRendering = new Canvas(localAvailableBitmap);
        }

        drawMask(canvasForRendering);
        canvas.save();
        canvas.clipRect(maskOffsetX, 0, maskOffsetX + getWidth() / 2, getHeight());
        canvas.drawBitmap(localAvailableBitmap, 0, 0, null);
        canvas.restore();

        localAvailableBitmap = null;
    }

    private void drawMask(Canvas renderCanvas) {
        localMaskBitmap = getSourceMaskBitmap();
        if (localMaskBitmap == null) {
            return;
        }

        renderCanvas.save();
        renderCanvas.clipRect(maskOffsetX, 0,
                maskOffsetX + localMaskBitmap.getWidth(),
                getHeight());

        super.dispatchDraw(renderCanvas);
        renderCanvas.drawBitmap(localMaskBitmap, maskOffsetX, 0, maskPaint);

        renderCanvas.restore();

        localMaskBitmap = null;
    }

    private void resetShimmering() {
        if (maskAnimator != null) {
            maskAnimator.end();
            maskAnimator.removeAllUpdateListeners();
        }

        maskAnimator = null;
        isAnimationStarted = false;

        releaseBitMaps();
    }

    private void releaseBitMaps() {
        if (sourceMaskBitmap != null) {
            sourceMaskBitmap.recycle();
            sourceMaskBitmap = null;
        }

        if (destinationBitmap != null) {
            destinationBitmap.recycle();
            destinationBitmap = null;
        }

        canvasForRendering = null;
    }

    private Bitmap getDestinationBitmap() {
        if (destinationBitmap == null) {
            destinationBitmap = createBitmap(getWidth(), getHeight());
        }

        return destinationBitmap;
    }

    private Bitmap getSourceMaskBitmap() {
        if (sourceMaskBitmap != null) {
            return sourceMaskBitmap;
        }

        int width = getWidth() / 2;
        int height = getHeight();

        sourceMaskBitmap = createBitmap(width, height);
        Canvas canvas = new Canvas(sourceMaskBitmap);

        final int edgeColor = reduceColorAlphaValueToZero(shimmerColor);
        LinearGradient gradient = new LinearGradient(
                0, 0,
                width, 0,
                new int[]{edgeColor, shimmerColor, shimmerColor, edgeColor},
                new float[]{0.25F, 0.47F, 0.53F, 0.75F},
                Shader.TileMode.CLAMP);

        canvas.rotate(20, width / 2, height / 2);

        Paint paint = new Paint();
        paint.setShader(gradient);
        int padding = (int) (Math.sqrt(2) * Math.max(width, height)) / 2;
        canvas.drawRect(0, -padding, width, height + padding, paint);

        return sourceMaskBitmap;
    }

    private Animator getShimmerAnimation() {
        if (maskAnimator != null) {
            return maskAnimator;
        }

        final int animationToX = getWidth();
        final int animationFromX = -animationToX;
        final int shimmerBitmapWidth = getWidth() / 2;
        final int shimmerAnimationFullLength = animationToX - animationFromX;

        maskAnimator = ValueAnimator.ofFloat(0.0F, 1.0F);
        maskAnimator.setDuration(shimmerAnimationDuration);
        maskAnimator.setRepeatCount(ObjectAnimator.INFINITE);

        final float[] value = new float[1];
        maskAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                value[0] = (Float) animation.getAnimatedValue();
                maskOffsetX = ((int) (animationFromX + shimmerAnimationFullLength * value[0]));

                if (maskOffsetX + shimmerBitmapWidth >= 0) {
                    invalidate();
                }
            }
        });

        return maskAnimator;
    }

    private Bitmap createBitmap(int width, int height) {
        try {
            return Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError e) {
            System.gc();
            return Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        }
    }

    private int getColor(int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return getContext().getColor(id);
        } else {
            //noinspection deprecation
            return getResources().getColor(id);
        }
    }

    private void removeGlobalLayoutListener(ViewTreeObserver.OnGlobalLayoutListener listener) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            getViewTreeObserver().removeOnGlobalLayoutListener(listener);
        } else {
            getViewTreeObserver().removeGlobalOnLayoutListener(listener);
        }
    }

    private int reduceColorAlphaValueToZero(int actualColor) {
        return Color.argb(0, Color.red(actualColor), Color.green(actualColor), Color.blue(actualColor));
    }
}
