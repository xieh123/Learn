package com.example.myapplication.widget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.example.myapplication.R;

/**
 * Created by xieH on 2017/7/4 0004.
 */
public class InstrumentView extends View {

    private Paint mPaintBig, mPaintSmall, mPaintYellow, mPaintGreen, mPaintRed;
    private int mWidth;
    private int mHeight;
    private RectF mBigRectF, mSmallRectF, mTextRectF;
    private TextPaint mTextPaint;
    private int[] offset = {-198, -125, -52, 18};//分别是第一段起点，第二段起点，第三段起点，第三段终点
    private Bitmap mGreenBitmap;
    private Bitmap mYellowBitmap;
    private Bitmap mRedBitmap;

    private int progress = 0;
    private int mFullEat;
    private Matrix mMatrix;

    public InstrumentView(Context context) {
        this(context, null);
    }

    public InstrumentView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public InstrumentView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaintBig = new Paint();//外环画笔
        mPaintBig.setAntiAlias(true);//使用抗锯齿功能
        mPaintBig.setColor(Color.argb(128, 0, 0, 0));    //设置画笔的颜色
        mPaintBig.setStyle(Paint.Style.STROKE);//设置画笔类型为描边
        mPaintBig.setStrokeWidth(16);

        mPaintYellow = new Paint();//黄色
        mPaintYellow.setAntiAlias(true);//使用抗锯齿功能
        mPaintYellow.setColor(Color.rgb(0xf7, 0xb5, 0x00));    //设置画笔的颜色
        mPaintYellow.setStyle(Paint.Style.STROKE);//设置画笔类型为描边
        mPaintYellow.setStrokeWidth(16);

        mPaintGreen = new Paint();//绿色
        mPaintGreen.setAntiAlias(true);//使用抗锯齿功能
        mPaintGreen.setColor(Color.rgb(0x5c, 0xd1, 0xb4));    //设置画笔的颜色
        mPaintGreen.setStyle(Paint.Style.STROKE);//设置画笔类型为描边
        mPaintGreen.setStrokeWidth(16);

        mPaintRed = new Paint();//红色
        mPaintRed.setAntiAlias(true);//使用抗锯齿功能
        mPaintRed.setColor(Color.rgb(0xff, 0x64, 0x6f));    //设置画笔的颜色
        mPaintRed.setStyle(Paint.Style.STROKE);//设置画笔类型为描边
        mPaintRed.setStrokeWidth(16);

        mPaintSmall = new Paint();//内环画笔
        mPaintSmall.setAntiAlias(true);//使用抗锯齿功能
        mPaintSmall.setColor(Color.argb(128, 0, 0, 0));    //设置画笔的颜色
        mPaintSmall.setStyle(Paint.Style.STROKE);//设置画笔类型为描边
        mPaintSmall.setStrokeWidth(8);

        mTextPaint = new TextPaint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setTextSize(28);

        mGreenBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_pointer_green);//绿色指针
        mYellowBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_pointer_black);//黄色指针
        mRedBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_pointer_red);//红色指针

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);
        int i = (mWidth - mHeight) / 2;
        mBigRectF = new RectF(10 + i, 10, mWidth - 10 - i, mHeight - 10);
        mTextRectF = new RectF(40 + i, 40, mWidth - 40 - i, mHeight - 40);
        mSmallRectF = new RectF(70 + i, 70, mWidth - 70 - i, mHeight - 70);
        mBigRectF.offset(0, 80);
        mSmallRectF.offset(0, 80);
        mTextRectF.offset(0, 80);
        mMatrix = new Matrix();
        mMatrix.postTranslate(mWidth / 2 - mGreenBitmap.getWidth() / 2, mHeight / 4);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e("__-progress", progress + "");
        drawInstrument(canvas);//进度底色
        drawText(canvas);//文字
        drawPointer(canvas);//指针
        drawSecond(canvas);//第二层
        drawCalorie(canvas);//绘制中间的卡路里
    }

    /*
    *
    * 圆弧中间文字
    * */
    private void drawCalorie(Canvas canvas) {
        mTextPaint.setTextSize(56);
        String num = String.valueOf((int) (mFullEat * progress / 1000f));

        int numberWidth = (int) mTextPaint.measureText(num);
        mTextPaint.setTextSize(28);
        int textWidth = (int) mTextPaint.measureText("千卡");

        mTextPaint.setTextSize(56);
        canvas.drawText(num, (mWidth - numberWidth - textWidth) / 2, mHeight / 2 + 80 - 20, mTextPaint);
        mTextPaint.setTextSize(28);
        canvas.drawText("千卡", (mWidth - numberWidth - textWidth) / 2 + numberWidth + 5, mHeight / 2 + 80 - 20, mTextPaint);

        String state = progress > 333 ? (progress > 666 ? "吃多了" : "正合适") : "还需吃";
        mTextPaint.setTextSize(32);
        canvas.drawText(state, (mWidth - mTextPaint.measureText(state)) / 2, mHeight / 2 + 80 + 30, mTextPaint);
    }

    /*
    *
    *动态进度条
    **/
    private void drawSecond(Canvas canvas) {
        //不能修改 mPaint 画笔的颜色复用 mPaint，初步估计 canvas.drawArc 方法为异步
        if (progress > 666) {
            //红色
            canvas.drawArc(mBigRectF, offset[2], 70 * (progress - 666) * 3 / 1000, false, mPaintRed);
        } else if (progress > 333) {
            //黄色
            canvas.drawArc(mBigRectF, offset[1], 70 * (progress - 333) * 3 / 1000, false, mPaintYellow);
        } else {
            //绿色
            canvas.drawArc(mBigRectF, offset[0], 70 * progress * 3 / 1000, false, mPaintGreen);
        }
    }

    /*
    * 绘制指针
    * */
    private void drawPointer(Canvas canvas) {
        mMatrix.reset();
        mMatrix.postTranslate(mWidth / 2 - mGreenBitmap.getWidth() / 2, mHeight / 4);
        if (progress > 666) {
            //红色
            mMatrix.postRotate(38, mWidth / 2f, mHeight / 2f + 80);
            mMatrix.postRotate(70 * (progress - 666) / 333f, mWidth / 2f, mHeight / 2f + 80);
            canvas.drawBitmap(mRedBitmap, mMatrix, null);
        } else if (progress > 333) {
            //黄色
            mMatrix.postRotate(-35, mWidth / 2f, mHeight / 2f + 80);
            mMatrix.postRotate(70 * (progress - 333) / 333f, mWidth / 2f, mHeight / 2f + 80);
            canvas.drawBitmap(mYellowBitmap, mMatrix, null);
        } else {
            //绿色
            mMatrix.postRotate(-108, mWidth / 2f, mHeight / 2f + 80);
            mMatrix.postRotate(70 * progress / 333f, mWidth / 2f, mHeight / 2f + 80);
            canvas.drawBitmap(mGreenBitmap, mMatrix, null);
        }
    }

    /**
     * 绘制圆弧标记 文字
     */
    private void drawText(Canvas canvas) {
        mTextPaint.setTextSize(28);
        Path path = new Path();
        path.addArc(mTextRectF, offset[0], 20);
        canvas.drawTextOnPath("0", path, 10f, 10f, mTextPaint);
        path.reset();
        path.addArc(mTextRectF, offset[1] - 15, 20);
        canvas.drawTextOnPath(String.valueOf(mFullEat / 3), path, 10f, 10f, mTextPaint);
        path.reset();
        path.addArc(mTextRectF, offset[2] - 15, 20);
        canvas.drawTextOnPath(String.valueOf(mFullEat / 3 * 2), path, 10f, 10f, mTextPaint);
        path.reset();
        path.addArc(mTextRectF, offset[3] - 20, 20);
        canvas.drawTextOnPath(String.valueOf(mFullEat), path, 10f, 10f, mTextPaint);
    }

    /*
    *
    * 绘制6段圆弧
    * */
    private void drawInstrument(Canvas canvas) {
        //70度一个弧形，中间隔5度
        canvas.drawArc(mBigRectF, offset[0], 70, false, mPaintBig);//绘制圆弧，不含圆心
        canvas.drawArc(mBigRectF, offset[1], 70, false, mPaintBig);//绘制圆弧，不含圆心
        canvas.drawArc(mBigRectF, offset[2], 70, false, mPaintBig);//绘制圆弧，不含圆心
        canvas.drawArc(mSmallRectF, offset[0], 70, false, mPaintSmall);//绘制圆弧，不含圆心
        canvas.drawArc(mSmallRectF, offset[1], 70, false, mPaintSmall);//绘制圆弧，不含圆心
        canvas.drawArc(mSmallRectF, offset[2], 70, false, mPaintSmall);//绘制圆弧，不含圆心
    }

    public void setData(int hasEat, int fullEat, boolean needAnimator) {
        progress = hasEat * 1000 / fullEat;
        mFullEat = fullEat;
        if (progress > 1000)
            progress = 1000;
        if (progress < 0)
            progress = 0;
        if (needAnimator) {
            ObjectAnimator objectAnimator = ObjectAnimator.ofInt(this, "progress", progress);
            objectAnimator.setDuration(2000);
            objectAnimator.setInterpolator(new DecelerateInterpolator());
            objectAnimator.start();
        } else {
            setProgress(progress);
        }
    }

    private void setProgress(int progress) {
        this.progress = progress;
        invalidate();
    }
}
