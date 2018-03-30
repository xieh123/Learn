package com.example.myapplication.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.Item;
import com.example.myapplication.widget.loadmoreadapter.BaseNormalAdapter;
import com.example.myapplication.widget.loadmoreadapter.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xieH on 2017/4/20 0020.
 */
public class Test22Fragment extends LazyFragment {

    private RecyclerView mRecyclerView;
    private BaseNormalAdapter<Item> mBaseNormalAdapter;
    private List<Item> mItemList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_test22;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        System.out.println("hhh-------22---onActivityCreated");

        initView();

        setData();
    }

    private void initView() {
        mRecyclerView = (RecyclerView) getContentView().findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        initAdapter();
        mRecyclerView.setAdapter(mBaseNormalAdapter);
    }

    private void initAdapter() {
        mBaseNormalAdapter = new BaseNormalAdapter<Item>(getActivity(), mItemList, false) {
            @Override
            protected int getItemLayoutId() {
                return R.layout.item_recycler;
            }

            @Override
            protected void convert(ViewHolder holder, Item item, int position) {

            }
        };
    }

    private void setData() {
        for (int i = 0; i < 20; i++) {
            Item item = new Item();
            mItemList.add(item);
        }

        mBaseNormalAdapter.refresh(mItemList);
    }

    @Override
    public void onStart() {
        super.onStart();
        System.out.println("hhh--------22--onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        System.out.println("hhh-------22---onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        System.out.println("hhh-------22---onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        System.out.println("hhh-------22---onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        System.out.println("hhh-------22---onDestroy");
    }

    @Override
    protected void onVisible() {
        super.onVisible();
        System.out.println("hhh-------22---onVisible");
    }

    @Override
    protected void onInvisible() {
        super.onInvisible();
        System.out.println("hhh-------22---onInvisible");
    }

    @Override
    protected void onLazyLoad() {
        super.onLazyLoad();

        System.out.println("hhh-------22---onLazyLoad");
    }
}
