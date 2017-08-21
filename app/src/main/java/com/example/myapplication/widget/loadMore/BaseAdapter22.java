package com.example.myapplication.widget.loadMore;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by xieH on 2017/8/4 0004.
 */
public abstract class BaseAdapter22<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TYPE_NORMAL_VIEW = 10001;  // 普通类型 item
    public static final int TYPE_FOOTER_VIEW = 10002;  // footer 类型 item
    public static final int TYPE_EMPTY_VIEW = 10003;   // empty view, 即初始化加载时的提示 view

    protected Context mContext;
    protected List<T> mDatas;
    protected final int mItemLayoutId;

    private boolean isOpenLoadMore;    // 是否开启加载更多


    private EmptyLayout mEmptyLayout;          // EmptyLayout
    private LoadingFooterLayout mFooterLayout; // FooterLayout

    private OnLoadMoreListener onLoadMoreListener;

    public abstract void convert(ViewHolder holder, T item, int position);

    public BaseAdapter22(Context context, List<T> datas, int itemLayoutId, boolean isOpenLoadMore) {
        this.mContext = context;
        this.mDatas = datas == null ? new ArrayList<T>() : datas;
        this.mItemLayoutId = itemLayoutId;
        this.isOpenLoadMore = isOpenLoadMore;

        mEmptyLayout = new EmptyLayout(mContext);
        mFooterLayout = new LoadingFooterLayout(mContext);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder = null;
        switch (viewType) {
            case TYPE_NORMAL_VIEW:
                viewHolder = ViewHolder.create(mContext, mItemLayoutId, parent);
                break;
            case TYPE_EMPTY_VIEW:
                viewHolder = ViewHolder.create(getEmptyLayout());
                break;
            case TYPE_FOOTER_VIEW:
                viewHolder = ViewHolder.create(getFooterLayout());
                break;
            default:
                break;
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_NORMAL_VIEW) {
            convert((ViewHolder) holder, mDatas.get(position), position);
        }
    }

    @Override
    public int getItemCount() {
        if (mDatas.isEmpty()) {
            return 1;
        }
        return mDatas.size() + getFooterViewCount();
    }

    @Override
    public int getItemViewType(int position) {
        if (mDatas.isEmpty()) {
            return TYPE_EMPTY_VIEW;
        }

        if (isFooterView(position)) {
            return TYPE_FOOTER_VIEW;
        }

        return TYPE_NORMAL_VIEW;
    }

    /**
     * 根据position 获取data
     *
     * @param position
     * @return
     */
    public T getItem(int position) {
        if (mDatas.isEmpty()) {
            return null;
        }
        return mDatas.get(position);
    }

    /**
     * 是否是 isEmptyView
     *
     * @param position
     * @return
     */
    private boolean isEmptyView(int position) {
        return (getItemViewType(position) == TYPE_EMPTY_VIEW);
    }

    /**
     * 是否是 FooterView
     *
     * @param position
     * @return
     */
    private boolean isFooterView(int position) {
        return isOpenLoadMore && position >= getItemCount() - 1;
    }

    /**
     * 返回 footer view 的数量
     *
     * @return
     */
    private int getFooterViewCount() {
        return isOpenLoadMore && !mDatas.isEmpty() ? 1 : 0;
    }

    /**
     * StaggeredGridLayoutManager模式时，FooterView可占据一行
     *
     * @param holder
     */
    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        if (isEmptyView(holder.getLayoutPosition()) || isFooterView(holder.getLayoutPosition())) {
            ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
            if (layoutParams != null && layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
                StaggeredGridLayoutManager.LayoutParams lp = (StaggeredGridLayoutManager.LayoutParams) layoutParams;
                lp.setFullSpan(true);
            }
        }
    }

    /**
     * GridLayoutManager模式时， FooterView可占据一行，判断RecyclerView是否到达底部
     *
     * @param recyclerView
     */
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        final RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (isEmptyView(position) || isFooterView(position)) {
                        return gridLayoutManager.getSpanCount();
                    }
                    return 1;
                }
            });
        }

        startLoadMore(recyclerView, layoutManager);
    }

    /**
     * 判断列表是否滑动到底部
     *
     * @param recyclerView
     * @param layoutManager
     */
    private void startLoadMore(RecyclerView recyclerView, final RecyclerView.LayoutManager layoutManager) {
        if (!isOpenLoadMore) {
            return;
        }

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == recyclerView.SCROLL_STATE_IDLE) {
                    if (findLastVisibleItemPosition(layoutManager) + 1 >= getItemCount() - getFooterViewCount()) {
                        scrollToLoadMore();
                    }
                }
            }
        });
    }

    private int findLastVisibleItemPosition(RecyclerView.LayoutManager layoutManager) {
        if (layoutManager instanceof LinearLayoutManager) {
            return ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
        } else if (layoutManager instanceof GridLayoutManager) {
            return ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            int[] lastVisibleItemPosition = ((StaggeredGridLayoutManager) layoutManager).findLastCompletelyVisibleItemPositions(null);
            return findMax(lastVisibleItemPosition);
        }

        return -1;
    }

    /**
     * StaggeredGridLayoutManager时，查找position最大的列
     *
     * @param lastVisiblePositions
     * @return
     */
    private int findMax(int[] lastVisiblePositions) {
        int max = lastVisiblePositions[0];
        for (int value : lastVisiblePositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    private void scrollToLoadMore() {
        if (mFooterLayout != null) {
            if (mFooterLayout.getState() == LoadingFooterLayout.State.Normal) {
                if (onLoadMoreListener != null) {
                    onLoadMoreListener.onLoadMore();
                    loadMoreLoading();
                }
            }
        }
    }

    /**
     * 获取 EmptyLayout
     *
     * @return
     */
    public EmptyLayout getEmptyLayout() {
        return mEmptyLayout;
    }

    /**
     * 设置 EmptyLayout
     *
     * @param emptyLayout
     */
    public void setEmptyLayout(EmptyLayout emptyLayout) {
        this.mEmptyLayout = emptyLayout;

        if (mEmptyLayout == null) {
            mEmptyLayout = new EmptyLayout(mContext);
        }
    }

    /**
     * 获取 FooterLayout
     *
     * @return
     */
    public LoadingFooterLayout getFooterLayout() {
        return mFooterLayout;
    }

    /**
     * 设置 FooterLayout
     *
     * @param footerLayout
     */
    public void setFooterLayout(LoadingFooterLayout footerLayout) {
        this.mFooterLayout = footerLayout;

        if (mFooterLayout == null) {
            mFooterLayout = new LoadingFooterLayout(mContext);
        }
    }

    /**
     * 初始化加载中...
     */
    public void loading() {
        mEmptyLayout.setState(EmptyLayout.State.Loading);
    }

    /**
     * 初始化加载完成
     */
    public void loadEnd() {
        mEmptyLayout.setState(EmptyLayout.State.End);
    }

    /**
     * 初始化加载失败
     */
    public void loadError() {
        mEmptyLayout.setState(EmptyLayout.State.Error);
    }

    /**
     * 加载更多
     */
    public void loadMoreNormal() {
        mFooterLayout.setState(LoadingFooterLayout.State.Normal);
    }

    /**
     * 加载更多中...
     */
    public void loadMoreLoading() {
        mFooterLayout.setState(LoadingFooterLayout.State.Loading);
    }

    /**
     * 加载更多完成
     */
    public void loadMoreEnd() {
        mFooterLayout.setState(LoadingFooterLayout.State.End);
    }

    /**
     * 加载更多失败
     */
    public void loadMoreError() {
        mFooterLayout.setState(LoadingFooterLayout.State.Error);
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public void refresh(Collection<T> datas) {
        if (datas == null) {
            mDatas = new ArrayList<>();
        } else if (datas instanceof List) {
            mDatas = (List<T>) datas;
        } else {
            mDatas = new ArrayList<>(datas);
        }

        notifyDataSetChanged();
    }
}
