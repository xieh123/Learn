package com.example.myapplication.ui.tab1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.TextView;

import com.example.myapplication.R;
import com.google.android.flexbox.FlexboxLayout;

/**
 * Created by xieH on 2017/3/24 0024.
 */
public class FlexboxLayoutActivity extends AppCompatActivity {

    private FlexboxLayout mFlexboxLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flexbox_layout);

        initView();
    }

    public void initView() {
        mFlexboxLayout = (FlexboxLayout) findViewById(R.id.flexbox_layout);

        for (int i = 0; i < 10; i++) {
            // 通过代码向FlexboxLayout添加View
            TextView textView = new TextView(this);
            textView.setBackground(ContextCompat.getDrawable(this, R.drawable.border_btn));
            textView.setText("Test  Label");
            textView.setGravity(Gravity.CENTER);
            textView.setPadding(30, 20, 30, 20);
            textView.setTextColor(ContextCompat.getColor(this, R.color.text_light_color));
            mFlexboxLayout.addView(textView);
        }

        // 通过FlexboxLayout.LayoutParams 设置子元素支持的属性
//        ViewGroup.LayoutParams params = textView.getLayoutParams();
//        if (params instanceof FlexboxLayout.LayoutParams) {
//            FlexboxLayout.LayoutParams layoutParams = (FlexboxLayout.LayoutParams) params;
//            layoutParams.setFlexBasisPercent(0.5f);
//        }
    }
}
