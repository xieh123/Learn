package com.example.myapplication.widget;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by xieH on 2017/3/8 0008.
 */
public class ParallaxRecyclerView extends RecyclerView {

    private Context mContext;

    public ParallaxRecyclerView(Context context) {
        this(context, null);
    }

    public ParallaxRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ParallaxRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;

        init();
    }

    public void init() {
        if (isInEditMode()) // 解决可视化编辑器无法识别自定义控件的问题
            return;

        setLayoutManager(new LinearLayoutManager(mContext));

        // 设置列表的间隔，把间隔设为负数
        addItemDecoration(new ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, State state) {
                super.getItemOffsets(outRect, view, parent, state);

                outRect.bottom = -dp2px(mContext, 10);
            }
        });

        addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                int firstPosition = layoutManager.findFirstVisibleItemPosition();
                int lastPosition = layoutManager.findLastVisibleItemPosition();
                int visibleCount = lastPosition - firstPosition;

                // 重置控件的高度
                int elevation = 1;
                for (int i = firstPosition - 1; i <= (firstPosition + visibleCount) + 1; i++) {
                    View view = layoutManager.findViewByPosition(i);
                    if (view != null) {
                        if (view instanceof CardView) {
                            ((CardView) view).setCardElevation(dp2px(mContext, elevation));
                            elevation += 5;
                        }

                        // 控件复用时，进行复位
//                        float translationY = view.getTranslationY();
//                        if (i > firstPosition && translationY != 0) {
                            view.setTranslationY(0);
//                        }
                    }
                }

                // 第一项增加一个差动效果
                View firstView = layoutManager.findViewByPosition(firstPosition);
                float firstViewTop = firstView.getTop();

                firstView.setTranslationY(-firstViewTop / 2.0f);
            }
        });
    }

    private int dp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
