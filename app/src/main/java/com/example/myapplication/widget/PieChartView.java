package com.example.myapplication.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.myapplication.model.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xieH on 2017/7/5 0005.
 */
public class PieChartView extends View {

    private Context mContext;
    private List<Message> mList;

    private Paint arcPaint;
    private Paint linePaint;
    private Paint textPaint;

    private float centerX;
    private float centerY;
    private float radius;
    private float total;
    private float startAngle;
    private float sweepAngle;
    private float textAngle;

    private List<PointF[]> lineList;
    private List<PointF> textList;

    private List<Point> pointList = new ArrayList<>();

    public PieChartView(Context context) {
        this(context, null);
    }

    public PieChartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PieChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        this.lineList = new ArrayList<>();
        this.textList = new ArrayList<>();
        this.mList = new ArrayList<>();

        this.arcPaint = new Paint();
        this.arcPaint.setAntiAlias(true);
        this.arcPaint.setDither(true);
        this.arcPaint.setStyle(Paint.Style.STROKE);

        this.linePaint = new Paint();
        this.linePaint.setAntiAlias(true);
        this.linePaint.setDither(true);
        this.linePaint.setStyle(Paint.Style.STROKE);
        this.linePaint.setStrokeWidth(dip2px(mContext, 2));
        this.linePaint.setColor(Color.parseColor("#FFFFFF"));

        this.textPaint = new Paint();
        this.textPaint.setAntiAlias(true);
        this.textPaint.setDither(true);
        this.textPaint.setStyle(Paint.Style.FILL);
        this.textPaint.setColor(Color.parseColor("#FFFFFF"));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerX = w / 2;
        centerY = h / 2;
        radius = Math.min(centerX, centerY) * 0.725f;

        arcPaint.setStrokeWidth(radius * 2 / 3);
        textPaint.setTextSize(radius / 7);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        textList.clear();
        lineList.clear();

        startAngle = 0.0f;

