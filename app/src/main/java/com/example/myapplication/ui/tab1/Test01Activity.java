package com.example.myapplication.ui.tab1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.myapplication.R;
import com.example.myapplication.widget.ColorPickerView;

/**
 * Created by xieH on 2017/4/11 0011.
 */
public class Test01Activity extends AppCompatActivity {

    private LinearLayout linearLayout;

    private ColorPickerView colorPickerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test01);

        initView();
    }

    public void initView() {
        linearLayout = (LinearLayout) findViewById(R.id.test_01_ll);

        ImageView imageView = new ImageView(this);
        imageView.setAdjustViewBounds(true);
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        imageView.setMaxHeight(200);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        imageView.setImageResource(R.drawable.welcome_thirdly);

        linearLayout.addView(imageView);


        colorPickerView = (ColorPickerView) findViewById(R.id.test_01_colorPickerView);
        colorPickerView.setColorPickerListener(new ColorPickerView.ColorPickerListener() {
            @Override
            public void onColorChanging(int color) {
//                if (colorDrawable == null) {
//                    colorDrawable = new ColorDrawable(color);
//                    iv_color.setImageDrawable(colorDrawable);
//                } else {
//                    colorDrawable.setColor(color);
//                }
            }

            @Override
            public void onColorPicked(int color) {

            }
        });



    }

    public void finish(View v) {
        setResult(RESULT_OK);
        this.finish();
    }
}
