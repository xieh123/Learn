package com.example.myapplication.ui.tab2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.widget.MagnifierView;

/**
 * Created by xieH on 2017/6/22 0022.
 */
public class MagnifierActivity extends AppCompatActivity {

    private MagnifierView mMagnifierView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magnifier);

        initView();
    }

    private void initView() {
        mMagnifierView = (MagnifierView) findViewById(R.id.magnifierView);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.welcome_thirdly);

//        mMagnifierView.setBitmap(bitmap);

    }
}
