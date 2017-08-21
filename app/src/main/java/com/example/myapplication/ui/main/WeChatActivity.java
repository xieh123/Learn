package com.example.myapplication.ui.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xieH on 2016/12/5 0005.
 */
public class WeChatActivity extends AppCompatActivity {


    RecyclerView mRecyclerView;

    List<Message> chatList = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wechat);


    }

    public void initView() {


    }
}
