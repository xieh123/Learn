package com.example.myapplication.ui.tab2;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.Item;
import com.example.myapplication.widget.loadMoreAdapter.BaseAdapter;
import com.example.myapplication.widget.loadMoreAdapter.EmptyLayout;
import com.example.myapplication.widget.loadMoreAdapter.LoadingFooterLayout;
import com.example.myapplication.widget.loadMoreAdapter.NormalBaseAdapter;
import com.example.myapplication.widget.loadMoreAdapter.ViewHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by xieH on 2017/8/7 0007.
 */
public class LoadMoreNormalActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private List<Item> mItemList = new ArrayList<>();

    private NormalBaseAdapter<Item> mNormalBaseAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_more_normal);

        initView();

        getData();
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        initAdapter();

//        /**
//         * 自定义状态View  TODO 注意：必须在添加 Adapter 之前设置
//         */
//        EmptyLayout mEmptyLayout = new EmptyLayout(this);
//        View loadingView = View.inflate(this, R.layout.dialog_loading, null);
//        mEmptyLayout.setLoadingView(loadingView);
//        mNormalBaseAdapter.setEmptyLayout(mEmptyLayout);
//
//        //////
//        LoadingFooterLayout mLoadingFooterLayout = new LoadingFooterLayout(this);
//        View loadMoreLoadingView = View.inflate(this, R.layout.recycler_footer_loading, null);
//        mLoadingFooterLayout.setLoadingView(loadMoreLoadingView);
//        mNormalBaseAdapter.setFooterLayout(mLoadingFooterLayout);

        ////
        mRecyclerView.setAdapter(mNormalBaseAdapter);

        ///////////
        mNormalBaseAdapter.getEmptyLayout().setOnReloadListener(new EmptyLayout.OnReloadListener() {
            @Override
            public void onReload() {
                getData();
            }
        });

        mNormalBaseAdapter.getFooterLayout().setOnReloadListener(new LoadingFooterLayout.OnReloadListener() {
            @Override
            public void onReload() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        int index = new Random().nextInt(3);
                        if (index == 0) {
                            mNormalBaseAdapter.loadMoreError();
                        } else if (index == 1) {
                            mNormalBaseAdapter.loadMoreEnd();
                        } else if (index == 2) {
                            setData();
                        }
                    }
                }, 1000);
            }
        });
    }

    private void initAdapter() {
        mNormalBaseAdapter = new NormalBaseAdapter<Item>(this, mItemList, true) {
            @Override
            protected void convert(ViewHolder holder, Item item, int position) {

            }

            @Override
            protected int getItemLayoutId() {
                return R.layout.item_recycler_2;
            }
        };

        mNormalBaseAdapter.setOnLoadMoreListener(new BaseAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                System.out.println("hhh-------load more-----");

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        int index = new Random().nextInt(3);

                        if (index == 0) {
                            mNormalBaseAdapter.loadMoreError();
                        } else if (index == 1) {
                            mNormalBaseAdapter.loadMoreEnd();
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
                    mNormalBaseAdapter.loadError();
                } else if (index == 1) {
                    mNormalBaseAdapter.loadEnd();
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

        mNormalBaseAdapter.refresh(mItemList);

        // 恢复状态
        mNormalBaseAdapter.loadMoreNormal();
    }

}
