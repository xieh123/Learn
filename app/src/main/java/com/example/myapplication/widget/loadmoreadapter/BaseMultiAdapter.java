package com.example.myapplication.widget.loadmoreadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by xieH on 2017/8/7 0007.
 */
public abstract class BaseMultiAdapter<T> extends BaseAdapter<T> {

    private OnItemClickListener mOnItemClickListener;

    protected abstract int getItemLayoutId(int viewType);

    protected abstract void convert(ViewHolder holder, T item, int position);

    public BaseMultiAdapter(Context context, List<T> datas, boolean isOpenLoadMore) {
        super(context, datas, isOpenLoadMore);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (isNormalItemView(viewType)) {
            return ViewHolder.create(mContext, getItemLayoutId(viewType), parent);
        }
        return super.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        int viewType = holder.getItemViewType();
        if (isNormalItemView(viewType)) {
            convert((ViewHolder) holder, mDatas.get(position), position);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(v, mDatas.get(position), position);
                }
            }
        });
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, Object data, int position);
    }
}
