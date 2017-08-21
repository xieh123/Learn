package com.example.myapplication.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

/**
 * Created by xieH on 2016/1/23.
 */
public class GuideView extends RelativeLayout implements ViewTreeObserver.OnGlobalLayoutListener {

    private final String TAG = getClass().getSimpleName();
    private Context mContext;
    private boolean first = true;
    private boolean isShowOnce = false;

    /**
     * mTargetView前缀。SHOW_GUIDE_PREFIX + mTargetView.getId()作为保存在SP文件的key。
     */
    private static final String SHOW_GUIDE_PREFIX = "show_guide_on_view_";
    /**
     * GuideView 偏移量
     */
    private int offsetX, offsetY;
    /**
     * mTargetView 的外切圆半径
     */
    private int radius;
    /**
     * 需要显示提示信息的View
     */
    private View mTargetView;
    /**
     * 自定义View
     */
    private View mCustomGuideView;
    /**
     * 透明圆形画笔
     */
    private Paint mCirclePaint;
    /**
     * 背景色画笔
     */
    private Paint mBackgroundPaint;
    /**
     * mTargetView是否已测量
     */
    private boolean isMeasured;
    /**
     * mTargetView圆心坐标
     */
    private int[] center;
    /**
     * 绘图层叠模式
     */
    private PorterDuffXfermode porterDuffXfermode;
    /**
     * 绘制前景bitmap
     */
    private Bitmap bitmap;
    /**
     * 背景色和透明度，格式 #aarrggbb
     */
    private int backgroundColor;
    /**
     * Canvas,绘制bitmap
     */
    private Canvas temp;
    /**
     * 相对于mTargetView的位置
     */
    private Direction direction;
    /**
     * mTargetView左上角坐标
     */
    private int[] location;
    private boolean onClickExit;
    private OnClickCallback onclickListener;


