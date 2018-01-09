package com.example.myapplication.ui.tab1;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.myapplication.R;
import com.example.myapplication.model.Ticket;
import com.example.myapplication.util.DensityUtils;
import com.example.myapplication.util.ScreenUtils;
import com.google.gson.Gson;

import java.util.Locale;

/**
 * Created by xieH on 2017/2/15 0015.
 */
public class CountdownActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mTextBtn;

    /**
     * 倒计时
     */
    private CountDownTimer mCountDownTimer;

    /**
     * 倒计时时长，单位为毫秒
     */
    private long mCount;

    /**
     * 时间间隔，单位为毫秒
     */
    private long mInterval;

    /**
     * 是否正在执行倒计时
     */
    private boolean isCountDownNow;

    /**
     * 默认按钮文字
     */
    private String mDefaultText;

    /////////////////////////////////

    private TextView mTest1Tv, mTest2Tv;
    private ImageView mTestIv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countdown);

        initView();
    }

    public void initView() {

        mTextBtn = (Button) findViewById(R.id.countdown_text_btn);
        mTextBtn.setOnClickListener(this);

        mCount = 60000L;
        mInterval = 1000L;

        // 初始化倒计时Timer
        if (mCountDownTimer == null) {
            mCountDownTimer = new CountDownTimer(mCount, mInterval) {
                @Override
                public void onTick(long millisUntilFinished) {
                    mTextBtn.setText(String.format(Locale.CHINA, "Please wait %d s", millisUntilFinished / 1000));
                }

                @Override
                public void onFinish() {
                    isCountDownNow = false;
                    mTextBtn.setEnabled(true);
                    mTextBtn.setClickable(true);
                    mTextBtn.setText(mDefaultText);
                }
            };
        }


        ///////////////////////////////////////

        mTest1Tv = (TextView) findViewById(R.id.countdown_test1_tv);
        mTest2Tv = (TextView) findViewById(R.id.countdown_test2_tv);

        mTestIv = (ImageView) findViewById(R.id.countdown_test_iv);

        mTest1Tv.setTextSize(20);
        mTest2Tv.setTextSize(DensityUtils.px2sp(this, 20));

    }

    @Override
    public void onClick(View view) {
        mDefaultText = mTextBtn.getText().toString();

        mTextBtn.setEnabled(false);
        mTextBtn.setClickable(false);

        // 开始倒计时
        mCountDownTimer.start();
        isCountDownNow = true;
    }

    /**
     * 移除倒计时
     */
    public void removeCountDown() {
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }

        isCountDownNow = false;
        mTextBtn.setText(mDefaultText);
        mTextBtn.setEnabled(true);
        mTextBtn.setClickable(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
    }

    /////////////////////////////

    public void getBitmap(View v) {
        Bitmap mBitmap = ScreenUtils.snapShotWithStatusBar(this);
        mTestIv.setImageBitmap(mBitmap);
    }

    public void parseJson(View v) {

        String json = getString(R.string.json);


        Gson gson = new Gson();

        Ticket ticket = gson.fromJson(json, Ticket.class);

        System.out.println("h---------" + ticket.getTrain_no());
        System.out.println("h---------" + ticket.getStart_station_name());
        System.out.println("h---------" + ticket.getEnd_station_name());

//        System.out.println("h---------" + ticket.getTrainNo());
//        System.out.println("h---------" + ticket.getStartStationName());
//        System.out.println("h---------" + ticket.getEndStationName());



        Ticket ticket1 = JSON.parseObject(json, Ticket.class);

        System.out.println("h-----1----" + ticket1.getTrain_no());
        System.out.println("h-----1----" + ticket1.getStart_station_name());
        System.out.println("h-----1----" + ticket1.getEnd_station_name());

//        System.out.println("h-----1----" + ticket1.getTrainNo());
//        System.out.println("h-----1----" + ticket1.getStartStationName());
//        System.out.println("h-----1----" + ticket1.getEndStationName());


    }
}
