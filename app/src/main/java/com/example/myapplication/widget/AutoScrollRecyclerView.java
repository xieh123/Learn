package com.example.myapplication.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

import java.lang.ref.WeakReference;

/**
 * Created by xieH on 2016/12/20 0020.
 */
public class AutoScrollRecyclerView extends RecyclerView {

    private static final long TIME_AUTO_SCROLL = 20;
    AutoScrollTask mAutoScrollTask;
    private boolean isRunning;      // 标示是否正在自动轮询
    private boolean isNeedToRun;    // 标示是否可以自动轮询,可在不需要的是否置false

    public AutoScrollRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mAutoScrollTask = new AutoScrollTask(this);
    }

    static class AutoScrollTask implements Runnable {
        private final WeakReference<AutoScrollRecyclerView> mReference;

        // 使用弱引用持有外部类引用->防止内存泄漏
        public AutoScrollTask(AutoScrollRecyclerView reference) {
            this.mReference = new WeakReference<AutoScrollRecyclerView>(reference);
        }

        @Override
        public void run() {
            AutoScrollRecyclerView recyclerView = mReference.get();
            if (recyclerView != null && recyclerView.isRunning && recyclerView.isNeedToRun) {
                recyclerView.scrollBy(0, 1);
                recyclerView.postDelayed(recyclerView.mAutoScrollTask, recyclerView.TIME_AUTO_SCROLL);
            }
        }
    }

    /**
     * 开启滚动：如果正在运行,先停止->再开启
     */
    public void start() {
        if (isRunning)
            stop();
        isNeedToRun = true;
        isRunning = true;
        postDelayed(mAutoScrollTask, TIME_AUTO_SCROLL);
    }

    /**
     * 停止滚动
     */
    public void stop() {
        isRunning = false;
        removeCallbacks(mAutoScrollTask);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (isNeedToRun)
                    stop();
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_OUTSIDE:
                if (isNeedToRun)
                    start();
                break;
            default:
                break;
        }

        return super.onTouchEvent(e);
    }
}
