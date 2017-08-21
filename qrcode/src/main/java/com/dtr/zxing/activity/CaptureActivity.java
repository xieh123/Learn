/*
 * Copyright (C) 2008 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dtr.zxing.activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.dtr.zxing.BaseActivity;
import com.dtr.zxing.R;
import com.dtr.zxing.camera.CameraManager;
import com.dtr.zxing.decode.DecodeThread;
import com.dtr.zxing.decode.qr.QRCodeDecoder;
import com.dtr.zxing.decode.qr.QRCodeEncoder;
import com.dtr.zxing.utils.BeepManager;
import com.dtr.zxing.utils.CaptureActivityHandler;
import com.dtr.zxing.utils.DialogHelper;
import com.dtr.zxing.utils.InactivityTimer;
import com.dtr.zxing.utils.StringUtils;
import com.google.zxing.Result;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;


/**
 * This activity opens the camera and does the actual scanning on a background
 * thread. It draws a viewfinder to help the user place the barcode correctly,
 * shows feedback as the image processing is happening, and then overlays the
 * results when a scan is successful.
 *
 * @author dswitkin@google.com (Daniel Switkin)
 * @author Sean Owen
 */
public final class CaptureActivity extends BaseActivity implements
        SurfaceHolder.Callback, View.OnClickListener, EasyPermissions.PermissionCallbacks {

    private static final String TAG = CaptureActivity.class.getSimpleName();


    private static final int CAMERA_PERM = 1;

    private CameraManager cameraManager;
    private CaptureActivityHandler handler;
    private InactivityTimer inactivityTimer;
    private BeepManager beepManager;

    private SurfaceView mScanPreview = null;
    private RelativeLayout scanContainer;
    private RelativeLayout scanCropView;
    private ImageView scanLine;
    private ImageView mFlash;

    private Rect mCropRect = null;

    private boolean isHasSurface = false;

    private boolean mIsLight;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_qr_scan);

        // 保持屏幕亮
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        initView();
    }

    public void initView() {
        mScanPreview = (SurfaceView) findViewById(R.id.capture_preview);
        mScanPreview.getHolder().addCallback(this);

        requestCamera();
    }

    private void initCamera() {
        scanContainer = (RelativeLayout) findViewById(R.id.capture_container);
        scanCropView = (RelativeLayout) findViewById(R.id.capture_crop_view);
        scanLine = (ImageView) findViewById(R.id.capture_scan_line);
        mFlash = (ImageView) findViewById(R.id.capture_flash);
        mFlash.setOnClickListener(this);

        inactivityTimer = new InactivityTimer(this);
        beepManager = new BeepManager(this);

        TranslateAnimation animation = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.9f);
        animation.setDuration(4500);
        animation.setRepeatCount(-1);
        animation.setRepeatMode(Animation.RESTART);
        scanLine.startAnimation(animation);

        cameraManager = new CameraManager(getApplication());
    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        if (cameraManager == null)
            return;

        if (surfaceHolder == null) {
            throw new IllegalStateException("No SurfaceHolder provided");
        }
        if (cameraManager.isOpen()) {
            Log.w(TAG,
                    "initCamera() while already open -- late SurfaceView callback?");
            return;
        }
        try {
            cameraManager.openDriver(surfaceHolder);
            // Creating the handler starts the preview, which can also throw a
            // RuntimeException.
            if (handler == null) {
                handler = new CaptureActivityHandler(this, cameraManager,
                        DecodeThread.ALL_MODE);
            }

            initCrop();
        } catch (IOException | RuntimeException e) {
            Log.w(TAG, "Unexpected error initializing camera", e);
            displayFrameworkBugMessageAndExit();
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (holder == null) {
            Log.e(TAG, "-- WARNING -- surfaceCreated() gave us a null surface!");
        }
        isHasSurface = true;
        initCamera(holder);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isHasSurface = false;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // Doing
    }

    public Handler getHandler() {
        return handler;
    }

    public CameraManager getCameraManager() {
        return cameraManager;
    }

    public Rect getCropRect() {
        return mCropRect;
    }

    /**
     * 初始化截取的矩形区域
     */
    @SuppressWarnings("SuspiciousNameCombination")
    private void initCrop() {
        int cameraWidth = cameraManager.getCameraResolution().y;
        int cameraHeight = cameraManager.getCameraResolution().x;

        /** 获取布局中扫描框的位置信息 */
        int[] location = new int[2];
        scanCropView.getLocationInWindow(location);

        int cropLeft = location[0];
        int cropTop = location[1] - getStatusBarHeight();

        int cropWidth = scanCropView.getWidth();
        int cropHeight = scanCropView.getHeight();

        /** 获取布局容器的宽高 */
        int containerWidth = scanContainer.getWidth();
        int containerHeight = scanContainer.getHeight();

        /** 计算最终截取的矩形的左上角顶点x坐标 */
        int x = cropLeft * cameraWidth / containerWidth;
        /** 计算最终截取的矩形的左上角顶点y坐标 */
        int y = cropTop * cameraHeight / containerHeight;

        /** 计算最终截取的矩形的宽度 */
        int width = cropWidth * cameraWidth / containerWidth;
        /** 计算最终截取的矩形的高度 */
        int height = cropHeight * cameraHeight / containerHeight;

        /** 生成最终的截取的矩形 */
        mCropRect = new Rect(x, y, width + x, height + y);
    }

    private int getStatusBarHeight() {
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            return getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.capture_flash) {
            light();
        }
    }


    /**
     * 测试扫描本地图片
     *
     * @param v
     */
    public void decode(View v) {

        QRCodeDecoder mDecoder = new QRCodeDecoder.Builder().build();

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.qr_image);

        String urlStr = mDecoder.decode(bitmap);

        openExternalBrowser(CaptureActivity.this, urlStr);

        System.out.println("hhh-------" + urlStr);

    }

    /**
     * 生成二维码
     */
    public void test() {

        String content = "";

        final Bitmap centerImage = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
        Bitmap bitmap = new QRCodeEncoder.Builder()
                .width(200)
                .height(200)
                .paddingPx(0)
                .marginPt(3)
                .centerImage(centerImage)
                .build()
                .encode(content);

    }


    /**
     * 开关闪光灯
     */
    private void light() {
        try {
            if (mIsLight) {
                // 关闪光灯
                cameraManager.offLight();
                mFlash.setBackgroundResource(R.drawable.flash_default);
                mIsLight = false;
            } else {
                // 开闪光灯
                cameraManager.openLight();
                mFlash.setBackgroundResource(R.drawable.flash_open);
                mIsLight = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterPermissionGranted(CAMERA_PERM)
    private void requestCamera() {
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.VIBRATE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            initCamera();
        } else {
            // Request one permission
            EasyPermissions.requestPermissions(this, "请求获取相机权限", CAMERA_PERM, perms);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        if (perms != null && perms.size() == 2) {
            initCamera();
        } else {
            displayFrameworkBugMessageAndExit();
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        displayFrameworkBugMessageAndExit();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // EasyPermissions handles the request result.
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    private void displayFrameworkBugMessageAndExit() {
        Toast.makeText(this, "相机无法完成初始化,请正确授权。", Toast.LENGTH_LONG).show();
    }

    public void restartPreviewAfterDelay(long delayMS) {
        if (handler != null) {
            handler.sendEmptyMessageDelayed(R.id.restart_preview, delayMS);
        }
    }


    /**
     * A valid barcode has been found, so give an indication of success and show
     * the results.
     *
     * @param rawResult The contents of the barcode.
     * @param bundle    The extras
     */
    public void handleDecode(final Result rawResult, Bundle bundle) {
        inactivityTimer.onActivity();
        beepManager.playBeepSoundAndVibrate();

        // 通过这种方式可以获取到扫描的图片
        bundle.putInt("width", mCropRect.width());
        bundle.putInt("height", mCropRect.height());
        bundle.putString("result", rawResult.getText());

//        Intent intent = new Intent(CaptureActivity.this, ResultActivity.class);
//        intent.putExtras(bundle);
//        startActivity(intent);

        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                handleText(rawResult.getText());
            }
        }, 800);
    }

    private void handleText(String text) {

        if (StringUtils.isUrl(text)) {
            showUrlOption(text);
        } else {
            handleOtherText(text);
        }
    }

    private void showUrlOption(final String url) {
//        if (url.contains("scan_login")) {
//            showConfirmLogin(url);
//            return;
//        }

        DialogHelper.getConfirmDialog(this, "可能存在风险，是否打开链接?\n" + url, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                openExternalBrowser(CaptureActivity.this, url);
                finish();
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        }).show();
    }

    /**
     * 打开外置的浏览器
     *
     * @param context
     * @param url
     */
    public static void openExternalBrowser(Context context, String url) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(Intent.createChooser(intent, "选择打开的应用"));
    }

    private void handleScanLogin(final String url) {
//        OSChinaApi.scanQrCodeLogin(url, new AsyncHttpResponseHandler() {
//
//            @Override
//            public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
//                ResultBean result = XmlUtils.toBean(ResultBean.class, arg2);
//                if (result != null && result.getResult() != null
//                        && result.getResult().OK()) {
//                    AppContext.showToast(result.getResult().getErrorMessage());
//                    finish();
//                } else {
//                    handler.sendEmptyMessage(R.id.restart_preview);
//                    AppContext.showToast(result != null
//                            && result.getResult() != null ? result.getResult()
//                            .getErrorMessage() : "登陆失败");
//                }
//            }
//
//            @Override
//            public void onFailure(int arg0, Header[] arg1, byte[] arg2,
//                                  Throwable arg3) {
//                handler.sendEmptyMessage(R.id.restart_preview);
//                if (arg2 != null) {
//                    AppContext.showToast(new String(arg2));
//                } else {
//                    AppContext.showToast("网页登陆失败");
//                }
//            }
//
//            @Override
//            public void onStart() {
//                super.onStart();
//                showWaitDialog("已扫描，正在登陆...");
//            }
//
//            @Override
//            public void onFinish() {
//                super.onFinish();
//                hideWaitDialog();
//            }
//        });
    }

    private void handleOtherText(final String text) {
        // 判断是否符合基本的json格式
//        if (!text.matches("^\\{.*")) {
//            showCopyTextOption(text);
//        } else {
//            try {
//                BarCode barcode = BarCode.parse(text);
//                int type = barcode.getType();
//                switch (type) {
//                    case BarCode.SIGN_IN:// 签到
//                        handleSignIn(barcode);
//                        break;
//                    default:
//                        break;
//                }
//            } catch (AppException e) {
//                showCopyTextOption(text);
//            }
//        }
    }

