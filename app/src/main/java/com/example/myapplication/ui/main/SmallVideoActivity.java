package com.example.myapplication.ui.main;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.util.DeviceUtils;
import com.example.myapplication.util.RecordUtils;
import com.example.myapplication.widget.RecorderProgress;

import java.util.Collections;
import java.util.List;

/**
 * Created by xieH on 2016/12/24 0024.
 */
public class SmallVideoActivity extends AppCompatActivity implements View.OnTouchListener, SurfaceHolder.Callback,
        RecorderProgress.OnProgressEndListener {

    private TextView mTipTv;

    private View mStateBtn;

    private RecorderProgress mProgressBar;

    /**
     * 是否上滑取消
     */
    private boolean isCancel;

    /**
     * 记录按下的Y坐标
     */
    private float startY = 0.0f;

    /**
     * 录制最长时间
     */
    public static int RECORD_TIME_MAX = 10 * 1000;
    /**
     * 录制最小时间
     */
    public static int RECORD_TIME_MIN = 2 * 1000;

    /**
     * 预览SurfaceView
     */
    private SurfaceView mSurfaceView;


    private SurfaceHolder mSurfaceHolder;

    /**
     * 对焦显示的矩形
     */
    private ImageView mFocusingIv;

    /**
     * 手势处理, 主要用于变焦 (双击放大缩小)
     */
    private GestureDetector mDetector;

    private RecordUtils mRecordUtils;

    //是否放大
    private boolean isZoomIn = false;

    /**
     * 摄像头对象
     */
    private Camera mCamera;

    /**
     * 摄像头参数
     */
    private Camera.Parameters mParameters;

    /**
     * 摄像头支持的预览尺寸集合
     */
    private List<Camera.Size> mSupportedPreviewSizes;

    /**
     * 最大帧率
     */
    public static final int MAX_FRAME_RATE = 25;
    /**
     * 最小帧率
     */
    public static final int MIN_FRAME_RATE = 15;

    /**
     * 帧率
     */
    protected int mFrameRate = MIN_FRAME_RATE;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_small_video);

        initView();
    }

    public void initView() {

        mSurfaceView = (SurfaceView) findViewById(R.id.small_video_surfaceView);
        mFocusingIv = (ImageView) findViewById(R.id.small_video_focusing_iv);

        ///////////
        mStateBtn = findViewById(R.id.small_video_control_ll);
        mProgressBar = (RecorderProgress) findViewById(R.id.small_video_progress_bar);

        mTipTv = (TextView) findViewById(R.id.small_video_tip_tv);


        mSurfaceHolder = mSurfaceView.getHolder();
        // 设置屏幕分辨率
        mSurfaceHolder.setFixedSize(RecordUtils.VIDEO_WITH, RecordUtils.VIDEO_HEIGHT);
        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        mSurfaceHolder.addCallback(this);

        mDetector = new GestureDetector(this, new ZoomGestureListener());
        // 单独处理mSurfaceView的双击事件
        mSurfaceView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mDetector.onTouchEvent(event);
                return true;
            }
        });

        mRecordUtils = new RecordUtils(this);
        mRecordUtils.setSurfaceHolder(mSurfaceHolder);

        ///////////
        mStateBtn.setOnTouchListener(this);
        mProgressBar.setOnProgressEndListener(this);
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mProgressBar.setCancel(false);

                // 显示上滑取消
                mTipTv.setVisibility(View.VISIBLE);
                mTipTv.setText("↑ 上滑取消");

                // 记录按下的Y坐标
                startY = event.getY();

                // 显示进度条
                mProgressBar.setVisibility(View.VISIBLE);

                // 开始录制
                mRecordUtils.startRecord();
                mProgressBar.startAnimation();

                break;
            case MotionEvent.ACTION_MOVE:
                float moveY = event.getY();
                float duration = moveY - startY;

                if ((duration > 0.0f) && Math.abs(duration) > 25.0f) {
                    isCancel = false;
                    mTipTv.setText("↑ 上滑取消");
                    mTipTv.setTextColor(Color.WHITE);
                }

                if ((duration < 0.0f) && (Math.abs(duration) > 25.0f)) {
                    isCancel = true;
                    mTipTv.setText("松开取消");
                    mTipTv.setTextColor(Color.RED);
                }

                mProgressBar.setCancel(isCancel);
                break;
            case MotionEvent.ACTION_UP:
                mProgressBar.setVisibility(View.INVISIBLE);

                if (!isCancel) { // 判断是否录制结束，或成功录制（时间太短）
                    if (mProgressBar.getRecordTime() < RECORD_TIME_MIN) {
                        // 时间太短不保存
                        stopRecordAndUnSave();
                        Toast.makeText(this, "时间太短", Toast.LENGTH_SHORT).show();
                    } else {
                        // 停止录制
                        stopRecordAndSave();
                    }
                } else {
                    // 现在是取消状态，不保存
                    stopRecordAndUnSave();

                    Toast.makeText(this, "取消录制", Toast.LENGTH_SHORT).show();
                    isCancel = false;
                    mProgressBar.setCancel(false);
                    mTipTv.setText("双击放大");
                    mTipTv.setTextColor(Color.WHITE);
                }
                break;
            default:
                break;
        }

        return true;
    }

    /**
     * 停止录制，并保存
     */
    public void stopRecordAndSave() {

        mRecordUtils.setOnSaveSuccessListener(new RecordUtils.OnSaveSuccessListener() {
            @Override
            public void OnSaveSuccess(String filePath) {
                Intent intent = new Intent();
                intent.putExtra("filePath", filePath);

                setResult(RESULT_OK, intent);

                SmallVideoActivity.this.finish();
            }
        });

        mRecordUtils.stopRecordAndSave();
        mProgressBar.stopAnimation();
    }

    /**
     * 停止录制，不保存
     */
    public void stopRecordAndUnSave() {
        mRecordUtils.stopRecordAndUnSave();
        mProgressBar.stopAnimation();
    }

    @Override
    public void onProgressEndListener() {
        // 时间超限，停止录制
        stopRecordAndSave();
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        this.mSurfaceHolder = surfaceHolder;
        startPreView(surfaceHolder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        if (mCamera != null) {
            // 停止预览并释放摄像头资源
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }

    /**
     * 开启预览
     */
    private void startPreView(SurfaceHolder surfaceHolder) {

        try {
            if (mCamera == null) { // Camera.CameraInfo.CAMERA_FACING_BACK
                mCamera = Camera.open();
            }

            // 配置Camera
            mRecordUtils.setCamera(mCamera);

            if (mCamera != null) {
                mCamera.setDisplayOrientation(90);

                mCamera.setPreviewDisplay(surfaceHolder);
                mParameters = mCamera.getParameters();

                mSupportedPreviewSizes = mParameters.getSupportedPreviewSizes();//	获取支持的尺寸
                prepareCameraParameters();
                mCamera.setParameters(mParameters);

                mCamera.startPreview();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 预处理一些拍摄参数
     * 注意：自动对焦参数cam_mode和cam-mode可能有些设备不支持，导致视频画面变形，需要判断一下，已知有"GT-N7100", "GT-I9308"会存在这个问题
     */
    @SuppressWarnings("deprecation")
    protected void prepareCameraParameters() {
        if (mParameters == null)
            return;

        List<Integer> rates = mParameters.getSupportedPreviewFrameRates();
        if (rates != null) {
            if (rates.contains(MAX_FRAME_RATE)) {
                mFrameRate = MAX_FRAME_RATE;
            } else {
                Collections.sort(rates);
                for (int i = rates.size() - 1; i >= 0; i--) {
                    if (rates.get(i) <= MAX_FRAME_RATE) {
                        mFrameRate = rates.get(i);
                        break;
                    }
                }
            }
        }

        mParameters.setPreviewFrameRate(mFrameRate);
        // mParameters.setPreviewFpsRange(15 * 1000, 20 * 1000);
        mParameters.setPreviewSize(640, 480);// 3:2

        // 设置输出视频流尺寸，采样率
        mParameters.setPreviewFormat(ImageFormat.NV21);

        //设置自动连续对焦
        String mode = getAutoFocusMode();
        if (!TextUtils.isEmpty(mode)) {
            mParameters.setFocusMode(mode);
        }

        //设置人像模式，用来拍摄人物相片，如证件照。数码相机会把光圈调到最大，做出浅景深的效果。而有些相机还会使用能够表现更强肤色效果的色调、对比度或柔化效果进行拍摄，以突出人像主体。
        //		if (mCameraId == Camera.CameraInfo.CAMERA_FACING_FRONT && isSupported(mParameters.getSupportedSceneModes(), Camera.Parameters.SCENE_MODE_PORTRAIT))
        //			mParameters.setSceneMode(Camera.Parameters.SCENE_MODE_PORTRAIT);

        if (isSupported(mParameters.getSupportedWhiteBalance(), "auto"))
            mParameters.setWhiteBalance("auto");

        //是否支持视频防抖
        if ("true".equals(mParameters.get("video-stabilization-supported")))
            mParameters.set("video-stabilization", "true");
        if (!DeviceUtils.isDevice("GT-N7100", "GT-I9308", "GT-I9300")) {
            mParameters.set("cam_mode", 1);
            mParameters.set("cam-mode", 1);
        }
    }

    /**
     * 连续自动对焦
     */
    private String getAutoFocusMode() {
        if (mParameters != null) {
            //持续对焦是指当场景发生变化时，相机会主动去调节焦距来达到被拍摄的物体始终是清晰的状态。
            List<String> focusModes = mParameters.getSupportedFocusModes();
            if ((Build.MODEL.startsWith("GT-I950") || Build.MODEL.endsWith("SCH-I959") || Build.MODEL.endsWith("MEIZU MX3")) && isSupported(focusModes, "continuous-picture")) {
                return "continuous-picture";
            } else if (isSupported(focusModes, "continuous-video")) {
                return "continuous-video";
            } else if (isSupported(focusModes, "auto")) {
                return "auto";
            }
        }
        return null;
    }

    /**
     * 检测是否支持指定特性
     */
    private boolean isSupported(List<String> list, String key) {
        return list != null && list.contains(key);
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
                setZoom(10);
                isZoomIn = true;
            } else {
                setZoom(0);
                isZoomIn = false;
            }
            return true;
        }
    }

    /**
     * 相机变焦
     *
     * @param zoomValue
     */
    public void setZoom(int zoomValue) {
        if (mCamera != null) {
            Camera.Parameters parameters = mCamera.getParameters();
            if (parameters.isZoomSupported()) {//判断是否支持
                int maxZoom = parameters.getMaxZoom();
                if (maxZoom == 0) {
                    return;
                }
                if (zoomValue > maxZoom) {
                    zoomValue = maxZoom;
                }
                parameters.setZoom(zoomValue);
                mCamera.setParameters(parameters);
            }
        }
    }
}
