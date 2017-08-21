package com.example.myapplication.ui.tab2;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;

/**
 * Created by xieH on 2017/6/25 0025.
 */
public class SecondActivity extends AppCompatActivity {

    private ImageView mImageView, mImageView11;
    private TextView mTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        initView();
    }

    private void initView() {
        mImageView = (ImageView) findViewById(R.id.imageView);
        mTextView = (TextView) findViewById(R.id.textView);

        mImageView11 = (ImageView) findViewById(R.id.imageView11);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mImageView.setTransitionName("tab_1");
            mTextView.setTransitionName("tab_2");

            mImageView11.setTransitionName("tab_3");

            postponeEnterTransition();

            // 最好在加载完毕后再调用
            startPostponedEnterTransition();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.finishAfterTransition();
        }
    }
}
