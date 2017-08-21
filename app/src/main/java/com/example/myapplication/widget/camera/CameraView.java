package com.example.myapplication.widget.camera;

import android.content.Context;
import android.graphics.ImageFormat;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.Camera;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.myapplication.utils.DeviceUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by xieH on 2016/12/30 0030.
 */
public class CameraView extends SurfaceView implements SurfaceHolder.Callback {

    private Context mContext;

    private SurfaceHolder mSurfaceHolder;

    /**
     * 摄像头对象
     */
    private Camera mCamera;

    /**
     * 摄像头类型（前置/后置），默认后置
     */
    protected int mCameraId = Camera.CameraInfo.CAMERA_FACING_BACK;

    /**
     * 摄像头参数
     */
    private Camera.Parameters mParameters = null;

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

    /**
     * 是否放大
     */
    private boolean isZoomIn = false;

    /**
     * 传感器
     */
    private SensorController mSensorController;

    private OnCameraPrepareListener onCameraPrepareListener;


    public CameraView(Context context) {
        this(context, null);
    }

    public CameraView(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.mContext = context;
        init();
    }


    public void init() {

        mSurfaceHolder = getHolder();
        // 设置屏幕分辨率
        mSurfaceHolder.setFixedSize(640, 480);
        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        mSurfaceHolder.addCallback(this);

        mSensorController = SensorController.getInstance();
    }


    /**
     * 是否前置摄像头
     */
    public boolean isFrontCamera() {
        return mCameraId == Camera.CameraInfo.CAMERA_FACING_FRONT;
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        this.mSurfaceHolder = surfaceHolder;

        startPreView(surfaceHolder);

        if (onCameraPrepareListener != null) {
            onCameraPrepareListener.onPrepare(mCamera);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

        //释放资源
        if (surfaceHolder != null) {
            if (Build.VERSION.SDK_INT >= 14) {
                surfaceHolder.getSurface().release();
            }
        }

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
            if (mCamera == null) {
                if (mCameraId == Camera.CameraInfo.CAMERA_FACING_BACK) {
                    mCamera = Camera.open();
                } else {
                    mCamera = Camera.open(mCameraId);
                }
            }

            if (mCamera != null) {
                mCamera.setDisplayOrientation(90);

                mCamera.setPreviewDisplay(surfaceHolder);
                mParameters = mCamera.getParameters();

                prepareCameraParameters();
                mCamera.setParameters(mParameters);

                mCamera.startPreview();


                if (mCameraId == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                    mSensorController.lockFocus();
                } else {
                    mSensorController.unlockFocus();
                }
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
     * 手动聚焦
     *
     * @param point 触屏坐标
     */
    protected boolean onFocus(Point point, Camera.AutoFocusCallback callback) {
        if (mCamera == null) {
            return false;
        }

        try {
            if (mParameters != null) {
                //不支持设置自定义聚焦，则使用自动聚焦，返回
                if (Build.VERSION.SDK_INT >= 14) {
                    if (mParameters.getMaxNumFocusAreas() <= 0) {
                        mCamera.autoFocus(callback);
                        return true;
                    }

                    List<Camera.Area> areas = new ArrayList<Camera.Area>();
                    int left = point.x - 300;
                    int top = point.y - 300;
                    int right = point.x + 300;
                    int bottom = point.y + 300;
                    left = left < -1000 ? -1000 : left;
                    top = top < -1000 ? -1000 : top;
                    right = right > 1000 ? 1000 : right;
                    bottom = bottom > 1000 ? 1000 : bottom;
                    areas.add(new Camera.Area(new Rect(left, top, right, bottom), 100));
                    mParameters.setFocusAreas(areas);
                    mCamera.setParameters(mParameters);
                }
            } else {
                mCamera.autoFocus(callback);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
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

    public void setOnCameraPrepareListener(OnCameraPrepareListener onCameraPrepareListener) {
        this.onCameraPrepareListener = onCameraPrepareListener;
    }

    public interface OnCameraPrepareListener {
        void onPrepare(Camera mCamera);
    }

}
