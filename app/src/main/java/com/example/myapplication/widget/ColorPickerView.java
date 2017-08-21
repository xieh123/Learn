package com.example.myapplication.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by xieH on 2017/5/6 0006.
 */
public class ColorPickerView extends View {

    private Paint paint, indexPaint;
    private static final int[] mColors = new int[]{0xFFFF0000, 0xFFFFFF00, 0xFF00FF00, 0xFF00FFFF, 0xFF0000FF, 0xFFFF00FF, 0xFFFFFFFF, 0xFF000000};
    private static final float[] mPositions = new float[]{0.0f, 0.143f, 0.286f, 0.429f, 0.571f, 0.714f, 0.857f, 1.0f};
    private float density;
    private int currentColor = 0xff000000;

    private int paddingWidth;
    private float currentPosition = 1.0f;

    private float scale = 10.0f;

    private ColorPickerListener colorPickerListener;

    public ColorPickerView(Context context, AttributeSet attrs) {
        super(context, attrs);

        DisplayMetrics displaysMetrics = context.getApplicationContext().getResources().getDisplayMetrics();
        density = displaysMetrics.density;

        paddingWidth = (int) density * 12;

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeWidth(0);

        indexPaint = new Paint();
        indexPaint.setAntiAlias(true);
        indexPaint.setStrokeWidth(density);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        //画颜色条
        Shader s = new LinearGradient(paddingWidth, 0, width - paddingWidth, 0, mColors, mPositions, Shader.TileMode.MIRROR);
        paint.setShader(s);

        int hh = height >> 1;

        RectF rectF = new RectF(paddingWidth, (height >> 1) - scale * density, width - paddingWidth, (height >> 1) + scale * density);
        canvas.drawRoundRect(rectF, scale * density, scale * density, paint);
        //画指示器
        indexPaint.setColor(0xFF979797);
        indexPaint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(paddingWidth + (width - 2 * paddingWidth) * currentPosition, height >> 1, paddingWidth - density, indexPaint);
        indexPaint.setColor(0xFFFFFFFF);
        indexPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(paddingWidth + (width - 2 * paddingWidth) * currentPosition, height >> 1, paddingWidth - density, indexPaint);
        indexPaint.setColor(currentColor);
        indexPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(paddingWidth + (width - 2 * paddingWidth) * currentPosition, height >> 1, paddingWidth * 2, indexPaint);
    }

    private int getColorByPosition(float position) {
        //{0xFFFF0000, 0xFFFFFF00, 0xFF00FF00,0xFF00FFFF, 0xFF0000FF,0xFFFF00FF,0xFFFFFFFF, 0xFF000000};
        float length = (1.0f / 7.0f);
        int index = (int) (position / length);
        float percent = (position - ((float) index) * length) / length;
        switch (index) {
            case 0://0xFFFF0000, 0xFFFFFF00
                return Color.argb(0xFF, 0xFF, getNumberByPresent(percent, 0x00, 0xFF), 0x00);
            case 1://0xFFFFFF00, 0xFF00FF00
                return Color.argb(0xFF, getNumberByPresent(percent, 0xFF, 0x00), 0xFF, 0x00);
            case 2://0xFF00FF00,0xFF00FFFF
                return Color.argb(0xFF, 0x00, 0xFF, getNumberByPresent(percent, 0x00, 0xFF));
            case 3://0xFF00FFFF, 0xFF0000FF
                return Color.argb(0xFF, 0x00, getNumberByPresent(percent, 0xFF, 0x00), 0xFF);
            case 4://0xFF0000FF,0xFFFF00FF
                return Color.argb(0xFF, getNumberByPresent(percent, 0x00, 0xFF), 0x00, 0xFF);
            case 5://0xFFFF00FF,0xFFFFFFFF
                return Color.argb(0xFF, 0xFF, getNumberByPresent(percent, 0x00, 0xFF), 0xFF);
            case 6://0xFFFFFFFF, 0xFF000000
                int number = getNumberByPresent(percent, 0xFF, 0x00);
                return Color.argb(0xFF, number, number, number);
            case 7:
                return Color.parseColor("#FF000000");
            default:
                return Color.parseColor("#FF000000");

        }
    }

    private int getNumberByPresent(float present, int start, int end) {
        return (int) ((end - start) * present + start);
    }

    private void setPositionByColor(int color) {
        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);

        int index = 0;
        if (r == g && g == b) {
            index = 6;
        } else if (r == 0xFF && b == 0x00) {
            index = 0;
        } else if (g == 0xFF && b == 0x00) {
            index = 1;
        } else if (r == 0x00 && g == 0xFF) {
            index = 2;
        } else if (r == 0x00 && b == 0xFF) {
            index = 3;
        } else if (g == 0x00 && b == 0xFF) {
            index = 4;
        } else if (r == 0xFF && b == 0xFF) {
            index = 5;
        }

        float present = 0;
        switch (index) {
            case 0:
                present = getPresentByNumber(g, 0x00, 0xFF);
                break;
            case 1:
                present = getPresentByNumber(r, 0xFF, 0x00);
                break;
            case 2:
                present = getPresentByNumber(b, 0x00, 0xFF);
                break;
            case 3:
                present = getPresentByNumber(g, 0xFF, 0x00);
                break;
            case 4:
                present = getPresentByNumber(r, 0x00, 0xFF);
                break;
            case 5:
                present = getPresentByNumber(g, 0x00, 0xFF);
                break;
            case 6:
                present = getPresentByNumber(r, 0xFF, 0x00);
                break;
        }

        float length = (1.0f / 7.0f);
        currentPosition = length * ((float) index + present);
    }

    private float getPresentByNumber(int number, int start, int end) {
        return (float) (number - start) / (float) (end - start);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE: {
                if (colorPickerListener != null) {
                    if (x < paddingWidth) {
                        x = paddingWidth;
                    }
                    if (x > getWidth() - paddingWidth) {
                        x = getWidth() - paddingWidth;
                    }
                    currentPosition = (float) (x - paddingWidth) / (float) (getWidth() - paddingWidth * 2);
                    currentColor = getColorByPosition(currentPosition);
                    colorPickerListener.onColorChanging(currentColor);
                    postInvalidate();
                }
            }
            break;
            case MotionEvent.ACTION_UP: {
                if (colorPickerListener != null) {
                    if (x < paddingWidth) {
                        x = paddingWidth;
                    }
                    if (x > getWidth() - paddingWidth) {
                        x = getWidth() - paddingWidth;
                    }
                    currentPosition = (float) (x - paddingWidth) / (float) (getWidth() - paddingWidth * 2);
                    currentColor = getColorByPosition(currentPosition);
                    colorPickerListener.onColorPicked(currentColor);
                    postInvalidate();
                }
            }
            break;
        }
        return true;
    }

    public int getCurrentColor() {
        return currentColor;
    }

    public void setCurrentColor(int color) {
        this.currentColor = color;
        setPositionByColor(color);
        postInvalidate();
    }

    public void setColorPickerListener(ColorPickerListener listener) {
        this.colorPickerListener = listener;
    }

    public interface ColorPickerListener {
        void onColorChanging(int color);

        void onColorPicked(int color);
    }
}
