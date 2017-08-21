package com.example.myapplication.ui.tab1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.widget.SwitchButton;

/**
 * Created by xieH on 2017/3/16 0016.
 */
public class ViewActivity extends AppCompatActivity {

    private SwitchButton mSwitchButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        initView();
    }

    public void initView(){

        mSwitchButton = (SwitchButton) findViewById(R.id.view_switchButton);

//        mSwitchButton.setSelected(true);

    }
}
