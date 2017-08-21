package com.example.myapplication.widget.loadMore;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by xieH on 2017/8/7 0007.
 */
public abstract class NormalBaseAdapter<T> extends BaseAdapter<T> {


    protected abstract void convert(ViewHolder holder, T item, int position);

    protected abstract int getItemLayoutId();

    public NormalBaseAdapter(Context context, List<T> datas, boolean isOpenLoadMore) {
        super(context, datas, isOpenLoadMore);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (isNormalItemView(viewType)) {
            return ViewHolder.create(mContext, getItemLayoutId(), parent);
        }

        return super.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = holder.getItemViewType();
        if (isNormalItemView(viewType)) {
            convert((ViewHolder) holder, mDatas.get(position), position);
        }
    }

    @Override
    protected int getViewType(int position, T data) {
        return TYPE_NORMAL_VIEW;
    }
}
