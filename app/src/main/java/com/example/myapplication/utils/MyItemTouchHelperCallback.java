package com.example.myapplication.utils;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by Alessandro on 12/01/2016.
 */
public class MyItemTouchHelperCallback extends ItemTouchHelper.Callback {

    ItemTouchCallback itemTouchCallback; // interface

    public MyItemTouchHelperCallback(ItemTouchCallback itemTouchCallback){
        this.itemTouchCallback = itemTouchCallback;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return false;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return false; // swiped disabled
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN; // movements drag
        return makeFlag( ItemTouchHelper.ACTION_STATE_DRAG , dragFlags); // as parameter, action drag and flags drag
    }



    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        itemTouchCallback.itemTouchOnMove(viewHolder.getAdapterPosition(),target.getAdapterPosition()); // information to the interface
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        // swiped disabled
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);

        itemTouchCallback.itemTouchOnMoveFinish();
    }
}