        if (mList != null) {
            RectF mRectF = new RectF(centerX - radius, centerY - radius, centerX + radius, centerY + radius);

            for (int i = 0; i < mList.size(); i++) {
                arcPaint.setColor(mList.get(i).getColor());

                sweepAngle = mList.get(i).getPercent() / total * 360f;

                /**
                 * oval :指定圆弧的外轮廓矩形区域。
                 * startAngle: 圆弧起始角度，单位为度。
                 * sweepAngle: 圆弧扫过的角度，顺时针方向，单位为度。
                 * useCenter: 如果为True时，在绘制圆弧时将圆心包括在内，通常用来绘制扇形。
                 * paint: 绘制圆弧的画板属性，如颜色，是否填充等。
                 */
                canvas.drawArc(mRectF, startAngle, sweepAngle, false, arcPaint);

                lineList.add(getLinePointFs());   // 获取直线 开始坐标 结束坐标

                textAngle = startAngle + sweepAngle / 2;
                textList.add(getTextPointF());   // 获取文本文本

                pointList.get(i).x = (int) startAngle;
                pointList.get(i).y = (int) (startAngle + sweepAngle);

                startAngle += sweepAngle;
            }

            // 绘制间隔空白线
            drawSpacingLine(canvas, lineList);
            // 绘制文字
            drawText(canvas);
        }

    }

    /**
     * 获取文本文本
     *
     * @return
     */
    private PointF getTextPointF() {
        float textPointX = (float) (centerX + radius * Math.cos(Math.toRadians(textAngle)));
        float textPointY = (float) (centerY + radius * Math.sin(Math.toRadians(textAngle)));
        return new PointF(textPointX, textPointY);
    }

    /**
     * 获取直线 开始坐标 结束坐标
     *
     * @return
     */
    private PointF[] getLinePointFs() {
        float stopX = (float) (centerX + (radius + arcPaint.getStrokeWidth() / 2) * Math.cos(Math.toRadians(startAngle)));
        float stopY = (float) (centerY + (radius + arcPaint.getStrokeWidth() / 2) * Math.sin(Math.toRadians(startAngle)));
        float startX = (float) (centerX + (radius - arcPaint.getStrokeWidth() / 2) * Math.cos(Math.toRadians(startAngle)));
        float startY = (float) (centerY + (radius - arcPaint.getStrokeWidth() / 2) * Math.sin(Math.toRadians(startAngle)));
        PointF startPoint = new PointF(startX, startY);
        PointF stopPoint = new PointF(stopX, stopY);
        return new PointF[]{startPoint, stopPoint};
    }

    /**
     * 画间隔线
     *
     * @param canvas
     */
    private void drawSpacingLine(Canvas canvas, List<PointF[]> pointFs) {
        for (PointF[] fp : pointFs) {
            canvas.drawLine(fp[0].x, fp[0].y, fp[1].x, fp[1].y, linePaint);
        }
    }

    /**
     * 画文本
     *
     * @param canvas
     */
    private void drawText(Canvas canvas) {
        for (int i = 0; i < textList.size(); i++) {
            textPaint.setTextAlign(Paint.Align.CENTER);
            String text = mList.get(i).getContent();
            canvas.drawText(text, textList.get(i).x, textList.get(i).y, textPaint);

            Paint.FontMetrics fm = textPaint.getFontMetrics();
//            canvas.drawText(format.format(mList.get(i).getPercent() * 100 / total) + "%", textList.get(i).x, textList.get(i).y + (fm.descent - fm.ascent), textPaint);

            canvas.drawText("饼图", textList.get(i).x, textList.get(i).y + (fm.descent - fm.ascent), textPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            float x = event.getX();
            float y = event.getY();
            int angle = 0; // 角度

            // 第四象限
            if (x >= getMeasuredWidth() / 2 && y >= getMeasuredHeight() / 2) {
                angle = (int) (Math.atan((y - getMeasuredHeight() / 2) * 1.0f
                        / (x - getMeasuredWidth() / 2)) * 180 / Math.PI);
            }
            // 第三象限
            if (x <= getMeasuredWidth() / 2 && y >= getMeasuredHeight() / 2) {
                angle = (int) (Math.atan((getMeasuredWidth() / 2 - x)
                        / (y - getMeasuredHeight() / 2))
                        * 180 / Math.PI + 90);
            }
            // 第二象限
            if (x <= getMeasuredWidth() / 2 && y <= getMeasuredHeight() / 2) {
                angle = (int) (Math.atan((getMeasuredHeight() / 2 - y)
                        / (getMeasuredWidth() / 2 - x))
                        * 180 / Math.PI + 180);
            }
            // 第一象限
            if (x >= getMeasuredWidth() / 2 && y <= getMeasuredHeight() / 2) {
                angle = (int) (Math.atan((x - getMeasuredWidth() / 2)
                        / (getMeasuredHeight() / 2 - y))
                        * 180 / Math.PI + 270);
            }

            for (int i = 0; i < pointList.size(); i++) {
                Point point = pointList.get(i);
                if (point.x <= angle && point.y >= angle) {
//                    select = i;
                    System.out.println("hhhh----" + i);
                    invalidate();
                    return true;
                }
            }
            return true;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 设置间隔线的颜色
     *
     * @param color
     */
    public void setSpacingLineColor(int color) {
        linePaint.setColor(color);
    }

    /**
     * 设置文本颜色
     *
     * @param color
     */
    public void setTextColor(int color) {
        textPaint.setColor(color);
    }

    /**
     * 设置开始角度
     *
     * @param startAngle
     */
    public void setStartAngle(float startAngle) {
        this.startAngle = startAngle;
    }

    /**
     * 设置饼的宽度
     *
     * @param width
     */
    public void setChartStrokeWidth(int width) {
        arcPaint.setStrokeWidth(dip2px(mContext, width));
    }

    /**
     * 设置饼的数据
     *
     * @param mList
     */
    public void setChartData(List<Message> mList) {
        total = 0;
        if (mList == null) {
            return;
        }
        for (int i = 0; i < mList.size(); i++) {
            total += mList.get(i).getPercent();

            Point point = new Point();
            pointList.add(point);
        }
        this.mList = mList;
        invalidate();
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
