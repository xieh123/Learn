package com.example.myapplication.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;

import com.example.myapplication.R;

/**
 * Created by xieH on 2017/1/7 0007.
 */
public class PathTestView extends View {


    private Context mContext;

    /**
     * 用于记录当前的位置，取值范围[0,1]映射Path的整个长度
     */
    private float mCurrentValue = 0;

    /**
     * 当前点的实际位置
     */
    private float[] pos;

    /**
     * 当前点的tangent值，用于计算图片所需旋转的角度  // tan	该点的正切值
     */
    private float[] tan;

    /**
     * 箭头图片
     */
    private Bitmap mBitmap;

    /**
     * 矩阵，用于对图片进行一些操作
     */
    private Matrix mMatrix;

    private Paint mDefaultPaint;

    private Path path;


    public PathTestView(Context context) {
        this(context, null);
    }

    public PathTestView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PathTestView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.mContext = context;
        init();
    }

    public void init() {

        pos = new float[2];
        tan = new float[2];

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 3;  // 缩放图片

        mBitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.arrow, options);

        mMatrix = new Matrix();

        mDefaultPaint = new Paint();

        mDefaultPaint.setAntiAlias(true);
        mDefaultPaint.setStyle(Paint.Style.STROKE);
        mDefaultPaint.setStrokeWidth(5);

        mDefaultPaint.setColor(Color.BLACK);

        path = new Path();
        path.addCircle(200, 200, 100, Path.Direction.CW);  // Path.Direction.CW顺时针方向
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        canvas.translate(mViewWith / 2, mViewHeight / 2);

        PathMeasure pathMeasure = new PathMeasure(path, false);

        mCurrentValue += 0.005;   // 用于计算当前的位置在总长度上的比例[0,1]
        if (mCurrentValue >= 1) {
            mCurrentValue = 0;
        }

        pathMeasure.getPosTan(pathMeasure.getLength() * mCurrentValue, pos, tan); // 获取当前位置的坐标以及趋势

        mMatrix.reset(); // 重置Matrix

        float degrees = (float) (Math.atan2(tan[1], tan[0]) * 180.0 / Math.PI); // 计算图片旋转的角度

        mMatrix.postRotate(degrees, mBitmap.getWidth() / 2, mBitmap.getHeight() / 2); // 旋转图片
        mMatrix.postTranslate(pos[0] - mBitmap.getWidth() / 2, pos[1] - mBitmap.getHeight() / 2); // 将图片绘制中心调整到当前点重合

        canvas.drawPath(path, mDefaultPaint);  // 绘制Path
        canvas.drawBitmap(mBitmap, mMatrix, mDefaultPaint); // 绘制箭头

        invalidate();
    }
}
