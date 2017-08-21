package com.example.myapplication.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.example.myapplication.R;

/**
 * Created by xieH on 2017/5/17 0017.
 */
public class ChangeBitmapColor extends View {

    private Paint mPaint;
    private Bitmap mBmp;

    public ChangeBitmapColor(Context context, AttributeSet attrs) {
        super(context, attrs);

        mPaint = new Paint();
        mBmp = BitmapFactory.decodeResource(getResources(), R.drawable.welcome_thirdly);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = 500;
        int height = width * mBmp.getHeight() / mBmp.getWidth();
        mPaint.setColor(Color.RED);

        int layerID = canvas.saveLayer(0, 0, width, height, mPaint, Canvas.ALL_SAVE_FLAG);

        canvas.drawBitmap(mBmp, null, new Rect(0, 0, width, height), mPaint);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        canvas.drawRect(0, 0, width, height, mPaint);

        canvas.restoreToCount(layerID);
    }
}
