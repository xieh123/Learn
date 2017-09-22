package com.example.myapplication.ui.tab2;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Chronometer;

import com.example.myapplication.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by xieH on 2017/9/8 0008.
 */
public class ChronometerActivity extends AppCompatActivity {

    private Chronometer mChronometer;

    private long mNextTime = 100L;

    private SimpleDateFormat mFormat = new SimpleDateFormat("mm:ss");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chronometer);

        initView();
    }

    private void initView() {
        mChronometer = (Chronometer) findViewById(R.id.chronometer);
        mChronometer.setFormat("计时：%s");


        mChronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
//                String time = chronometer.getText().toString();
                if (mNextTime <= 0) {
                    mChronometer.stop();
                }

                mChronometer.setText(String.format("倒计时：%s", mFormat.format(new Date(mNextTime * 1000))));
                mNextTime--;
            }
        });
    }

    public void onStart(View view) {
        mChronometer.start();
    }

    public void onStop(View view) {
        mChronometer.stop();
    }

    public void onReset(View view) {
        mChronometer.setBase(SystemClock.elapsedRealtime());
    }
}
