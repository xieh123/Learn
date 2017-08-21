package com.example.myapplication.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.myapplication.R;
import com.example.myapplication.utils.QRCodeUtil;

/**
 * Created by xieH on 2017/1/4 0004.
 */
public class QRCodeActivity extends AppCompatActivity {

    private ImageView mQRCodeIv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code);

        initView();
    }


    public void initView() {

        mQRCodeIv = (ImageView) findViewById(R.id.qr_code_iv);

        String url = "https://fir.im/cloudreader";
        QRCodeUtil.showThreadImage(this, url, mQRCodeIv, R.mipmap.ic_launcher);

    }

    public void share(View v) {
        share(this, "测试分享");
    }

    public static void share(Context context, String extraText) {
        Intent intent = new Intent(Intent.ACTION_SEND);

        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
        intent.putExtra(Intent.EXTRA_TEXT, extraText);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(Intent.createChooser(intent, "分享"));
    }
}
