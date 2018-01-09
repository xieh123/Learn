package com.example.myapplication.util;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by xieH on 2017/7/7 0007.
 */
public class SimpleItemTouchHelperCallback extends ItemTouchHelper.Callback {


    private ItemTouchCallback itemTouchCallback;

    public SimpleItemTouchHelperCallback(ItemTouchCallback itemTouchCallback) {
        this.itemTouchCallback = itemTouchCallback;
    }


    /**
     * 这个方法是用来设置我们拖动的方向以及侧滑的方向的
     */
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        // 设置拖拽方向为上下
        final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        // 设置侧滑方向为从左到右和从右到左都可以
        final int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
        // 将方向参数设置进去
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        itemTouchCallback.itemTouchOnMove(viewHolder.getAdapterPosition(), target.getAdapterPosition()); // information to the interface
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        itemTouchCallback.onItemDismiss(viewHolder.getAdapterPosition());
    }
}
