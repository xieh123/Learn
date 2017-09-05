package com.example.myapplication.widget.loadMoreAdapter;

import android.content.Context;

import com.example.myapplication.R;
import com.example.myapplication.model.Item;

import java.util.List;

/**
 * Created by xieH on 2017/8/7 0007.
 */
public class MultiRefreshAdapter extends MultiBaseAdapter<Item> {

    public MultiRefreshAdapter(Context context, List<Item> datas, boolean isOpenLoadMore) {
        super(context, datas, isOpenLoadMore);
    }

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
    protected int getViewType(int position, Item data) {
        if (position % 2 == 0) {
            return 0;
        }
        return 1;
    }
}
