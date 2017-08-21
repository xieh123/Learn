package com.example.myapplication.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by xieH on 2017/1/7 0007.
 */
public class PathRectView extends View {

    private Context mContext;

    private Paint mPaint;

    private Path mSourcePath;
    private Path mDstPath;


    private float mCurrentValue = 0;

    public PathRectView(Context context) {
        this(context, null);
    }

    public PathRectView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PathRectView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.mContext = context;

        init();
    }

    public void init() {

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);


        mSourcePath = new Path(); // 创建Path并添加了一个矩形
        mSourcePath.addRect(100, 100, 300, 300, Path.Direction.CW);

        mDstPath = new Path();

        // 先重置一下显示动画的 Path
        mDstPath.reset();
        mDstPath.lineTo(0, 0);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        PathMeasure pathMeasure = new PathMeasure();  // 将 Path 与 PathMeasure 关联

        pathMeasure.setPath(mSourcePath, false);

        // 截取一部分存入dst中，并使用 moveTo 保持截取得到的 Path 第一个点的位置不变
        pathMeasure.getSegment(0, pathMeasure.getLength() * mCurrentValue, mDstPath, true);


        mPaint.setColor(Color.GREEN);
        canvas.drawPath(mSourcePath, mPaint);

        mPaint.setColor(Color.RED);
        canvas.drawPath(mDstPath, mPaint);

        mCurrentValue += 0.002f;

        if (mCurrentValue < 1f) {
            invalidate();
        }
    }

}
