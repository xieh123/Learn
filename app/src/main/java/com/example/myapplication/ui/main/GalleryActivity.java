package com.example.myapplication.ui.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.adapter.BaseRecyclerAdapter;
import com.example.myapplication.adapter.RecyclerHolder;
import com.example.myapplication.model.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xieH on 2017/2/9 0009.
 */
public class GalleryActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private BaseRecyclerAdapter<Item> mBaseRecyclerAdapter;
    private List<Item> itemList = new ArrayList<>();

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        initView();

        setData();
    }

    public void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.gallery_rv);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        initAdapter();
        mRecyclerView.setAdapter(mBaseRecyclerAdapter);

        // 居中显示
        new LinearSnapHelper().attachToRecyclerView(mRecyclerView);

        // 居左显示
//        CustomSnapHelper mSnapHelper = new CustomSnapHelper();
//        mSnapHelper.attachToRecyclerView(mRecyclerView);
    }

    public void initAdapter() {
        mBaseRecyclerAdapter = new BaseRecyclerAdapter<Item>(mRecyclerView, itemList, R.layout.item_card_view) {
            @Override
            public void convert(RecyclerHolder holder, Item item, int position, boolean isScrolling) {
                ImageView mImageView = holder.getView(R.id.item_iv);
                Glide.with(GalleryActivity.this).load(item.getUrl()).into(mImageView);
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
