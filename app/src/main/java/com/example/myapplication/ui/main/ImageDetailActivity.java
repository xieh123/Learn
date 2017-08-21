package com.example.myapplication.ui.main;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.example.myapplication.R;

/**
 * Created by Administrator on 2016/11/24 0024.
 */
public class ImageDetailActivity extends AppCompatActivity {

    ImageView mImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);

        initView();
    }

    public void initView() {

        mImageView = (ImageView) findViewById(R.id.image_detail_iv);

        String filePath = getIntent().getStringExtra("imagePath");

        Bitmap bitmap = BitmapFactory.decodeFile(filePath);

        mImageView.setImageBitmap(bitmap);

    }
}
