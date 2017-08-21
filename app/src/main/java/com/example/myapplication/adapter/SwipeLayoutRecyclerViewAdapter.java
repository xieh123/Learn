package com.example.myapplication.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.model.Item;
import com.example.myapplication.widget.SwipeDeleteLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xieH on 2017/3/8 0008.
 */
public class SwipeLayoutRecyclerViewAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<Item> itemList;
    private boolean isEdit;

    private ArrayList<SwipeDeleteLayout> allItems = new ArrayList<>();
    private ArrayList<SwipeDeleteLayout> mLeftOpenItems = new ArrayList<>();
    private ArrayList<SwipeDeleteLayout> mRightOpenItems = new ArrayList<>();

    private static final long delayClickTime = 500;
    private long lastClickTime;

    public SwipeLayoutRecyclerViewAdapter(Context context, List<Item> itemList) {
        this.mContext = context;
        this.itemList = itemList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_swipe, parent, false);
        ViewHolder holder = new ViewHolder(view);
        holder.swipeDeleteLayout = (SwipeDeleteLayout) view.findViewById(R.id.sdl);

        holder.vPreDelete = view.findViewById(R.id.fl_predelete);
        holder.vDelete = view.findViewById(R.id.fl_delete);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ViewHolder viewHolder = (ViewHolder) holder;

        final SwipeDeleteLayout layout = viewHolder.swipeDeleteLayout;

        viewHolder.vPreDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeRightOpenToLeftAll(layout);
                layout.rightOpen();
            }
        });

        layout.setEdit(isEdit);

        viewHolder.vDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long currentTime = System.currentTimeMillis();
                if (currentTime - lastClickTime < delayClickTime) {
                    return;
                }
                itemList.remove(position);
                notifyItemRemoved(position);
                if (position != itemList.size()) {
                    notifyItemRangeChanged(position, itemList.size() - position);
                }
                lastClickTime = currentTime;
            }
        });

        layout.setOnDragStateChangeListener(new SwipeDeleteLayout.OnDragStateChangeListener() {

            @Override
            public void onPreExecuted(SwipeDeleteLayout layout) {
                allItems.add(layout);
            }

            @Override
            public void onDragging() {

            }

            @Override
            public void onStartRightOpen(SwipeDeleteLayout layout) {
                if (!mRightOpenItems.contains(layout)) {
                    mRightOpenItems.add(layout);
                }
            }

            @Override
            public void onClose(SwipeDeleteLayout layout) {
                if (mLeftOpenItems.contains(layout)) {
                    mLeftOpenItems.remove(layout);
                } else if (mRightOpenItems.contains(layout)) {
                    mRightOpenItems.remove(layout);
                }
            }

            @Override
            public void onLeftOpen(SwipeDeleteLayout layout) {
                mLeftOpenItems.add(layout);
            }

            @Override
            public void onRightOpen(SwipeDeleteLayout layout) {
                closeRightOpenToLeftAll(layout);
                if (!mRightOpenItems.contains(layout)) {
                    mRightOpenItems.add(layout);
                }
            }
        });

    }

    public void closeAll() {
        for (SwipeDeleteLayout layout : allItems) {
            layout.close(true);
        }
    }

    public void openLeftAll() {
        for (SwipeDeleteLayout sl : allItems) {
            sl.leftOpen(true);
        }
    }

    /**
     * 将所有右侧打开的item变为左侧打开
     *
     * @param unCloseLayout 不关闭的layout
     */
    public void closeRightOpenToLeftAll(SwipeDeleteLayout unCloseLayout) {
        for (SwipeDeleteLayout layout : mRightOpenItems) {
            if (layout != unCloseLayout) {
                layout.leftOpen(true);
            }
        }
        mRightOpenItems.clear();
    }

    public void setEdit(boolean isEdit) {
        this.isEdit = isEdit;
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {
        SwipeDeleteLayout swipeDeleteLayout;
        View vPreDelete;
        View vDelete;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}

