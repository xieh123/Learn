package com.example.myapplication.widget.recyclerview;

import android.support.v7.widget.GridLayoutManager;

/**
 * RecyclerView为GridLayoutManager时，设置了HeaderView，就会用到这个SpanSizeLookup
 * <p>
 * Created by xieH on 2017/3/13 0013.
 */
public class HeaderSpanSizeLookup extends GridLayoutManager.SpanSizeLookup {

    private HeaderAndFooterRecyclerViewAdapter adapter;
    private int mSpanSize = 1;

    public HeaderSpanSizeLookup(HeaderAndFooterRecyclerViewAdapter adapter, int spanSize) {
        this.adapter = adapter;
        this.mSpanSize = spanSize;
    }

    @Override
    public int getSpanSize(int position) {
        boolean isHeaderOrFooter = adapter.isHeader(position) || adapter.isFooter(position);
        return isHeaderOrFooter ? mSpanSize : 1;
    }
}
