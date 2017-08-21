package com.example.myapplication.widget.camera;

import android.content.Context;
import android.graphics.Point;
import android.hardware.Camera;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.example.myapplication.R;

/**
 * Created by xieH on 2016/12/30 0030.
 */
public class MySurfaceView extends FrameLayout implements IActivityLifeCycle {

    private Context mContext;

    private int screenWidth;

    private CameraView mCameraView;

    private FocusingView mFocusingView;

    //是否放大
    private boolean isZoomIn = false;

    /**
     * 手势处理, 主要用于变焦 (双击放大缩小)
     */
    private GestureDetector mDetector;

    private SensorController mSensorController;

    private Handler mHandler = new Handler();


    public MySurfaceView(Context context) {
        this(context, null);
    }

    public MySurfaceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MySurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;

        init();
    }

    public void init() {

        inflate(mContext, R.layout.layout_camera, this);

        mCameraView = (CameraView) findViewById(R.id.camera_camera_view);
        mFocusingView = (FocusingView) findViewById(R.id.camera_focusing_view);

        Display display = ((WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        screenWidth = display.getWidth();

        mSensorController = SensorController.getInstance();

        mSensorController.setCameraFocusListener(new SensorController.CameraFocusListener() {
            @Override
            public void onFocus() {
                Point point = new Point(screenWidth / 2, screenWidth / 2);

                onCameraFocus(point);
            }
        });

        mDetector = new GestureDetector(mContext, new ZoomGestureListener());
    }

    /**
     * 相机对焦  默认不需要延时
     *
     * @param point
     */
    private void onCameraFocus(final Point point) {
        onCameraFocus(point, false);
    }

    /**
     * 相机对焦
     *
     * @param point
     * @param needDelay 是否需要延时
     */
    public void onCameraFocus(final Point point, boolean needDelay) {
        long delayDuration = needDelay ? 300 : 0;

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!mSensorController.isFocusLocked()) {
                    if (mCameraView.onFocus(point, mAutoFocusCallback)) {
                        mSensorController.lockFocus();
                        mFocusingView.startFocus(point);
                    }
                }
            }
        }, delayDuration);
    }

    private final Camera.AutoFocusCallback mAutoFocusCallback = new Camera.AutoFocusCallback() {

        @Override
        public void onAutoFocus(boolean success, Camera camera) {
            // 聚焦之后根据结果修改图片
            if (success) {
                mFocusingView.onFocusSuccess();
            } else {
                // 聚焦失败显示的图片，由于未找到合适的资源，这里仍显示同一张图片
                mFocusingView.onFocusFailed();
            }

            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // 一秒之后才能再次对焦
                    mSensorController.unlockFocus();
                }
            }, 1000);
        }
    };

    @Override
    public void onStart() {
        mSensorController.onStart();
    }

    @Override
    public void onStop() {
        mSensorController.onStop();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                break;
            case MotionEvent.ACTION_UP:
                // 设置聚焦
                Point point = new Point((int) event.getX(), (int) event.getY());
                onCameraFocus(point);
                break;
        }

        mDetector.onTouchEvent(event);
        return true;
    }

    /**
     * 变焦手势处理类
     */
    private class ZoomGestureListener extends GestureDetector.SimpleOnGestureListener {
        //双击手势事件
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            super.onDoubleTap(e);

            if (!isZoomIn) {
                mCameraView.setZoom(20);
                isZoomIn = true;
            } else {
                mCameraView.setZoom(0);
                isZoomIn = false;
            }
            return true;
        }
    }

    public void setOnCameraPrepareListener(CameraView.OnCameraPrepareListener onCameraPrepareListener) {
        mCameraView.setOnCameraPrepareListener(onCameraPrepareListener);
    }
}