//    private void handleSignIn(BarCode barCode) {
//        if (barCode.isRequireLogin() && !AccountHelper.isLogin()) {
//            showLogin();
//            return;
//        }
//        showWaitDialog("正在签到...");
//        AsyncHttpResponseHandler handler = new AsyncHttpResponseHandler() {
//            @Override
//            public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
//                try {
//                    SingInResult res = SingInResult.parse(new String(arg2));
//                    if (res.isOk()) {
//                        DialogHelper.getMessageDialog(CaptureActivity.this, res.getMessage()).show();
//                    } else {
//                        DialogHelper.getMessageDialog(CaptureActivity.this, res.getErrorMes()).show();
//                    }
//                } catch (AppException e) {
//                    e.printStackTrace();
//                    onFailure(arg0, arg1, arg2, e);
//                }
//            }
//
//            @Override
//            public void onFailure(int arg0, Header[] arg1, byte[] arg2,
//                                  Throwable arg3) {
//                hideWaitDialog();
//                DialogHelper.getMessageDialog(CaptureActivity.this, arg3.getMessage()).show();
//            }
//
//            @Override
//            public void onFinish() {
//                super.onFinish();
//                hideWaitDialog();
//            }
//        };
//        OSChinaApi.signin(barCode.getUrl(), handler);
//    }
//
//    private void showLogin() {
//        DialogHelper.getConfirmDialog(this, "请先登录，再进行", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                UIHelper.showLoginActivity(CaptureActivity.this);
//            }
//        }).show();
//    }
//
//    private void showCopyTextOption(final String text) {
//        DialogHelper.getConfirmDialog(this, text, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                ClipboardManager cbm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
//                cbm.setText(text);
//                AppContext.showToast("复制成功");
//                finish();
//            }
//        }, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                finish();
//            }
//        }).show();
//    }


    @Override
    protected void onResume() {
        if (mScanPreview != null) {
            handler = null;
            if (isHasSurface) {
                // The activity was paused but not stopped, so the surface still
                // exists. Therefore
                // surfaceCreated() won't be called, so init the camera here.
                initCamera(mScanPreview.getHolder());
            }
        }
        if (inactivityTimer != null) {
            inactivityTimer.onResume();
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        if (inactivityTimer != null) {
            inactivityTimer.onPause();
        }
        if (beepManager != null) {
            beepManager.close();
        }
        if (cameraManager != null) {
            cameraManager.closeDriver();
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (inactivityTimer != null) {
            inactivityTimer.shutdown();
        }
        if (mScanPreview != null) {
            mScanPreview.getHolder().removeCallback(this);
        }
        super.onDestroy();
    }

}
