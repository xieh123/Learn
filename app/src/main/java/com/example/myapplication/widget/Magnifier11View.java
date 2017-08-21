package com.example.myapplication.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.myapplication.R;

/**
 * Created by xieH on 2017/6/22 0022.
 */
public class Magnifier11View extends View {

    private Path mPath;
    private Matrix matrix;
    private Bitmap bitmap;

    // 放大镜的半径
    private int RADIUS = 80;

    // 放大倍数
    private int FACTOR = 2;
    private int mCurrentX, mCurrentY;

    public Magnifier11View(Context context) {
        this(context, null);
    }

    public Magnifier11View(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Magnifier11View(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPath = new Path();
        matrix = new Matrix();

        mPath.addCircle(RADIUS, RADIUS, RADIUS, Path.Direction.CW);
        matrix.setScale(FACTOR, FACTOR);

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.welcome_thirdly);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mCurrentX = (int) event.getX();
        mCurrentY = (int) event.getY();

        invalidate();
        return true;
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 底图
        canvas.drawBitmap(bitmap, 0, 0, null);

        // 剪切
        canvas.translate(mCurrentX - RADIUS, mCurrentY - RADIUS);
        canvas.clipPath(mPath);

        // 画放大后的图
        canvas.translate(RADIUS - mCurrentX * FACTOR, RADIUS - mCurrentY * FACTOR);

        canvas.drawBitmap(bitmap, matrix, null);
    }
}
