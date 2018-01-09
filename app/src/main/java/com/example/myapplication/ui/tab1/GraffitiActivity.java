package com.example.myapplication.ui.tab1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.myapplication.R;
import com.example.myapplication.util.ScreenUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by xieH on 2017/4/14 0014.
 */
public class GraffitiActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graffiti);

        initView();
    }

    public void initView() {


    }

    public void graffiti(View v) {
        Bitmap bitmap = ScreenUtils.snapShotWithStatusBar(this);

        String path = Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator
                + "HHH"
                + File.separator
                + "images"
                + File.separator
                + "shot.png";

        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(path);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        Intent intent = new Intent(this, Graffiti11Activity.class);
        intent.putExtra("path", path);
        startActivity(intent);
    }
}
