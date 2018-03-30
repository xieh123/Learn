package com.example.myapplication.ui.tab2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.myapplication.R;
import com.example.myapplication.model.Item;
import com.example.myapplication.widget.ParallaxImageView;
import com.example.myapplication.widget.ParallaxImageView11;
import com.example.myapplication.widget.loadmoreadapter.BaseNormalAdapter;
import com.example.myapplication.widget.loadmoreadapter.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xieH on 2018/1/26 0026.
 */
public class ParallaxPictureActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private BaseNormalAdapter<Item> mBaseNormalAdapter;
    private List<Item> mItemList = new ArrayList<>();

    private LinearLayoutManager mLinearLayoutManager;

    /////////
    private RecyclerView mRecyclerView11;
    private BaseNormalAdapter<Item> mBaseNormalAdapter11;

    private LinearLayoutManager mLinearLayoutManager11;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parallax_picture);

        initView();

        setData();
    }

    private void initView() {
        initAdapter();

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(mBaseNormalAdapter);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int fPos = mLinearLayoutManager.findFirstVisibleItemPosition();
                int lPos = mLinearLayoutManager.findLastCompletelyVisibleItemPosition();
                for (int i = fPos; i <= lPos; i++) {
                    View view = mLinearLayoutManager.findViewByPosition(i);
                    ParallaxImageView imageView = (ParallaxImageView) view.findViewById(R.id.item_iv);
                    if (imageView.getVisibility() == View.VISIBLE) {
                        imageView.setDy(mLinearLayoutManager.getHeight() - view.getTop());
                    }
                }
            }
        });

        ///////////
        mRecyclerView11 = (RecyclerView) findViewById(R.id.recyclerView11);
        mLinearLayoutManager11 = new LinearLayoutManager(this);
        mRecyclerView11.setLayoutManager(mLinearLayoutManager11);
        mRecyclerView11.setAdapter(mBaseNormalAdapter11);

        mRecyclerView11.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int fPos = mLinearLayoutManager11.findFirstVisibleItemPosition();
                int lPos = mLinearLayoutManager11.findLastCompletelyVisibleItemPosition();
                for (int i = fPos; i <= lPos; i++) {
                    final View itemView = mLinearLayoutManager11.findViewByPosition(i);
                    ParallaxImageView11 imageView = (ParallaxImageView11) itemView.findViewById(R.id.item_iv);

                    imageView.setListener(new ParallaxImageView11.ParallaxImageListener() {
                        @Override
                        public int[] requireValuesForTranslate() {
                            if (itemView.getParent() == null) {
                                return null;
                            } else {
                                int[] itemPosition = new int[2];
                                // 获取itemView左上角在屏幕上的坐标
                                itemView.getLocationOnScreen(itemPosition);
                                int[] recyclerViewPosition = new int[2];
                                // 获取recyclerView在屏幕上的坐标
                                ((RecyclerView) itemView.getParent()).getLocationOnScreen(recyclerViewPosition);
                                // 将参数传递过去
                                // itemView的高度, itemView在屏幕上的y坐标, recyclerView的高度, recyclerView在屏幕上的y坐标
                                return new int[]{itemView.getMeasuredHeight(), itemPosition[1], ((RecyclerView) itemView.getParent()).getHeight(), recyclerViewPosition[1]};
                            }
                        }
                    });

                    if (imageView.getVisibility() == View.VISIBLE) {
                        imageView.doTranslate();
                    }
                }
            }
        });
    }

    private void initAdapter() {
        mBaseNormalAdapter = new BaseNormalAdapter<Item>(this, mItemList, false) {
            @Override
            protected int getItemLayoutId() {
                return R.layout.item_recycler_parallax_picture;
            }

            @Override
            protected void convert(ViewHolder holder, Item item, int position) {
                if (position > 0 && position % 5 == 0) {
                    holder.getView(R.id.item_iv).setVisibility(View.VISIBLE);
                    holder.getView(R.id.item_title_tv).setVisibility(View.GONE);
                    holder.getView(R.id.item_desc_tv).setVisibility(View.GONE);
                } else {
                    holder.getView(R.id.item_iv).setVisibility(View.GONE);
                    holder.getView(R.id.item_title_tv).setVisibility(View.VISIBLE);
                    holder.getView(R.id.item_desc_tv).setVisibility(View.VISIBLE);
                }
            }
        };

        mBaseNormalAdapter11 = new BaseNormalAdapter<Item>(this, mItemList, false) {
            @Override
            protected int getItemLayoutId() {
                return R.layout.item_recycler_parallax_picture11;
            }

            @Override
            protected void convert(ViewHolder holder, Item item, int position) {
                if (position > 0 && position % 5 == 0) {
                    holder.getView(R.id.item_iv).setVisibility(View.VISIBLE);
                    holder.getView(R.id.item_title_tv).setVisibility(View.GONE);
                    holder.getView(R.id.item_desc_tv).setVisibility(View.GONE);
                } else {
                    holder.getView(R.id.item_iv).setVisibility(View.GONE);
                    holder.getView(R.id.item_title_tv).setVisibility(View.VISIBLE);
                    holder.getView(R.id.item_desc_tv).setVisibility(View.VISIBLE);
                }
            }
        };
    }

    private void setData() {
        for (int i = 0; i < 20; i++) {
            Item item = new Item();

            mItemList.add(item);
        }

        mBaseNormalAdapter.refresh(mItemList);
        mBaseNormalAdapter11.refresh(mItemList);
    }
}
