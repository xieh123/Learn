package com.example.myapplication.ui.tab2;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.Item;
import com.example.myapplication.widget.loadmoreadapter.BaseAdapter;
import com.example.myapplication.widget.loadmoreadapter.EmptyLayout;
import com.example.myapplication.widget.loadmoreadapter.LoadingFooterLayout;
import com.example.myapplication.widget.loadmoreadapter.BaseNormalAdapter;
import com.example.myapplication.widget.loadmoreadapter.ViewHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by xieH on 2017/8/7 0007.
 */
public class LoadMoreNormalActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private List<Item> mItemList = new ArrayList<>();

    private BaseNormalAdapter<Item> mBaseNormalAdapter;

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
//        mBaseNormalAdapter.setEmptyLayout(mEmptyLayout);
//
//        //////
//        LoadingFooterLayout mLoadingFooterLayout = new LoadingFooterLayout(this);
//        View loadMoreLoadingView = View.inflate(this, R.layout.recycler_footer_loading, null);
//        mLoadingFooterLayout.setLoadingView(loadMoreLoadingView);
//        mBaseNormalAdapter.setFooterLayout(mLoadingFooterLayout);

        ////
        mRecyclerView.setAdapter(mBaseNormalAdapter);

        ///////////
        mBaseNormalAdapter.getEmptyLayout().setOnReloadListener(new EmptyLayout.OnReloadListener() {
            @Override
            public void onReload() {
                getData();
            }
        });

        mBaseNormalAdapter.getFooterLayout().setOnReloadListener(new LoadingFooterLayout.OnReloadListener() {
            @Override
            public void onReload() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        int index = new Random().nextInt(3);
                        if (index == 0) {
                            mBaseNormalAdapter.loadMoreError();
                        } else if (index == 1) {
                            mBaseNormalAdapter.loadMoreEnd();
                        } else if (index == 2) {
                            setData();
                        }
                    }
                }, 1000);
            }
        });
    }

    private void initAdapter() {
        mBaseNormalAdapter = new BaseNormalAdapter<Item>(this, mItemList, true) {
            @Override
            protected void convert(ViewHolder holder, Item item, int position) {

            }

            @Override
            protected int getItemLayoutId() {
                return R.layout.item_recycler_2;
            }
        };

        mBaseNormalAdapter.setOnLoadMoreListener(new BaseAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                System.out.println("hhh-------load more-----");

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        int index = new Random().nextInt(3);

                        if (index == 0) {
                            mBaseNormalAdapter.loadMoreError();
                        } else if (index == 1) {
                            mBaseNormalAdapter.loadMoreEnd();
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
                    mBaseNormalAdapter.loadError();
                } else if (index == 1) {
                    mBaseNormalAdapter.loadEnd();
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

        mBaseNormalAdapter.refresh(mItemList);

        // 恢复状态
        mBaseNormalAdapter.loadMoreNormal();
    }

}
