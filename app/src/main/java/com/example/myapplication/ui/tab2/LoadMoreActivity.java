package com.example.myapplication.ui.tab2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.myapplication.R;
import com.example.myapplication.model.Item;
import com.example.myapplication.widget.loadmoreadapter.BaseAdapter2;
import com.example.myapplication.widget.loadmoreadapter.EmptyLayout;
import com.example.myapplication.widget.loadmoreadapter.LoadingFooterLayout;
import com.example.myapplication.widget.loadmoreadapter.ViewHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by xieH on 2017/8/5 0005.
 */
public class LoadMoreActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private BaseAdapter2<Item> mBaseAdapter;
    private List<Item> mItemList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_more);

        initView();

        getData();
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        initAdapter();

//        /**
//         * 自定义状态View  TODO 注意：必须在添加 Adapter 之前设置
//         */
//        EmptyLayout mEmptyLayout = new EmptyLayout(this);
//        View loadingView = View.inflate(this, R.layout.dialog_loading, null);
//        mEmptyLayout.setLoadingView(loadingView);
//        mBaseAdapter.setEmptyLayout(mEmptyLayout);
//
//        //////
//        LoadingFooterLayout mLoadingFooterLayout = new LoadingFooterLayout(this);
//        View loadMoreLoadingView = View.inflate(this, R.layout.recycler_footer_loading, null);
//        mLoadingFooterLayout.setLoadingView(loadMoreLoadingView);
//        mBaseAdapter.setFooterLayout(mLoadingFooterLayout);


        mRecyclerView.setAdapter(mBaseAdapter);

        ///////////
        mBaseAdapter.getEmptyLayout().setOnReloadListener(new EmptyLayout.OnReloadListener() {
            @Override
            public void onReload() {
                getData();
            }
        });

        mBaseAdapter.getFooterLayout().setOnReloadListener(new LoadingFooterLayout.OnReloadListener() {
            @Override
            public void onReload() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        int index = new Random().nextInt(3);
                        if (index == 0) {
                            mBaseAdapter.loadMoreError();
                        } else if (index == 1) {
                            mBaseAdapter.loadMoreEnd();
                        } else if (index == 2) {
                            setData();
                        }
                    }
                }, 1000);
            }
        });
    }

    private void initAdapter() {
        mBaseAdapter = new BaseAdapter2<Item>(this, mItemList, R.layout.item_recycler, true) {
            @Override
            public void convert(ViewHolder holder, Item item, int position) {

            }
        };

        mBaseAdapter.setOnLoadMoreListener(new BaseAdapter2.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                System.out.println("hhh-------load more-----");

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        int index = new Random().nextInt(3);

                        if (index == 0) {
                            mBaseAdapter.loadMoreError();
                        } else if (index == 1) {
                            mBaseAdapter.loadMoreEnd();
                        } else if (index == 2) {
                            setData();
                        }
                    }
                }, 1000);
            }
        });
    }

    private void getData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                int index = new Random().nextInt(3);

                if (index == 0) {
                    mBaseAdapter.loadError();
                } else if (index == 1) {
                    mBaseAdapter.loadEnd();
                } else if (index == 2) {
                    setData();
                }
            }
        }, 1000);
    }

    private void setData() {
        for (int i = 0; i < 10; i++) {
            Item item = new Item();
            mItemList.add(item);
        }

        mBaseAdapter.refresh(mItemList);

        // 恢复状态
        mBaseAdapter.loadMoreNormal();
    }

    public void normal(View v) {
        Intent intent = new Intent(this, LoadMoreNormalActivity.class);
        startActivity(intent);
    }

    public void multi(View v) {
        Intent intent = new Intent(this, LoadMoreMultiActivity.class);
        startActivity(intent);
    }

}
