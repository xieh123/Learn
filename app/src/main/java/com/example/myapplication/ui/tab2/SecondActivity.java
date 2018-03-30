package com.example.myapplication.ui.tab2;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;

/**
 * Created by xieH on 2017/6/25 0025.
 */
public class SecondActivity extends AppCompatActivity {

    public static final String TAB_1 = "tab_1";
    public static final String TAB_2 = "tab_2";
    public static final String TAB_3 = "tab_3";

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
            ViewCompat.setTransitionName(mImageView, TAB_1);
            ViewCompat.setTransitionName(mTextView, TAB_2);

            ViewCompat.setTransitionName(mImageView11, TAB_3);

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
