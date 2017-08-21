package com.example.myapplication.ui.tab2;

import android.animation.ObjectAnimator;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.example.myapplication.R;

/**
 * Created by xieH on 2017/6/15 0015.
 */
public class ViewActivity extends AppCompatActivity {

    private ImageView mLightBeamIv;

    private ObjectAnimator radarScanAnim;   // 扫描动画

    private int width;
    private int height;

    private ImageView mImageView;

    ////////////

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab2_view);

        initView();
    }

    private void initView() {
        mLightBeamIv = (ImageView) findViewById(R.id.light_beam_iv);

        mImageView = (ImageView) findViewById(R.id.imageView);
    }


    public void transparentActivity(View v) {
        Intent intent = new Intent(this, TransparentActivity.class);
        startActivity(intent);
    }

//    @Override
//    public void onWindowFocusChanged(boolean hasFocus) {
//        super.onWindowFocusChanged(hasFocus);
//
//        if (height == 0 || width == 0) {
//            // 获取屏幕长、宽
//            width = ScreenUtils.getScreenWidth(this);
//            height = ScreenUtils.getScreenHeight(this);
//
//            // 根据屏幕长、宽计算扫描圆的直径
//            int diameter = (int) Math.sqrt(Math.pow(height, 2) + Math.pow(width, 2));
//
//            // 修改光束的大小，使光束可以扫描到整个屏幕
//            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(diameter, diameter);
//            mLightBeamIv.setLayoutParams(layoutParams);
//
//            // 将扫描光束的中心移至屏幕内容中心
//            int offsetX = (width - diameter) / 2;
//            int offsetY = (height - diameter) / 2 + ScreenUtils.getStatusHeight(this) / 2;
//            mLightBeamIv.setX(offsetX);
//            mLightBeamIv.setY(offsetY);
//        }
//    }

    @Override
    protected void onResume() {
        super.onResume();
        startScan();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopScan();
    }

    /**
     * 开始扫描
     */
    private void startScan() {
        radarScanAnim = ObjectAnimator.ofFloat(mLightBeamIv, "rotation", 0f, 360f);
        radarScanAnim.setDuration(5000);    // 5秒扫描一圈
        radarScanAnim.setInterpolator(new LinearInterpolator());
        radarScanAnim.setRepeatCount(ObjectAnimator.INFINITE);   // 循环扫描

        mLightBeamIv.setVisibility(View.VISIBLE);
        radarScanAnim.start();
    }

    /**
     * 停止扫描
     */
    private void stopScan() {
        mLightBeamIv.setVisibility(View.GONE);
        radarScanAnim.end();
    }


    /**
     * 跳转到微信的扫一扫界面
     *
     * @param v
     */
    public void toWeChatScanDirect(View v) {
        try {
            Intent intent = new Intent();
            intent.setComponent(new ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI"));
            intent.putExtra("LauncherUI.From.Scaner.Shortcut", true);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.setAction("android.intent.action.VIEW");
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
