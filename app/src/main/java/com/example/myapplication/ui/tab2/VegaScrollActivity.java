package com.example.myapplication.ui.tab2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.Item;
import com.example.myapplication.widget.VegaLayoutManager;
import com.example.myapplication.widget.loadmoreadapter.BaseNormalAdapter;
import com.example.myapplication.widget.loadmoreadapter.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xieH on 2017/9/25 0025.
 */
public class VegaScrollActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private BaseNormalAdapter<Item> mItemBaseAdapter;
    private List<Item> mItemList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vega_scroll);

        initView();

        setData();
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new VegaLayoutManager());
        initAdapter();
        mRecyclerView.setAdapter(mItemBaseAdapter);
    }

    private void initAdapter() {
        mItemBaseAdapter = new BaseNormalAdapter<Item>(this, mItemList, false) {
            @Override
            protected void convert(ViewHolder holder, Item item, int position) {

            }

            @Override
            protected int getItemLayoutId() {
                return R.layout.item_recycler;
            }
        };
    }

    private void setData() {
        for (int i = 0; i < 20; i++) {
            Item item = new Item();
            mItemList.add(item);
        }
        mItemBaseAdapter.refresh(mItemList);
    }
}
