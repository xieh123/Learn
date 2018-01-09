package com.example.myapplication.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ImageButton;

/**
 * Created by xieH on 2017/2/10 0010.
 */
public class RotatableImageButton extends ImageButton {
    private float degrees;

    public RotatableImageButton(Context paramContext) {
        super(paramContext);
    }

    public RotatableImageButton(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
    }

    public RotatableImageButton(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
        super(paramContext, paramAttributeSet, paramInt);
    }

    @Override
    protected void onDraw(Canvas paramCanvas) {
        paramCanvas.save(Canvas.MATRIX_SAVE_FLAG);
        paramCanvas.rotate(this.degrees, getWidth() / 2.0F, getHeight() / 2.0F);
        super.onDraw(paramCanvas);
        paramCanvas.restore();
    }

    public void setAngle(float paramFloat) {
        this.degrees = paramFloat;
    }
}
