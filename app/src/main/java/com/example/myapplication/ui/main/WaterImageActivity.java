package com.example.myapplication.ui.main;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.myapplication.R;
import com.example.myapplication.utils.ImageUtils;

/**
 * Created by Administrator on 2016/11/25 0025.
 */
public class WaterImageActivity extends AppCompatActivity {

    private ImageView mImageView;


    Bitmap waterBitmap, textBitmap;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_image);

        initView();
    }

    public void initView() {

        mImageView = (ImageView) findViewById(R.id.water_image_iv);

        waterBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.welcome_thirdly);

        mImageView.setImageBitmap(waterBitmap);


    }

    public void add(View v) {


        EditDialogFragment editDialogFragment = new EditDialogFragment();

        editDialogFragment.setOnAddClickListener(new EditDialogFragment.OnAddClickListener() {
            @Override
            public void add(String inputText, int textSize, int color) {

                textBitmap = ImageUtils.drawTextToCenter(WaterImageActivity.this, waterBitmap, inputText, textSize, color);

                mImageView.setImageBitmap(textBitmap);
            }
        });

        editDialogFragment.show(getSupportFragmentManager(), null);


    }
}
