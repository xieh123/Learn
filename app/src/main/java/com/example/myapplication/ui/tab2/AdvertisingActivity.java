package com.example.myapplication.ui.tab2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.Item;
import com.example.myapplication.widget.loadmoreadapter.BaseMultiAdapter;
import com.example.myapplication.widget.loadmoreadapter.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xieH on 2017/9/4 0004.
 */
public class AdvertisingActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    private BaseMultiAdapter<Item> mBaseAdapter;
    private List<Item> mItemList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertising);

        initView();

        setData();
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        initAdapter();
        mRecyclerView.setAdapter(mBaseAdapter);
    }

    private void initAdapter() {
        mBaseAdapter = new BaseMultiAdapter<Item>(this, mItemList, false) {
            @Override
            protected void convert(ViewHolder holder, Item item, int position) {
                if (getItemViewType(position) == 1) {
                    
                }
            }

            @Override
            protected int getItemLayoutId(int viewType) {
                if (viewType == 1) {
                    return R.layout.item_recycler_3;
                }
                return R.layout.item_recycler_2;
            }

            @Override
            protected int getViewType(int position, Item item) {
                if (position == 10) {
                    return 1;
                }
                return 0;
            }
        };
    }

    private void setData() {
        for (int i = 0; i < 20; i++) {
            Item item = new Item();
            mItemList.add(item);
        }

        mBaseAdapter.refresh(mItemList);
    }
}