    public GuideView(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    private void init() {
        //    mContext.getSharedPreferences(TAG, Context.MODE_PRIVATE).edit().clear().commit();

        mBackgroundPaint = new Paint();

        mCirclePaint = new Paint();
    }

    /**
     * 添加提示文字，位置在mTargetView的下边
     * 在屏幕窗口，添加蒙层，蒙层绘制总背景和透明圆形，圆形下边绘制说明文字
     */
    private void createGuideView() {
        // Tips布局参数
        LayoutParams guideViewParams;
        guideViewParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        guideViewParams.setMargins(0, center[1] + radius + 10, 0, 0);

        if (mCustomGuideView != null) {
            if (direction != null) {
//                int width = this.getMeasuredWidth();
//                int height = this.getMeasuredHeight();

                int width = getScreenWidth(mContext);
                int height = getScreenHeight(mContext);

                int left = center[0] - radius;
                int right = center[0] + radius;
                int top = center[1] - radius;
                int bottom = center[1] + radius;

//                int left = center[0];
//                int right = center[0];
//                int top = center[1];
//                int bottom = center[1];

                switch (direction) {
                    case TOP:
                        this.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
                        guideViewParams.setMargins(offsetX, 0, 0, height - top + offsetY);
                        break;
                    case LEFT:
                        this.setGravity(Gravity.RIGHT);
                        guideViewParams.setMargins(0, top + offsetY, width - left + offsetX, 0);
                        break;
                    case BOTTOM:
                        this.setGravity(Gravity.CENTER_HORIZONTAL);
                        guideViewParams.setMargins(offsetX, bottom + offsetY, 0, 0);
                        break;
                    case RIGHT:
                        guideViewParams.setMargins(right + offsetX, top + offsetY, 0, 0);
                        break;
                    case LEFT_TOP:
                        this.setGravity(Gravity.RIGHT | Gravity.BOTTOM);
                        guideViewParams.setMargins(0, 0, width - left + offsetX, height - top + offsetY);
                        break;
                    case LEFT_BOTTOM:
                        this.setGravity(Gravity.RIGHT);
                        guideViewParams.setMargins(0, bottom + offsetY, width - left + offsetX, 0);
                        break;
                    case RIGHT_TOP:
                        this.setGravity(Gravity.BOTTOM);
                        guideViewParams.setMargins(right + offsetX, 0, 0, height - top + offsetY);
                        break;
                    case RIGHT_BOTTOM:
                        guideViewParams.setMargins(right + offsetX, bottom + offsetY, 0, 0);
                        break;
                }
            } else {
                guideViewParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                guideViewParams.setMargins(offsetX, offsetY, -offsetX, -offsetY);
            }

            this.addView(mCustomGuideView, guideViewParams);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (!isMeasured || mTargetView == null)
            return;

        drawBackground(canvas);
    }

    private void drawBackground(Canvas canvas) {
        // 先绘制bitmap，再将bitmap绘制到屏幕
        bitmap = Bitmap.createBitmap(canvas.getWidth(), canvas.getHeight(), Bitmap.Config.ARGB_8888);
        temp = new Canvas(bitmap);

        if (backgroundColor != 0) {
            mBackgroundPaint.setColor(backgroundColor);
        } else {
            mBackgroundPaint.setColor(getResources().getColor(android.R.color.background_dark));
        }

        // 绘制屏幕背景
        temp.drawRect(0, 0, temp.getWidth(), temp.getHeight(), mBackgroundPaint);

        porterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT);// 或者CLEAR
        mCirclePaint.setXfermode(porterDuffXfermode);
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setColor(getResources().getColor(android.R.color.transparent));
        temp.drawCircle(center[0], center[1], radius, mCirclePaint);

        // 绘制到屏幕
        canvas.drawBitmap(bitmap, 0, 0, mBackgroundPaint);
        bitmap.recycle();
    }

    public void show() {
        if (hasShown())
            return;

        if (mTargetView != null) {
            mTargetView.getViewTreeObserver().addOnGlobalLayoutListener(this);
        }

        this.setBackgroundResource(android.R.color.transparent);

        ((FrameLayout) ((Activity) mContext).getWindow().getDecorView()).addView(this);
        first = false;

        if (isShowOnce) {
            mContext.getSharedPreferences(TAG, Context.MODE_PRIVATE).edit().putBoolean(generateUniqueId(mTargetView), true).commit();
        }
    }

    public void hide() {
        if (mCustomGuideView != null) {
            mTargetView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            this.removeAllViews();
            ((FrameLayout) ((Activity) mContext).getWindow().getDecorView()).removeView(this);
            restoreState();
        }
    }

    public void restoreState() {
        offsetX = offsetY = 0;
        radius = 0;
        mCirclePaint = null;
        mBackgroundPaint = null;
        isMeasured = false;
        center = null;
        porterDuffXfermode = null;
        bitmap = null;
        temp = null;
    }

    /**
     * 获得mTargetView 的宽高，如果未测量，返回｛-1， -1｝
     *
     * @return
     */
    private int[] getTargetViewSize() {
        int[] location = {-1, -1};
        if (isMeasured) {
            location[0] = mTargetView.getWidth();
            location[1] = mTargetView.getHeight();
        }
        return location;
    }

    /**
     * 获得mTargetView 的半径
     *
     * @return
     */
    private int getTargetViewRadius() {
        if (isMeasured) {
            int[] size = getTargetViewSize();
            int x = size[0];
            int y = size[1];

            return (int) (Math.sqrt(x * x + y * y) / 2);
        }
        return -1;
    }

    public int[] getLocation() {
        return location;
    }

    public void setLocation(int[] location) {
        this.location = location;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public void setOffsetX(int offsetX) {
        this.offsetX = offsetX;
    }

    public void setOffsetY(int offsetY) {
        this.offsetY = offsetY;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void setCustomGuideView(View mCustomGuideView) {
        this.mCustomGuideView = mCustomGuideView;
        if (!first) {
            restoreState();
        }
    }

    public void setBgColor(int background_color) {
        this.backgroundColor = background_color;
    }

    public View getTargetView() {
        return mTargetView;
    }

    public void setTargetView(View mTargetView) {
        this.mTargetView = mTargetView;
        if (!first) {
//            guideViewLayout.removeAllViews();
        }
    }

    public void showOnce() {
        if (mTargetView != null) {
            isShowOnce = true;
        }
    }

    private boolean hasShown() {
        if (mTargetView == null) return true;
        return mContext.getSharedPreferences(TAG, Context.MODE_PRIVATE).getBoolean(generateUniqueId(mTargetView), false);
    }

    private String generateUniqueId(View v) {
        return SHOW_GUIDE_PREFIX + v.getId();
    }

    public int[] getCenter() {
        return center;
    }

    public void setCenter(int[] center) {
        this.center = center;
    }

    public void setOnClickExit(boolean onClickExit) {
        this.onClickExit = onClickExit;
    }

    public void setOnclickListener(OnClickCallback onclickListener) {
        this.onclickListener = onclickListener;
    }

    private void setClickInfo() {
        final boolean exit = onClickExit;
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onclickListener != null) {
                    onclickListener.onClickedGuideView();
                }
                if (exit) {
                    hide();
                }
            }
        });
    }

    @Override
    public void onGlobalLayout() {
        if (isMeasured)
            return;
        if (mTargetView.getHeight() > 0 && mTargetView.getWidth() > 0) {
            isMeasured = true;
        }

        // 获取mTargetView的中心坐标
        if (center == null) {
            // 获取右上角坐标
            location = new int[2];
            mTargetView.getLocationInWindow(location);
            center = new int[2];
            // 获取中心坐标
            center[0] = location[0] + mTargetView.getWidth() / 2;
            center[1] = location[1] + mTargetView.getHeight() / 2;
        }
        // 获取mTargetView外切圆半径
        if (radius == 0) {
            radius = getTargetViewRadius();
        }
        // 添加GuideView
        createGuideView();
    }

    /**
     * 定义GuideView相对于mTargetView的方位，共八种。不设置则默认在mTargetView下方
     */
    public enum Direction {
        LEFT, TOP, RIGHT, BOTTOM,
        LEFT_TOP, LEFT_BOTTOM,
        RIGHT_TOP, RIGHT_BOTTOM
    }

    /**
     * GuideView点击Callback
     */
    public interface OnClickCallback {
        void onClickedGuideView();
    }

    /**
     * 获得屏幕高度
     *
     * @param context
     * @return
     */
    public  int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    /**
     * 获得屏幕宽度
     *
     * @param context
     * @return
     */
    public  int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    public static class Builder {
        static GuideView guiderView;
        static Builder instance = new Builder();
        Context mContext;

        private Builder() {
        }

        public Builder(Context ctx) {
            mContext = ctx;
        }

        public static Builder newInstance(Context ctx) {
            guiderView = new GuideView(ctx);
            return instance;
        }

        public Builder setTargetView(View target) {
            guiderView.setTargetView(target);
            return instance;
        }

        public Builder setBgColor(int color) {
            guiderView.setBgColor(color);
            return instance;
        }

        public Builder setDirection(Direction dir) {
            guiderView.setDirection(dir);
            return instance;
        }

        public Builder setOffset(int x, int y) {
            guiderView.setOffsetX(x);
            guiderView.setOffsetY(y);
            return instance;
        }

        public Builder setRadius(int radius) {
            guiderView.setRadius(radius);
            return instance;
        }

        public Builder setCustomGuideView(View view) {
            guiderView.setCustomGuideView(view);
            return instance;
        }

        public Builder setCenter(int X, int Y) {
            guiderView.setCenter(new int[]{X, Y});
            return instance;
        }

        public Builder showOnce() {
            guiderView.showOnce();
            return instance;
        }

        public GuideView build() {
            guiderView.setClickInfo();
            return guiderView;
        }

        public Builder setOnclickExit(boolean onclickExit) {
            guiderView.setOnClickExit(onclickExit);
            return instance;
        }

        public Builder setOnclickListener(final OnClickCallback callback) {
            guiderView.setOnclickListener(callback);
            return instance;
        }
    }
}
