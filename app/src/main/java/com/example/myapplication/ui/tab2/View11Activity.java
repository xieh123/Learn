package com.example.myapplication.ui.tab2;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.myapplication.R;
import com.example.myapplication.model.Message;
import com.example.myapplication.widget.BalloonRelativeLayout;
import com.example.myapplication.widget.CircleChartView;
import com.example.myapplication.widget.PieChartView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by xieH on 2017/7/5 0005.
 */
public class View11Activity extends AppCompatActivity {

    private PieChartView mPieChartView;

    private List<Message> mMessageList = new ArrayList<>();

    ////////
    private CircleChartView mCircleChartView;


    private BalloonRelativeLayout mBalloonRelativeLayout;

    private int TIME = 100;   // 这里默认每隔100毫秒添加一个气泡
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab2_view11);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        mPieChartView = (PieChartView) findViewById(R.id.pieChartView);

        int[] colors = new int[]{Color.parseColor("#cd0000"), Color.parseColor("#fff000"), Color.parseColor("#ff00ff"), Color.parseColor("#00ffff"), Color.parseColor("#000000")};

        for (int i = 0; i < 5; i++) {
            Message mes = new Message();
            mes.setPercent(10 * (i + 1));
            mes.setContent("身高");
            mes.setColor(colors[i]);
            mMessageList.add(mes);
        }

        mPieChartView.setChartData(mMessageList);

        ///////////////////
        mCircleChartView = (CircleChartView) findViewById(R.id.circleChartView);

        List<Double> numbers = new ArrayList<>();

        numbers.add(15d);
        numbers.add(25d);
        numbers.add(35d);
        numbers.add(45d);
        numbers.add(10d);
        numbers.add(55d);

        mCircleChartView.setNumbers(numbers);

        mBalloonRelativeLayout = (BalloonRelativeLayout) findViewById(R.id.balloonRelativeLayout);

        mHandler.postDelayed(runnable, TIME);
    }

    Runnable runnable = new Runnable() {

        @Override
        public void run() {
            try {
                mBalloonRelativeLayout.addBalloon();
                mHandler.postDelayed(this, TIME);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };


    public void newActivity(View v) {
        Intent intent = new Intent(this, TestActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }
}
