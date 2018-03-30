package com.example.myapplication.ui.tab2;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.transition.Slide;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;

/**
 * Created by xieH on 2018/1/24 0024.
 */
public class MaterialDesign11Activity extends AppCompatActivity {

    public static final String VIEW_IMAGE = "view_image";

    public static final String VIEW_TEXT = "view_text";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_design11);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // 设置转场动画
            getWindow().setEnterTransition(new Explode().setDuration(1000));
            getWindow().setExitTransition(new Slide().setDuration(1000));

//            getWindow().setEnterTransition(new Explode().setDuration(2000));
//            getWindow().setExitTransition(new Explode().setDuration(2000));
//
//            getWindow().setEnterTransition(new Slide().setDuration(2000));
//            getWindow().setExitTransition(new Slide().setDuration(2000));
//
//            getWindow().setEnterTransition(new Fade().setDuration(2000));
//            getWindow().setExitTransition(new Fade().setDuration(2000));
        }


        initView();
    }

    private void initView() {
        // 获取本界面中两个对应的布局控件
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        TextView textView = (TextView) findViewById(R.id.textView);

        // 设置被共享的控件，由上一个Activity传入，通过自定义常量标识获取
        ViewCompat.setTransitionName(imageView, VIEW_IMAGE);
        ViewCompat.setTransitionName(textView, VIEW_TEXT);

    }
}
