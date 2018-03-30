package com.example.myapplication.ui.tab2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.myapplication.R;
import com.example.myapplication.service.TestService;
import com.example.myapplication.widget.Floating11View;

/**
 * Created by xieH on 2018/1/24 0024.
 */
public class OCRActivity extends AppCompatActivity {

//    public TessBaseAPI mTess;

    private Floating11View mFloating11View;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocr);

        initView();

        //  initTessBaseData();
    }

    private void initView() {
        mFloating11View = (Floating11View) findViewById(R.id.floating11View);

        mFloating11View.loadUrl("hhhh", true);

    }

    public void startService(View v) {
        Intent intent = new Intent(this, TestService.class);
        startService(intent);
    }

    public void startService11(View v) {
        Intent intent = new Intent(this, TestService.class);
        startService(intent);
    }

    public void stopService(View v) {
        Intent intent = new Intent(this, TestService.class);
        stopService(intent);
    }


//    private void initTessBaseData() {
//
//        mTess = new TessBaseAPI();
//        String dataPath = getExternalFilesDir("/").getPath() + "/";
//
//        String language = "chi_sim";
//        File dir = new File(dataPath);
//        if (!dir.exists()) {
//            dir.mkdirs();
//        }
//
//        mTess.init(dataPath, language);
//
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_image_ocr);
//
//        mTess.setImage(bitmap);
//        String str = mTess.getUTF8Text();
//        Log.e("hhh", "hh----" + str);
//
//    }


}
