package com.example.myapplication.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.myapplication.R;

/**
 * Created by xieH on 2017/6/22 0022.
 */
public class MagnifierView extends View {

    private Bitmap mBitmap;

    private ShapeDrawable mShapeDrawable;

    /**
     * 放大镜的半径
     */
    private int radius = 80;

    /**
     * 放大倍数
     */
    private int factor = 3;

    private Matrix mMatrix = new Matrix();

    public MagnifierView(Context context) {
        this(context, null);
    }

    public MagnifierView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MagnifierView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.people);

        Bitmap scaledBitmap = Bitmap.createScaledBitmap(mBitmap, mBitmap.getWidth() * factor, mBitmap.getHeight() * factor, true);

        BitmapShader bitmapShader = new BitmapShader(scaledBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

        // 圆形的drawable
        mShapeDrawable = new ShapeDrawable(new OvalShape());
        mShapeDrawable.getPaint().setShader(bitmapShader);
        mShapeDrawable.setBounds(0, 0, radius * 2, radius * 2);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        if (mShapeDrawable != null) {
            // 画shader的起始位置
            mMatrix.setTranslate(radius - x * factor, radius - y * factor);
            mShapeDrawable.getPaint().getShader().setLocalMatrix(mMatrix);

            mShapeDrawable.setBounds(x - radius, y - radius, x + radius, y + radius);
            invalidate();
        }

        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mBitmap != null) {
            canvas.drawBitmap(mBitmap, 0, 0, null);
            mShapeDrawable.draw(canvas);
        }
    }

    public void setBitmap(Bitmap bitmap) {
        this.mBitmap = bitmap;

        BitmapShader bitmapShader = new BitmapShader(Bitmap.createScaledBitmap(mBitmap,
                mBitmap.getWidth() * factor, mBitmap.getHeight() * factor, true),
                Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

        // 圆形的drawable
        mShapeDrawable = new ShapeDrawable(new OvalShape());
        mShapeDrawable.getPaint().setShader(bitmapShader);
        mShapeDrawable.setBounds(0, 0, radius * 2, radius * 2);

        invalidate();
    }
}
