package com.example.myapplication.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * Created by xieH on 2018/1/11 0011.
 */
public class TextButton extends AppCompatTextView {

    private Paint mPaint;

    public TextButton(Context context) {
        this(context, null);
    }

    public TextButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(50.0f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        String test = "hhhhhhhhhhhhhhhhhhh";

        canvas.drawText(test, 0, getMeasuredHeight(), mPaint);
    }
}
