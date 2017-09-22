package com.example.myapplication.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by xieH on 2017/8/2 0002.
 */
public class ChatImageView extends ImageView {

    private Context mContext;
    private Paint mPaint;
    private Bitmap mBackgroundBitmap;   // .9.png 背景图
    private Bitmap mForegroundBitmap;   // 前景图

    private Drawable mBackgroundDrawable;
    private Drawable mForegroundDrawable;

    // 控件默认长、宽
    private int defaultWidth = 0;
    private int defaultHeight = 0;

    public ChatImageView(Context context) {
        this(context, null);
    }

    public ChatImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChatImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;

        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setFilterBitmap(true);
        mPaint.setDither(true);
        this.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        if (mBackgroundDrawable == null) {
            mBackgroundDrawable = getBackground();
        }

        if (mForegroundDrawable == null) {
            mForegroundDrawable = getDrawable();
        }

        if (mBackgroundDrawable == null || mForegroundDrawable == null) {
            return;
        }
        if (getWidth() == 0 || getHeight() == 0) {
            return;
        }
        this.measure(0, 0);

        mBackgroundBitmap = drawableToBitmap(mBackgroundDrawable, getWidth(), getHeight()); // 获取背景图
        mForegroundBitmap = drawableToBitmap(mForegroundDrawable, getWidth(), getHeight()); // 获取前景图

        if (defaultWidth == 0) {
            defaultWidth = getWidth();
        }

        if (defaultHeight == 0) {
            defaultHeight = getHeight();
        }

        canvas.drawBitmap(mBackgroundBitmap, 0, 0, mPaint);  // 画背景图
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(mForegroundBitmap, 0, 0, mPaint);
        mPaint.setXfermode(null);

    }

    public Bitmap drawableToBitmap(Drawable drawable, int w, int h) {
        // 建立对应 bitmap
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);

        drawable.draw(canvas);
        return bitmap;
    }
}
