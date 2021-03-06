package com.example.myapplication;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.dtr.zxing.activity.CaptureActivity;
import com.example.myapplication.base.BaseActivity;
import com.example.myapplication.listener.LoginListener;
import com.example.myapplication.ui.main.AnimatorActivity;
import com.example.myapplication.ui.main.CardViewActivity;
import com.example.myapplication.ui.main.ChangeThemeActivity;
import com.example.myapplication.ui.main.CycleRecyclerActivity;
import com.example.myapplication.ui.main.DownloadNotificationActivity;
import com.example.myapplication.ui.main.GalleryActivity;
import com.example.myapplication.ui.main.GlideActivity;
import com.example.myapplication.ui.main.ImageActivity;
import com.example.myapplication.ui.main.LoadingActivity;
import com.example.myapplication.ui.main.LoginActivity;
import com.example.myapplication.ui.main.OrderDetailsActivity;
import com.example.myapplication.ui.main.PathAnimActivity;
import com.example.myapplication.ui.main.QRCodeActivity;
import com.example.myapplication.ui.main.RecordActivity;
import com.example.myapplication.ui.main.SVGActivity;
import com.example.myapplication.ui.main.SmallVideoActivity;
import com.example.myapplication.ui.main.SurfaceViewActivity;
import com.example.myapplication.ui.main.UserInfoActivity;
import com.example.myapplication.ui.main.WaterImageActivity;
import com.example.myapplication.ui.main.WebViewActivity;
import com.example.myapplication.ui.rxbus.RxBusActivity;
import com.xieh.imagepicker.SelectImageActivity;
import com.xieh.imagepicker.config.SelectOptions;

import java.util.ArrayList;

/**
 * Created by xieH on 2016/11/24 0024.
 */
public class MainActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    @Override
    protected void initView() {

    }

    /**
     * launchMode等于singleTask ，再次启动此 Activity 时调用该方法
     *
     * @param intent
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        if (intent.getBooleanExtra("isExit", false)) {
            // 退出应用
            finish();
        }
    }

    public void record(View v) {
        Intent intent = new Intent(this, RecordActivity.class);
        startActivity(intent);
    }

    public void userInfo(View v) {
        Intent intent = new Intent(this, UserInfoActivity.class);
        startActivity(intent);
    }

    public void image(View v) {
        Intent intent = new Intent(this, ImageActivity.class);
        startActivity(intent);
    }

    public void waterImage(View v) {
        Intent intent = new Intent(this, WaterImageActivity.class);
        startActivity(intent);
    }

    public void svgImage(View v) {
        Intent intent = new Intent(this, SVGActivity.class);
        startActivity(intent);
    }

    public void glide(View v) {
        Intent intent = new Intent(this, GlideActivity.class);
        startActivity(intent);
    }

    public void animator(View v) {
        Intent intent = new Intent(this, AnimatorActivity.class);
        startActivity(intent);
    }

    public void cycle(View v) {
        Intent intent = new Intent(this, CycleRecyclerActivity.class);
        startActivity(intent);
    }

    public void change(View v) {
        Intent intent = new Intent(this, ChangeThemeActivity.class);
        startActivity(intent);
    }

    public void loading(View v) {
        Intent intent = new Intent(this, LoadingActivity.class);
        startActivity(intent);
    }

    public void video(View v) {
//        Intent intent = new Intent(this, SimpleActivity.class);
//        startActivity(intent);
    }

    public void smallVideo(View v) {
        Intent intent = new Intent(this, SmallVideoActivity.class);
        startActivity(intent);
    }

    public void surfaceView(View v) {
        Intent intent = new Intent(this, SurfaceViewActivity.class);
        startActivity(intent);
    }

    public void webView(View v) {
        Intent intent = new Intent(this, WebViewActivity.class);
        startActivity(intent);
    }

    public void qrCode(View v) {
        Intent intent = new Intent(this, QRCodeActivity.class);
        startActivity(intent);
    }

    public void pathAnim(View v) {
        Intent intent = new Intent(this, PathAnimActivity.class);
        startActivity(intent);
    }

    public void cardView(View v) {
        Intent intent = new Intent(this, CardViewActivity.class);
        startActivity(intent);
    }

    public void imagePicker(View v) {
//        Intent intent = new Intent(this, SelectImageActivity.class);
//        startActivity(intent);

        SelectImageActivity.show(this, new SelectOptions.Builder()
                .setHasCam(true)
                .setCrop(300, 300)
                .setSelectCount(5)
//                .setSelectedImages(new String[2])
                .setCallback(new SelectOptions.Callback() {
                    @Override
                    public void doSelected(ArrayList<String> images) {
                        //  set(images);
                    }
                }).build());
    }

    public void rxBus(View v) {
        Intent intent = new Intent(this, RxBusActivity.class);
        startActivity(intent);
    }

    public void qrCodeTest(View v) {
        Intent intent = new Intent(this, CaptureActivity.class);
        startActivity(intent);
    }

    public void download(View v) {
        Intent intent = new Intent(this, DownloadNotificationActivity.class);
        startActivity(intent);
    }

    public void gallery(View v) {
        Intent intent = new Intent(this, GalleryActivity.class);
        startActivity(intent);
    }

    private boolean isLogin = false;

    public void orderDetails(View v) {
        if (!isLogin) {
            LoginActivity.startActivity(this, new LoginListener() {
                @Override
                public void onLogin() {
                    Intent intent = new Intent(MainActivity.this, OrderDetailsActivity.class);
                    intent.putExtra("orderId", 1234);
                    startActivity(intent);
                }
            });
        } else {
            Intent intent = new Intent(this, OrderDetailsActivity.class);
            startActivity(intent);
        }
    }

    public void login(View v) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void tab1(View v) {
        Intent intent = new Intent(this, Tab1Activity.class);
        startActivity(intent);
    }

    public void tab2(View v) {
        Intent intent = new Intent(this, Tab2Activity.class);
        startActivity(intent);
    }

    public void tab3(View v) {
        Intent intent = new Intent(this, Tab3Activity.class);
        startActivity(intent);
    }

    public void tab4(View v) {
        Intent intent = new Intent(this, Tab4Activity.class);
        startActivity(intent);
    }


//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//
//            // 返回桌面，不退出程序
//            moveTaskToBack(false);  // If false then this only works if the activity is the root of a task; if true it will work for any activity in a task.
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }


    @Override
    protected void onPause() {
        super.onPause();

        System.out.println("a--------onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();

        System.out.println("a--------onResume");
    }

    @Override
    protected void onStop() {
        super.onStop();

        System.out.println("a--------onStop");
    }

    private long mBackPressedTime;
    private Toast mBackToast;

    @Override
    public void onBackPressed() {
        long mCurTime = SystemClock.uptimeMillis();
        if ((mCurTime - mBackPressedTime) < (2 * 1000)) {
            if (mBackToast != null) {
                mBackToast.cancel();
            }
            super.onBackPressed();
        } else {
            mBackPressedTime = mCurTime;

            if (mBackToast == null) {
                mBackToast = Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT);
            }
            mBackToast.show();
        }
    }

}
