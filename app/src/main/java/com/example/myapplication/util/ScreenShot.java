package com.example.myapplication.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.Image;
import android.media.ImageReader;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Handler;
import android.support.v4.os.AsyncTaskCompat;
import android.util.DisplayMetrics;
import android.view.Surface;
import android.view.WindowManager;

/**
 * Created by xieH on 2018/1/31 0031.
 */
@TargetApi(21)
public class ScreenShot {

    private static WindowManager windowManager;
    private static int screenDensity;
    private static int screenWidth;
    private static int screenHeight;

    private static MediaProjection mediaProjection;
    private static VirtualDisplay virtualDisplay;
    private static ImageReader imageReader;
    public static Surface surface;

    public static void setUpMediaProjection(Activity activity, Intent scIntent) {
        if (scIntent == null) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            activity.startActivity(intent);
        } else {
            mediaProjection = getMediaProjectionManager(activity).getMediaProjection(Activity.RESULT_OK, scIntent);
        }
    }

    public static void getWH(Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        windowManager = (WindowManager) activity.getSystemService(Activity.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(metrics);
        screenDensity = metrics.densityDpi;
        screenWidth = metrics.widthPixels;
        screenHeight = metrics.heightPixels;
    }

    public static void createImageReader() {
        imageReader = ImageReader.newInstance(screenWidth, screenHeight, PixelFormat.RGBA_8888, 1);
    }

    public static void beginScreenShot(final Activity activity, final Intent intent) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                beginVirtual(activity, intent);
            }
        }, 0);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                beginCapture(activity, intent);
            }
        }, 150);
    }

    private static void beginVirtual(Activity activity, Intent intent) {
        if (null != mediaProjection) {
            virtualDisplay();
        } else {
            setUpMediaProjection(activity, intent);
            virtualDisplay();
        }
    }

    private static void virtualDisplay() {
        surface = imageReader.getSurface();
        virtualDisplay = mediaProjection.createVirtualDisplay("screen-mirror", screenWidth,
                screenHeight, screenDensity, DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR, surface,
                null, null);
    }

    private static MediaProjectionManager getMediaProjectionManager(Activity activity) {
        return (MediaProjectionManager) activity.getSystemService(Context.MEDIA_PROJECTION_SERVICE);
    }

    private static void beginCapture(Activity activity, Intent intent) {
        Image acquireLatestImage = null;
        try {
            acquireLatestImage = imageReader.acquireLatestImage();
        } catch (IllegalStateException e) {
            if (acquireLatestImage != null) {
                acquireLatestImage.close();
                acquireLatestImage = imageReader.acquireLatestImage();
            }
        }

        if (acquireLatestImage == null) {
            beginScreenShot(activity, intent);
        } else {
            SaveTask saveTask = new SaveTask();
            AsyncTaskCompat.executeParallel(saveTask, acquireLatestImage);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    releaseVirtual();
                    stopMediaProjection();
                }
            }, 1500);
        }
    }

    private static void releaseVirtual() {
        if (null != virtualDisplay) {
            virtualDisplay.release();
            virtualDisplay = null;
        }
    }

    private static void stopMediaProjection() {
        if (null != mediaProjection) {
            mediaProjection.stop();
            mediaProjection = null;
        }
    }

}
