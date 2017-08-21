package com.example.myapplication.ui.timeline;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.example.myapplication.R;
import com.example.myapplication.adapter.BaseRecyclerAdapter;
import com.example.myapplication.adapter.RecyclerHolder;
import com.example.myapplication.model.Item;
import com.example.myapplication.utils.RecyclerViewItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xieH on 2017/6/12 0012.
 */
public class TimeLineActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView, mRecyclerView11, mRecyclerView22;
    private BaseRecyclerAdapter<Item> mBaseRecyclerAdapter;
    private List<Item> mItemList = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_line);

        initView();

        setData();
    }

    public void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.time_line_rv);

        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        initAdapter();
        mRecyclerView.setAdapter(mBaseRecyclerAdapter);
        mRecyclerView.addItemDecoration(new ItemDecoration(this, 20));

        mRecyclerView11 = (RecyclerView) findViewById(R.id.time_line11_rv);
        mRecyclerView11.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView11.setAdapter(mBaseRecyclerAdapter);
        mRecyclerView11.addItemDecoration(new ItemDecoration2(this, 20));


        mRecyclerView22 = (RecyclerView) findViewById(R.id.time_line22_rv);
        mRecyclerView22.setLayoutManager(new GridLayoutManager(this, 2));
        mRecyclerView22.setAdapter(mBaseRecyclerAdapter);
        mRecyclerView22.addItemDecoration(new RecyclerViewItemDecoration.Builder(this)
                .mode(RecyclerViewItemDecoration.MODE_GRID)
                .color(Color.RED)
                .dashWidth(20)
                .dashGap(5)
                .thickness(6)
//                .drawableID(R.drawable.diver)
//                .paddingStart(20)
//                .paddingEnd(10)
                .gridBottomVisible(true)
                .gridTopVisible(true)
                .gridLeftVisible(true)
                .gridRightVisible(true)
                .create());
    }

    private void initAdapter() {
        mBaseRecyclerAdapter = new BaseRecyclerAdapter<Item>(mRecyclerView, mItemList, R.layout.item_recycler) {
            @Override
            public void convert(RecyclerHolder holder, Item item, int position, boolean isScrolling) {

            }
        };
    }

    private void setData() {
        for (int i = 0; i < 10; i++) {
            Item item = new Item();

            mItemList.add(item);
        }

        mBaseRecyclerAdapter.refresh(mItemList);
    }
}
