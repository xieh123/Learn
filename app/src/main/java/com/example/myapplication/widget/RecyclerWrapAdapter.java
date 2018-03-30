package com.example.myapplication.widget;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by xieH on 2018/3/20 0020.
 */
public class RecyclerWrapAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER_VIEW = Integer.MIN_VALUE;

    /**
     * 限定可以添加2个 HeaderView
     */
    private static final int TYPE_FOOTER_VIEW = Integer.MIN_VALUE + 2;

    private RecyclerView.Adapter<RecyclerView.ViewHolder> mInnerAdapter;

    private ArrayList<View> mHeaderViews = new ArrayList<>();

    private ArrayList<View> mFooterViews = new ArrayList<>();


    public RecyclerWrapAdapter(RecyclerView.Adapter adapter) {
        setAdapter(adapter);
    }

    private void setAdapter(RecyclerView.Adapter<RecyclerView.ViewHolder> adapter) {
        this.mInnerAdapter = adapter;
    }

    /**
     * 最多只能添加2个头部
     *
     * @param view
     */
    public void addHeaderView(View view) {
        if (mHeaderViews.size() < 2) {
            mHeaderViews.add(view);
        }
    }

    public void addFooterView(View view) {
        mFooterViews.add(view);
    }

    /**
     * 获取头布局的数量
     *
     * @return
     */
    public int getHeaderViewsCount() {
        return mHeaderViews.size();
    }

    public int getFooterViewsCount() {
        return mFooterViews.size();
    }

    /**
     * 把View直接封装在ViewHolder中，然后我们面向的是ViewHolder这个实例
     * 如果是Header、Footer则使用HeaderViewHolder封装
     * 其他的就不变
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType < TYPE_HEADER_VIEW + getHeaderViewsCount()) {
            return new HeaderViewHolder(mHeaderViews.get(viewType - TYPE_HEADER_VIEW));
        } else if (viewType >= TYPE_FOOTER_VIEW && viewType < Integer.MAX_VALUE / 2) {
            return new HeaderViewHolder(mFooterViews.get(viewType - TYPE_FOOTER_VIEW));
        } else {
            return mInnerAdapter.onCreateViewHolder(parent, viewType - Integer.MAX_VALUE / 2);
        }
    }

    /**
     * 用于适配渲染数据到View中。方法提供给你了一个viewHolder，而不是原来的convertView。
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int headersCount = getHeaderViewsCount();
        if (position >= headersCount) {
            int adjPosition = position - headersCount;
            if (mInnerAdapter != null) {
                int adapterCount = mInnerAdapter.getItemCount();
                if (adjPosition < adapterCount) {
                    mInnerAdapter.onBindViewHolder(holder, adjPosition);
                }
            }
        } else {
            ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
            if (layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
                ((StaggeredGridLayoutManager.LayoutParams) layoutParams).setFullSpan(true);
            }
        }
    }

    /**
     * 将HeaderView、FooterView挂靠到RecyclerView
     *
     * @param recyclerView
     */
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        // 布局是GridLayoutManager所管理
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) manager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    // 如果是HeaderView、FooterView的对象则占据spanCount的位置，否则就只占用1个位置
                    return (isHeaderView(position) || isFooterView(position)) ? gridLayoutManager.getSpanCount() : 1;
                }
            });
        }
    }

    /**
     * 判断是否是Header View的位置
     * 如果是Header View的则返回true否则返回false
     *
     * @param position
     * @return
     */
    public boolean isHeaderView(int position) {
        return position >= 0 && position < mHeaderViews.size();
    }

    /**
     * 判断是否是Footer View的位置
     * 如果是Footer View的位置则返回true否则返回false
     *
     * @param position
     * @return
     */
    public boolean isFooterView(int position) {
        return position < getItemCount() && position >= getItemCount() - mFooterViews.size();
    }

    @Override
    public int getItemCount() {
        if (mInnerAdapter != null) {
            return getHeaderViewsCount() + getFooterViewsCount() + mInnerAdapter.getItemCount();
        } else {
            return getHeaderViewsCount() + getFooterViewsCount();
        }
    }

    @Override
    public int getItemViewType(int position) {
        int headersCount = getHeaderViewsCount();
        if (position < headersCount) {
            // 说明是Header所占用的空间
            return TYPE_HEADER_VIEW + position;
        }
        int adjPosition = position - headersCount;
        int adapterCount = 0;
        if (mInnerAdapter != null) {
            adapterCount = mInnerAdapter.getItemCount();
            if (adjPosition < adapterCount) {
                return mInnerAdapter.getItemViewType(adjPosition) + Integer.MAX_VALUE / 2;
            }
        }

        // 说明是Footer的所占用的空间
        return TYPE_FOOTER_VIEW + position - headersCount - adapterCount;
    }

    @Override
    public long getItemId(int position) {
        int headersCount = getHeaderViewsCount();
        if (mInnerAdapter != null && position >= headersCount) {
            int adjPosition = position - headersCount;
            int adapterCount = mInnerAdapter.getItemCount();
            if (adjPosition < adapterCount) {
                // 不是Header和Footer则返回其itemId
                return mInnerAdapter.getItemId(adjPosition);
            }
        }
        return -1;
    }

    private class HeaderViewHolder extends RecyclerView.ViewHolder {
        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }
}
