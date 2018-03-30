package com.example.myapplication.ui.tab3;

import android.content.Context;
import android.content.Intent;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;
import com.example.myapplication.util.ScreenShot;

/**
 * Created by xieH on 2018/1/31 0031.
 */
public class MediaProjectionActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 1;

    private Button mButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_projection);

        initView();
    }

    private void initView() {
        mButton = (Button) findViewById(R.id.button);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("hhh", "start");
                requestCapturePermission();
            }
        });
    }

    private void requestCapturePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            MediaProjectionManager mediaProjectionManager = (MediaProjectionManager) getSystemService(Context.MEDIA_PROJECTION_SERVICE);
            startActivityForResult(mediaProjectionManager.createScreenCaptureIntent(), REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_CODE:
                if (data != null) {
                    if (RESULT_OK == resultCode) {
                        ScreenShot.setUpMediaProjection(MediaProjectionActivity.this, data);
                        ScreenShot.getWH(MediaProjectionActivity.this);
                        ScreenShot.createImageReader();
                        ScreenShot.beginScreenShot(MediaProjectionActivity.this, data);
                    }
                }
                break;
            default:
                break;
        }
    }

}
