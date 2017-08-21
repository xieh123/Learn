package com.example.myapplication.utils;

/**
 * Interface to listen move in ItemTouchHelper.Callback
 * Created by Alessandro on 15/01/2016.
 */
public interface ItemTouchCallback {

    /**
     * Called when an item has been dragged
     *
     * @param oldPosition start position
     * @param newPosition end position
     */
    void itemTouchOnMove(int oldPosition, int newPosition);

    void itemTouchOnMoveFinish();

    void onItemDismiss(int position);
}
