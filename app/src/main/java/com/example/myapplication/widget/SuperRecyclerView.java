package com.example.myapplication.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by xieH on 2017/1/11 0011.
 */
public class SuperRecyclerView extends RecyclerView {

    private Context mContext;

    private OnBottomListener onBottomListener;

    public interface OnBottomListener {
        void onBottom();
    }

    public void setOnBottomListener(OnBottomListener onBottomListener) {
        this.onBottomListener = onBottomListener;
    }

    public SuperRecyclerView(Context context) {
        this(context, null);
    }

    public SuperRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SuperRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        this.mContext = context;
    }

    @Override
    public void onScrolled(int dx, int dy) {
        super.onScrolled(dx, dy);

        if (isSlideToBottom()) {
            if (onBottomListener != null) {
                onBottomListener.onBottom();
            }
        }
    }

    /**
     * 方法1
     * <p>
     * 是否滑到底部
     *
     * @return
     */
    public boolean isSlideToBottom() {

        System.out.println("extent---" + this.computeVerticalScrollExtent()); // 当前屏幕显示的区域高度     838
        System.out.println("offset---" + this.computeVerticalScrollOffset()); // 当前屏幕之前滑过的距离     0~8642
        System.out.println("range---" + this.computeVerticalScrollRange());   // 整个View控件的高度        9480

        if (this != null) {
            if (this.computeVerticalScrollExtent() + this.computeVerticalScrollOffset()
                    >= this.computeVerticalScrollRange())
                return true;
        }
        return false;
    }

    /**
     * 方法2
     * <p>
     * 原理跟方法1一样，都是根据computeVerticalScrollExtent()，computeVerticalScrollOffset()，computeVerticalScrollRange()来判断的
     *
     * @param recyclerView
     */
    public void isBottom(RecyclerView recyclerView) {

        boolean canDown = recyclerView.canScrollVertically(1);  // 表示是否能向上滚动，false表示已经滚动到底部
        boolean canUp = recyclerView.canScrollVertically(-1);   // 表示是否能向下滚动，false表示已经滚动到顶部

    }

}
