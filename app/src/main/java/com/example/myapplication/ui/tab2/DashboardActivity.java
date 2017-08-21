package com.example.myapplication.ui.tab2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.widget.DashboardView;
import com.example.myapplication.widget.InstrumentView;

import java.util.Random;

/**
 * Created by xieH on 2017/6/22 0022.
 */
public class DashboardActivity extends AppCompatActivity {

    private DashboardView mDashboardView;


    private InstrumentView mInstrumentView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        initView();
    }


    public void initView() {
        mDashboardView = (DashboardView) findViewById(R.id.dashboardView);

        mDashboardView.setCreditValueWithAnim(new Random().nextInt(950 - 350) + 350);


        mInstrumentView = (InstrumentView) findViewById(R.id.instrumentView);
        mInstrumentView.setData(200, 500, true);
    }
}
