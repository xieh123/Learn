package com.example.myapplication.ui.tab1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;
import com.example.myapplication.adapter.SwipeLayoutRecyclerViewAdapter;
import com.example.myapplication.model.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xieH on 2017/3/8 0008.
 */
public class SwipeLayoutActivity extends AppCompatActivity {

    private Button mEditBtn;

    private boolean isEdit;

    private RecyclerView mRecyclerView;
    private SwipeLayoutRecyclerViewAdapter mSwipeLayoutRecyclerViewAdapter;
    private List<Item> itemList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_layout);

        initView();
    }

    public void initView() {
        mEditBtn = (Button) findViewById(R.id.swipe_layout_bt);

        mEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isEdit) {
                    mEditBtn.setText("编辑");
                    isEdit = false;
                    mSwipeLayoutRecyclerViewAdapter.closeAll();
                } else {
                    mEditBtn.setText("完成");
                    isEdit = true;
                    mSwipeLayoutRecyclerViewAdapter.openLeftAll();
                }
                mSwipeLayoutRecyclerViewAdapter.setEdit(isEdit);
            }
        });


        mRecyclerView = (RecyclerView) findViewById(R.id.swipe_layout_rv);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        setData();

        mSwipeLayoutRecyclerViewAdapter = new SwipeLayoutRecyclerViewAdapter(this, itemList);
        mRecyclerView.setAdapter(mSwipeLayoutRecyclerViewAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }


    public void setData() {
        for (int i = 0; i < 20; i++) {
            Item item = new Item();
            itemList.add(item);
        }
    }
}
