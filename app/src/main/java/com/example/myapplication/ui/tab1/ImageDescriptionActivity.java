package com.example.myapplication.ui.tab1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.widget.imagedetail.ImageDescriptionView;

/**
 * Created by xieH on 2017/2/21 0021.
 */
public class ImageDescriptionActivity extends AppCompatActivity {

    private ImageDescriptionView mImageDescriptionView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_description);

        initView();
    }

    public void initView() {

        mImageDescriptionView = (ImageDescriptionView) findViewById(R.id.image_description_ImageDescriptionView);

        mImageDescriptionView.setText(getString(R.string.description));

    }
}
