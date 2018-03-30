package com.example.myapplication.ui.tab3;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.myapplication.R;
import com.example.myapplication.model.Item;
import com.example.myapplication.widget.RecyclerWrapAdapter;
import com.example.myapplication.widget.loadmoreadapter.BaseNormalAdapter;
import com.example.myapplication.widget.loadmoreadapter.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xieH on 2017/7/17 0017.
 */
public class Behavior22Activity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private BaseNormalAdapter<Item> mBaseNormalAdapter;
    private List<Item> mItemList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_behavior22);

        initView();

        setData();
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        initAdapter();

        RecyclerWrapAdapter recyclerWrapAdapter = new RecyclerWrapAdapter(mBaseNormalAdapter);

        View view = View.inflate(this, R.layout.item_recycler_2, null);
        recyclerWrapAdapter.addHeaderView(view);

        View view11 = View.inflate(this, R.layout.item_recycler_2, null);
        recyclerWrapAdapter.addHeaderView(view11);


        View view22 = View.inflate(this, R.layout.item_recycler_2, null);
        recyclerWrapAdapter.addFooterView(view22);

        View view33 = View.inflate(this, R.layout.item_recycler_2, null);
        recyclerWrapAdapter.addFooterView(view33);


        mRecyclerView.setAdapter(recyclerWrapAdapter);
    }

    private void initAdapter() {
        mBaseNormalAdapter = new BaseNormalAdapter<Item>(this, mItemList, false) {
            @Override
            protected int getItemLayoutId() {
                return R.layout.item_recycler;
            }

            @Override
            protected void convert(ViewHolder holder, Item item, int position) {

            }
        };

        mBaseNormalAdapter.setOnItemClickListener(new BaseNormalAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Object data, int position) {

            }
        });
    }

    private void setData() {
        for (int i = 0; i < 20; i++) {
            Item item = new Item();
            mItemList.add(item);
        }

        mBaseNormalAdapter.refresh(mItemList);
    }

}
