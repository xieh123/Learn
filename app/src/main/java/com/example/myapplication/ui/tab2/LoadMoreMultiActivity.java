package com.example.myapplication.ui.tab2;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.Item;
import com.example.myapplication.widget.loadMore.BaseAdapter;
import com.example.myapplication.widget.loadMore.EmptyLayout;
import com.example.myapplication.widget.loadMore.LoadingFooterLayout;
import com.example.myapplication.widget.loadMore.MultiBaseAdapter;
import com.example.myapplication.widget.loadMore.ViewHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by xieH on 2017/8/7 0007.
 */
public class LoadMoreMultiActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private List<Item> mItemList = new ArrayList<>();
    private MultiBaseAdapter<Item> mMultiBaseAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_more_multi);

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
//        mMultiBaseAdapter.setEmptyLayout(mEmptyLayout);
//
//        //////
//        LoadingFooterLayout mLoadingFooterLayout = new LoadingFooterLayout(this);
//        View loadMoreLoadingView = View.inflate(this, R.layout.recycler_footer_loading, null);
//        mLoadingFooterLayout.setLoadingView(loadMoreLoadingView);
//        mMultiBaseAdapter.setFooterLayout(mLoadingFooterLayout);

        ////
        mRecyclerView.setAdapter(mMultiBaseAdapter);

        ///////////
        mMultiBaseAdapter.getEmptyLayout().setOnReloadListener(new EmptyLayout.OnReloadListener() {
            @Override
            public void onReload() {
                getData();
            }
        });

        mMultiBaseAdapter.getFooterLayout().setOnReloadListener(new LoadingFooterLayout.OnReloadListener() {
            @Override
            public void onReload() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        int index = new Random().nextInt(3);

                        if (index == 0) {
                            mMultiBaseAdapter.loadMoreError();
                        } else if (index == 1) {
                            mMultiBaseAdapter.loadMoreEnd();
                        } else if (index == 2) {
                            setData();
                        }
                    }
                }, 1000);
            }
        });
    }

    private void initAdapter() {
        mMultiBaseAdapter = new MultiBaseAdapter<Item>(this, mItemList, true) {
            @Override
            protected void convert(ViewHolder holder, Item item, int position) {

            }

            @Override
            protected int getItemLayoutId(int viewType) {
                if (viewType == 0) {
                    return R.layout.item_recycler;
                }
                return R.layout.item_recycler_2;
            }

            @Override
            protected int getViewType(int position, Item item) {
                if (position % 2 == 0) {
                    return 0;
                }
                return 1;
            }
        };

        mMultiBaseAdapter.setOnLoadMoreListener(new BaseAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                System.out.println("hhh-------load more-----");

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        int index = new Random().nextInt(3);

                        if (index == 0) {
                            mMultiBaseAdapter.loadMoreError();
                        } else if (index == 1) {
                            mMultiBaseAdapter.loadMoreEnd();
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
                    mMultiBaseAdapter.loadError();
                } else if (index == 1) {
                    mMultiBaseAdapter.loadEnd();
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

        mMultiBaseAdapter.refresh(mItemList);

        // 恢复状态
        mMultiBaseAdapter.loadMoreNormal();
    }
}
