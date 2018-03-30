package com.example.myapplication.ui.tab1;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.myapplication.R;
import com.example.myapplication.fragment.LazyFragment;
import com.example.myapplication.model.Item;
import com.example.myapplication.widget.IndicatorViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by xieH on 2017/3/23 0023.
 */
public class MyFragment extends LazyFragment {


    private String[] urls = new String[]{
            "http://pic19.nipic.com/20120324/3484432_092618805000_2.jpg",
            "http://img3.tuniucdn.com/images/2011-03-29/L/LFXLzoSGG9g753SH.jpg",
            "http://pic3.nipic.com/20090603/2781538_100414093_2.jpg"
    };

    private IndicatorViewPager mIndicatorViewPager;
    private List<Item> mItemList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView();
    }

    private void initView() {
        mIndicatorViewPager = (IndicatorViewPager) getContentView().findViewById(R.id.indicatorViewPager);

        mItemList.clear();
        for (int i = 0; i < 3; i++) {
            Item item = new Item();
            item.setUrl(urls[i]);

            mItemList.add(item);
        }
        mIndicatorViewPager.setViewpagerData(mItemList);
        mIndicatorViewPager.setAutoScroll(true);
    }

    @Override
    protected void onVisible() {
        super.onVisible();
        if (mIndicatorViewPager != null) {
            mIndicatorViewPager.setAutoScroll(true);
        }
    }

    @Override
    protected void onInvisible() {
        super.onInvisible();
        if (mIndicatorViewPager != null) {
            mIndicatorViewPager.setAutoScroll(false);
        }
    }
}
