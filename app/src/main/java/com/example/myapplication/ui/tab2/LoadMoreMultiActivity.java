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
import com.example.myapplication.widget.loadmoreadapter.BaseMultiAdapter;
import com.example.myapplication.widget.loadmoreadapter.EmptyLayout;
import com.example.myapplication.widget.loadmoreadapter.LoadingFooterLayout;
import com.example.myapplication.widget.loadmoreadapter.ViewHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by xieH on 2017/8/7 0007.
 */
public class LoadMoreMultiActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private List<Item> mItemList = new ArrayList<>();
    private BaseMultiAdapter<Item> mBaseMultiAdapter;

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
//        mBaseMultiAdapter.setEmptyLayout(mEmptyLayout);
//
//        //////
//        LoadingFooterLayout mLoadingFooterLayout = new LoadingFooterLayout(this);
//        View loadMoreLoadingView = View.inflate(this, R.layout.recycler_footer_loading, null);
//        mLoadingFooterLayout.setLoadingView(loadMoreLoadingView);
//        mBaseMultiAdapter.setFooterLayout(mLoadingFooterLayout);

        ////
        mRecyclerView.setAdapter(mBaseMultiAdapter);

        ///////////
        mBaseMultiAdapter.getEmptyLayout().setOnReloadListener(new EmptyLayout.OnReloadListener() {
            @Override
            public void onReload() {
                getData();
            }
        });

        mBaseMultiAdapter.getFooterLayout().setOnReloadListener(new LoadingFooterLayout.OnReloadListener() {
            @Override
            public void onReload() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        int index = new Random().nextInt(3);

                        if (index == 0) {
                            mBaseMultiAdapter.loadMoreError();
                        } else if (index == 1) {
                            mBaseMultiAdapter.loadMoreEnd();
                        } else if (index == 2) {
                            setData();
                        }
                    }
                }, 1000);
            }
        });
    }

    private void initAdapter() {
        mBaseMultiAdapter = new BaseMultiAdapter<Item>(this, mItemList, true) {
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

        mBaseMultiAdapter.setOnLoadMoreListener(new BaseAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                System.out.println("hhh-------load more-----");

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        int index = new Random().nextInt(3);

                        if (index == 0) {
                            mBaseMultiAdapter.loadMoreError();
                        } else if (index == 1) {
                            mBaseMultiAdapter.loadMoreEnd();
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
                    mBaseMultiAdapter.loadError();
                } else if (index == 1) {
                    mBaseMultiAdapter.loadEnd();
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

        mBaseMultiAdapter.refresh(mItemList);

        // 恢复状态
        mBaseMultiAdapter.loadMoreNormal();
    }
}
