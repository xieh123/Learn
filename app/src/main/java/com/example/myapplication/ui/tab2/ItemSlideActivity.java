package com.example.myapplication.ui.tab2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;
import com.example.myapplication.adapter.SlideRecyclerAdapter;
import com.example.myapplication.model.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xieH on 2017/10/17 0017.
 */
public class ItemSlideActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mEditBtn;
    private boolean isEdit = false;

    private RecyclerView mRecyclerView;
    private SlideRecyclerAdapter slideRecyclerAdapter;
    private List<Item> mItemList = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_slide);

        initView();

        setData();
    }

    protected void initView() {
        mEditBtn = (Button) findViewById(R.id.slide_edit_bt);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        slideRecyclerAdapter = new SlideRecyclerAdapter(this, mItemList);
        mRecyclerView.setAdapter(slideRecyclerAdapter);

        mEditBtn.setOnClickListener(this);
    }

    private void setData() {
        for (int i = 0; i < 10; i++) {
            Item item = new Item();
            mItemList.add(item);
        }

        slideRecyclerAdapter.refresh(mItemList);
    }

    @Override
    public void onClick(View v) {
        if (isEdit) {
            mEditBtn.setText("编辑");
            isEdit = false;
            slideRecyclerAdapter.closeItemsAnimation();
        } else {
            mEditBtn.setText("完成");
            isEdit = true;
            slideRecyclerAdapter.openItemsAnimation();
        }
    }
}
