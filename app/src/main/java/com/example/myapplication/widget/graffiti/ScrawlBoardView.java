package com.example.myapplication.widget.graffiti;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Xfermode;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 画板
 */
public class ScrawlBoardView extends View {

    Canvas paintCanvas;
    Paint paint, eraserPaint;
    Bitmap bitmap;
    Bitmap backgroundBitmap;

    float startX, startY, endX, endY;

    Context context;

    boolean isEraser;
    List<DrawPathEntry> drawPathList = new ArrayList<>();
    Path path;

    public ScrawlBoardView(Context context) {
        super(context);
    }

    public ScrawlBoardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setFilterBitmap(true);
        paint.setColor(ContextCompat.getColor(context, R.color.red));
        paint.setStrokeWidth(10);

        eraserPaint = new Paint();
        eraserPaint.setStyle(Paint.Style.STROKE);
        eraserPaint.setStrokeWidth(20);
        eraserPaint.setColor(Color.TRANSPARENT);
        Xfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.CLEAR);
        eraserPaint.setXfermode(xfermode);

        this.context = context;
    }

    public ScrawlBoardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (backgroundBitmap != null && !backgroundBitmap.isRecycled()) {
            canvas.drawBitmap(backgroundBitmap, 0, 0, null);
        }

        if (bitmap != null && !bitmap.isRecycled()) {
            canvas.drawBitmap(bitmap, 0, 0, null);
        }
    }

    /**
     * 设置背景图片及监理新的用来涂鸦的Bitmap
     *
     * @param bitmap 传入的截图界面图片
     */
    public void setBackgroud(Bitmap bitmap) {
        this.backgroundBitmap = bitmap;
        this.bitmap = Bitmap.createBitmap(backgroundBitmap.getWidth(), backgroundBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        paintCanvas = new Canvas(this.bitmap);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = event.getX();
                startY = event.getY();

                path = new Path();
                path.moveTo(startX, startY);

                break;

            case MotionEvent.ACTION_MOVE:
                endX = event.getX();
                endY = event.getY();
                path.quadTo(startX, startY, endX, endY);
                paintCanvas.drawPath(path, isEraser ? eraserPaint : paint);
                startX = endX;
                startY = endY;
                postInvalidate();
                break;

            case MotionEvent.ACTION_UP:
                drawPathList.add(new DrawPathEntry(path, isEraser ? eraserPaint.getColor() : paint.getColor(), isEraser));
                break;

            default:
                break;
        }
        return true;
    }

    /**
     * 设置画笔颜色及橡皮擦
     *
     * @param type
     */
    public void setPaintType(int type) {
        isEraser = false;
        switch (type) {
            case AnnotationConfig.PaintType.Paint_Red:
                paint.setColor(ContextCompat.getColor(context, R.color.red));
                break;
            case AnnotationConfig.PaintType.Paint_Orange:
                paint.setColor(ContextCompat.getColor(context, R.color.orange));
                break;
            case AnnotationConfig.PaintType.Paint_Yellow:
                paint.setColor(ContextCompat.getColor(context, R.color.yellow));
                break;
            case AnnotationConfig.PaintType.Paint_Green:
                paint.setColor(ContextCompat.getColor(context, R.color.green));
                break;
            case AnnotationConfig.PaintType.Paint_Blue:
                paint.setColor(ContextCompat.getColor(context, R.color.blue));
                break;
            case AnnotationConfig.PaintType.Paint_Purple:
                paint.setColor(ContextCompat.getColor(context, R.color.purple));
                break;
            case AnnotationConfig.PaintType.Paint_Eraser:
                isEraser = true;
                break;
            default:
                break;
        }
    }

    /**
     * 撤销操作
     */
    public void cancelPath() {
        if (drawPathList != null && drawPathList.size() <= 0) {
            return;
        }
        drawPathList.remove(drawPathList.size() - 1);
        paintCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        for (DrawPathEntry entry : drawPathList) {
            paint.setColor(entry.getPaintColor());
            paintCanvas.drawPath(entry.getPath(), entry.isEraser() ? eraserPaint : paint);
        }
        postInvalidate();
    }


    /**
     * 清空涂鸦
     */
    public void clearScrawlBoard() {
        paintCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        drawPathList.clear();
        postInvalidate();
    }

    /**
     * @return 返回最终的涂鸦好的图片
     */
    public Bitmap getSrawBoardBitmap() {
        Bitmap resultBitmap = Bitmap.createBitmap(backgroundBitmap.getWidth(), backgroundBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(resultBitmap);
        canvas.drawBitmap(backgroundBitmap, 0, 0, null);
        canvas.drawBitmap(bitmap, 0, 0, null);
        canvas.save();
        return resultBitmap;
    }

}
