package com.example.myapplication.ui.tab1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.adapter.BaseRecyclerAdapter;
import com.example.myapplication.adapter.RecyclerHolder;
import com.example.myapplication.model.Item;
import com.example.myapplication.widget.ParallaxRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xieH on 2017/3/8 0008.
 */
public class FoldRecyclerViewActivity extends AppCompatActivity {

    private ParallaxRecyclerView mParallaxRecyclerView;

    private BaseRecyclerAdapter<Item> mBaseRecyclerAdapter;
    private List<Item> itemList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fold_recyclerview);

        initView();

        setData();
    }

    public void initView() {
        mParallaxRecyclerView = (ParallaxRecyclerView) findViewById(R.id.fold_recyclerview_rv);

        initAdapter();
        mParallaxRecyclerView.setAdapter(mBaseRecyclerAdapter);
    }

    public void initAdapter() {
        mBaseRecyclerAdapter = new BaseRecyclerAdapter<Item>(this, itemList, R.layout.item_recycler) {
            @Override
            public void convert(RecyclerHolder holder, Item item, int position) {

            }
        };
    }

    public void setData() {

        for (int i = 0; i < 20; i++) {
            Item item = new Item();

            itemList.add(item);
        }

        mBaseRecyclerAdapter.refresh(itemList);
    }
}
