package com.example.myapplication.ui.tab1;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.BaseRecyclerAdapter;
import com.example.myapplication.adapter.RecyclerHolder;
import com.example.myapplication.model.Item;
import com.willowtreeapps.spruce.Spruce;
import com.willowtreeapps.spruce.animation.DefaultAnimations;
import com.willowtreeapps.spruce.sort.DefaultSort;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xieH on 2017/6/14 0014.
 */
public class ListActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    private BaseRecyclerAdapter<Item> baseRecyclerAdapter;
    private List<Item> mItemList = new ArrayList<>();

    private Animator spruceAnimator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        initView();

        setData();
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.list_rv);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this) {
            @Override
            public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
                super.onLayoutChildren(recycler, state);
                // Animate in the visible children
                spruceAnimator = new Spruce.SpruceBuilder(mRecyclerView)
                        .sortWith(new DefaultSort(100))
                        .animateWith(DefaultAnimations.shrinkAnimator(mRecyclerView, 800),
                                ObjectAnimator.ofFloat(mRecyclerView, "translationX", -mRecyclerView.getWidth(), 0f).setDuration(800))
                        .start();

//                Animator spruceAnimator = new Spruce.SpruceBuilder(mRecyclerView)
//                        .sortWith(new LinearSort(/*interObjectDelay=*/100L, /*reversed=*/false, LinearSort.Direction.TOP_TO_BOTTOM))
//                        .animateWith(DefaultAnimations.shrinkAnimator(mRecyclerView, /*duration=*/800))
//                        .start();

            }
        };

        mRecyclerView.setLayoutManager(linearLayoutManager);
        initAdapter();
        mRecyclerView.setAdapter(baseRecyclerAdapter);

    }

    private void initAdapter() {
        baseRecyclerAdapter = new BaseRecyclerAdapter<Item>(mRecyclerView, mItemList, R.layout.item_recycler) {
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

        baseRecyclerAdapter.refresh(mItemList);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (spruceAnimator != null) {
            spruceAnimator.start();
        }
    }
}
