package com.example.myapplication.ui.tab1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.BaseRecyclerAdapter;
import com.example.myapplication.adapter.RecyclerHolder;
import com.example.myapplication.model.Item;
import com.example.myapplication.transform.ImageUtils;
import com.example.myapplication.utils.DensityUtils;
import com.example.myapplication.widget.DividerGridItemDecoration;
import com.example.myapplication.widget.recyclerview.FooterStateUtils;
import com.example.myapplication.widget.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.example.myapplication.widget.recyclerview.HeaderSpanSizeLookup;
import com.example.myapplication.widget.recyclerview.LoadingFooterLayout;
import com.example.myapplication.widget.recyclerview.RecyclerOnScrollListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xieH on 2017/2/16 0016.
 */
public class RecyclerDecorationActivity extends AppCompatActivity {

    private String[] urls = new String[]{
            "http://img01.sogoucdn.com/app/a/100520093/60d2f4fe0275d790-fbe7539243950f9f-7f669dbeead0ad667f21be96b5efd843.jpg",
            "http://pic19.nipic.com/20120324/3484432_092618805000_2.jpg",
            "http://img3.tuniucdn.com/images/2011-03-29/L/LFXLzoSGG9g753SH.jpg",
            "http://pic3.nipic.com/20090603/2781538_100414093_2.jpg",
            "http://pic.nipic.com/2008-03-01/2008319174451_2.jpg",
            "http://img01.sogoucdn.com/app/a/100520093/013d20860a59d114-e452007590d91dbc-e655209caf5a45fc1143eefe707a62ab.jpg",
            "http://pic24.nipic.com/20121029/3822951_090444696000_2.jpg",
            "http://img4.duitang.com/uploads/item/201209/20/20120920165508_EuenZ.jpeg",
            "http://img01.sogoucdn.com/app/a/100520093/ac75323d6b6de243-503c0c74be6ae02f-bd063b042a12e0bf0776668bccfddea3.jpg",
            "http://img4.duitang.com/uploads/item/201209/20/20120920165508_EuenZ.jpeg"};

    private RecyclerView mRecyclerView;

    private List<Item> itemList = new ArrayList<>();
    private BaseRecyclerAdapter<Item> mBaseRecyclerAdapter;

    private HeaderAndFooterRecyclerViewAdapter mHeaderAndFooterRecyclerViewAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_decoration);

        initView();

        setData();
    }

    public void initView() {

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_rv);

        initAdapter();

        mHeaderAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(mBaseRecyclerAdapter);
        mRecyclerView.setAdapter(mHeaderAndFooterRecyclerViewAdapter);

//        mRecyclerView.addItemDecoration(new DividerGridItemDecoration(this));

        mRecyclerView.addItemDecoration(new DividerGridItemDecoration(
                this, DensityUtils.dp2px(this, 5), ContextCompat.getColor(this, R.color.red_e73a3d)));

        GridLayoutManager manager = new GridLayoutManager(this, 3);
        manager.setSpanSizeLookup(new HeaderSpanSizeLookup((HeaderAndFooterRecyclerViewAdapter) mRecyclerView.getAdapter(), manager.getSpanCount()));
        mRecyclerView.setLayoutManager(manager);

        //  mRecyclerView.setNestedScrollingEnabled(false);


        View header = View.inflate(this, R.layout.activity_user_info, null);
        mHeaderAndFooterRecyclerViewAdapter.addHeaderView(header);

        LoadingFooterLayout mLoadingFooterLayout = new LoadingFooterLayout(this);
        mHeaderAndFooterRecyclerViewAdapter.addFooterView(mLoadingFooterLayout);


        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

//        mRecyclerView.addItemDecoration(new DividerItemDecoration(
//                this, LinearLayoutManager.VERTICAL, DensityUtils.dp2px(this, 5), ContextCompat.getColor(this, R.color.red_e73a3d)));
//        mRecyclerView.addItemDecoration(new DividerItemDecoration(
//                this, LinearLayoutManager.HORIZONTAL, DensityUtils.dp2px(this, 50), ContextCompat.getColor(this, R.color.red_e73a3d)));


        mRecyclerView.addOnScrollListener(new RecyclerOnScrollListener() {
            @Override
            public void onLoadNextPage(View view) {
                super.onLoadNextPage(view);

                LoadingFooterLayout.State state = FooterStateUtils.getFooterViewState(mRecyclerView);

                if (state == LoadingFooterLayout.State.Loading) {
                    Log.d("Network", "the state is Loading, just wait...");
                    return;
                }

                if (1 < 10) {
                    // loading more
                    FooterStateUtils.setFooterViewState(mRecyclerView, LoadingFooterLayout.State.Loading);

                } else {
                    // the end
                    FooterStateUtils.setFooterViewState(mRecyclerView, LoadingFooterLayout.State.End);
                }

            }
        });

//        mNestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
//            @Override
//            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                // title的高度
//                int titleHeight = mTitleLl.getBottom();
//
//                // 当滑动的距离 <= title 高度的时候，改变title背景色的透明度，达到渐变的效果
//                if (scrollY <= titleHeight) {
//                    float scale = (float) scrollY / titleHeight;
//                    float alpha = scale * 255;
//                    mTitleLl.setBackgroundColor(Color.argb((int) alpha, 255, 255, 255));
//
//                    if (scrollY >= titleHeight / 2) {
//                        mSearchTv.setBackgroundResource(R.drawable.search_btn_light);
//                        mLocationTv.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_down_light, 0, 0);
//                        mLocationTv.setTextColor(ContextCompat.getColor(HomeActivity.this, R.color.text_normal_color));
//                        mMessageTv.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_message_light, 0, 0);
//                        mMessageTv.setTextColor(ContextCompat.getColor(HomeActivity.this, R.color.text_normal_color));
//                    } else {
//                        mSearchTv.setBackgroundResource(R.drawable.search_btn_normal);
//                        mLocationTv.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_down_normal, 0, 0);
//                        mLocationTv.setTextColor(ContextCompat.getColor(HomeActivity.this, R.color.white_color));
//                        mMessageTv.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_message_normal, 0, 0);
//                        mMessageTv.setTextColor(ContextCompat.getColor(HomeActivity.this, R.color.white_color));
//                    }
//
//                } else {
//                    //将标题栏的颜色设置为完全不透明状态
//                    mTitleLl.setBackgroundResource(R.color.white_color);
//                    mSearchTv.setBackgroundResource(R.drawable.search_btn_light);
//                    mLocationTv.setTextColor(ContextCompat.getColor(HomeActivity.this, R.color.text_normal_color));
//                    mMessageTv.setTextColor(ContextCompat.getColor(HomeActivity.this, R.color.text_normal_color));
//                }
//            }
//        });


    }

    public void initAdapter() {
        mBaseRecyclerAdapter = new BaseRecyclerAdapter<Item>(this, itemList, R.layout.recyclerview_item) {
            @Override
            public void convert(RecyclerHolder holder, Item item, int position) {
                ImageView mImageView = holder.getView(R.id.recyclerview_item_iv);

                ImageUtils.loadCornersImage(RecyclerDecorationActivity.this, mImageView, item.getUrl());
            }
        };
    }

    public void setData() {

        for (int i = 0; i < urls.length; i++) {
            Item item = new Item();

            item.setUrl(urls[i]);

            itemList.add(item);
        }

        mBaseRecyclerAdapter.refresh(itemList);
    }
}
