package com.example.myapplication.listener;

/**
 * Created by xieH on 2017/11/14 0014.
 */
public interface OnDragVHListener {

    /**
     * Item被选中时触发
     */
    void onItemSelected();


    /**
     * Item在拖拽结束/滑动结束后触发
     */
    void onItemFinish();
}
