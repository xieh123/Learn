package com.example.myapplication.ui.tab1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.example.myapplication.R;

/**
 * Created by xieH on 2017/4/24 0024.
 */
public class View22Activity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view22);

        // 设置屏幕为安全模式，使用户无法进行截屏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);

        initView();
    }

    public void initView() {
//        setBackgroundAlpha(0.5f);
//        setBackgroundAlpha(1f);
    }


    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.alpha = bgAlpha; // 0.0 - 1.0
        getWindow().setAttributes(lp);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }
}
