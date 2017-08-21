package com.example.myapplication.ui.tab2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.utils.ActivityUtils;

/**
 * Created by xieH on 2017/6/27 0027.
 */
public class TransparentActivity extends AppCompatActivity {

    private SeekBar mSeekBar;

    private TextView mTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        WindowManager.LayoutParams windowLP = getWindow().getAttributes();
//        windowLP.alpha = 0.5f;  // 透明度
//        windowLP.dimAmount = 0.5f; // 灰度
//        getWindow().setAttributes(windowLP);

        setContentView(R.layout.activity_transparent);

        initView();
    }


    private void initView() {
        mSeekBar = (SeekBar) findViewById(R.id.seekBar);
        mTextView = (TextView) findViewById(R.id.textView);

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {

                if (progress < 10) {

                } else {
                    mTextView.setText("Activity当前亮度为：" + progress);
                    ActivityUtils.setBrightness(TransparentActivity.this, progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
}
