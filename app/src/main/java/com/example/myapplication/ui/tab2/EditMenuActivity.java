package com.example.myapplication.ui.tab2;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.myapplication.R;
import com.example.myapplication.adapter.BaseRecyclerAdapter;
import com.example.myapplication.adapter.ChannelAdapter;
import com.example.myapplication.adapter.RecyclerHolder;
import com.example.myapplication.listener.ItemDragHelperCallback;
import com.example.myapplication.model.ChannelEntity;
import com.example.myapplication.model.Item;
import com.example.myapplication.util.ItemTouchCallback;
import com.example.myapplication.util.SimpleItemTouchHelperCallback;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by xieH on 2017/7/7 0007.
 */
public class EditMenuActivity extends AppCompatActivity implements ItemTouchCallback {

    private RecyclerView mSelectRecyclerView;
    private BaseRecyclerAdapter<Item> mSelectBaseRecyclerAdapter;
    private List<Item> mSelectList = new ArrayList<>();

    private RecyclerView mAllRecyclerView;
    private BaseRecyclerAdapter<Item> mAllBaseRecyclerAdapter;
    private List<Item> mAllList = new ArrayList<>();


    private ItemTouchHelper mItemTouchHelper;


    private LinearLayout mLinearLayout;

    /////////////////

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_menu);

//        initView();
//
//        setData();

        init();
    }

    protected void initView() {
        mLinearLayout = (LinearLayout) findViewById(R.id.linearLayout);

        mSelectRecyclerView = (RecyclerView) findViewById(R.id.select_rv);
        mAllRecyclerView = (RecyclerView) findViewById(R.id.all_rv);

        initAdapter();
        mSelectRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mSelectRecyclerView.setAdapter(mSelectBaseRecyclerAdapter);

        mAllRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mAllRecyclerView.setAdapter(mAllBaseRecyclerAdapter);


        //关联ItemTouchHelper和RecyclerView
//        ItemTouchHelper.Callback callback = new MyItemTouchHelperCallback(this);

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(this);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mSelectRecyclerView);
    }

    private void initAdapter() {
        mSelectBaseRecyclerAdapter = new BaseRecyclerAdapter<Item>(this, mSelectList, R.layout.item_recycler_category) {
            @Override
            public void convert(RecyclerHolder holder, Item item, int position) {
                holder.setText(R.id.item_tv, item.getTitle());
            }
        };

        mAllBaseRecyclerAdapter = new BaseRecyclerAdapter<Item>(this, mAllList, R.layout.item_recycler_category) {
            @Override
            public void convert(RecyclerHolder holder, Item item, int position) {
                holder.setText(R.id.item_tv, item.getTitle());
            }
        };

        mAllBaseRecyclerAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Object data, final int position) {
                final PathMeasure mPathMeasure;
                final float[] mCurrentPosition = new float[2];
                int[] parentLoc = new int[2];
                mLinearLayout.getLocationInWindow(parentLoc);

                int[] startLoc = new int[2];
                view.getLocationInWindow(startLoc);

                final View startView = view;
                startView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
                mAllRecyclerView.removeView(view);
                mLinearLayout.addView(startView);

                final View endView;
                float toX, toY;
                int[] endLoc = new int[2];
                // 进行判断
                int i = mSelectList.size();

                if (i == 0) {
                    toX = view.getWidth();
                    toY = view.getHeight();
                } else if (i % 4 == 0) {
                    endView = mSelectRecyclerView.getChildAt(i - 4);
                    endView.getLocationInWindow(endLoc);
                    toX = endLoc[0] - parentLoc[0];
                    toY = endLoc[1] + view.getHeight() - parentLoc[1];
                } else {
                    endView = mSelectRecyclerView.getChildAt(i - 1);
                    endView.getLocationInWindow(endLoc);
                    toX = endLoc[0] + view.getWidth() - parentLoc[0];
                    toY = endLoc[1] - parentLoc[1];
                }

                float startX = startLoc[0] - parentLoc[0];
                float startY = startLoc[1] - parentLoc[1];

                Path path = new Path();
                path.moveTo(startX, startY);
                path.lineTo(toX, toY);
                mPathMeasure = new PathMeasure(path, false);

                // 属性动画实现
                ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, mPathMeasure.getLength());
                valueAnimator.setDuration(500);
                // 匀速插值器
                valueAnimator.setInterpolator(new LinearInterpolator());
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float value = (Float) animation.getAnimatedValue();
                        // 获取当前点坐标封装到mCurrentPosition
                        mPathMeasure.getPosTan(value, mCurrentPosition, null);
                        startView.setX(mCurrentPosition[0]);
                        startView.setY(mCurrentPosition[1]);
                    }
                });
                valueAnimator.start();

                valueAnimator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        // 默认RecyclerView的动画
                        mAllRecyclerView.setItemAnimator(new DefaultItemAnimator());
                        mSelectRecyclerView.setItemAnimator(new DefaultItemAnimator());
                        mSelectList.add(mSelectList.size(), mAllList.get(position));
                        mAllList.remove(position);
                        // 先更新数据
                        mAllBaseRecyclerAdapter.notifyDataSetChanged();
                        mSelectBaseRecyclerAdapter.notifyDataSetChanged();
                        // 再更新动画
                        mAllBaseRecyclerAdapter.notifyItemRemoved(position);
                        mSelectBaseRecyclerAdapter.notifyItemInserted(mSelectList.size());
                        mLinearLayout.removeView(startView);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {
                    }
                });
            }
        });
    }

    private void setData() {
        for (int i = 0; i < 8; i++) {
            Item item = new Item();
            item.setTitle("热门---" + i);

            mSelectList.add(item);
        }
        mSelectBaseRecyclerAdapter.refresh(mSelectList);

        for (int i = 0; i < 15; i++) {
            Item item = new Item();
            item.setTitle("新闻---" + i);

            mAllList.add(item);
        }
        mAllBaseRecyclerAdapter.refresh(mAllList);
    }

    @Override
    public void itemTouchOnMove(int oldPosition, int newPosition) {
        Collections.swap(mSelectList, oldPosition, newPosition); // change position
        mSelectBaseRecyclerAdapter.notifyItemMoved(oldPosition, newPosition); //notifies changes in adapter, in this case use the notifyItemMoved
    }

    @Override
    public void itemTouchOnMoveFinish() {
        mSelectBaseRecyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemDismiss(int position) {
        // 删除mItemList数据
        mSelectList.remove(position);
        // 删除RecyclerView列表对应item
        mSelectBaseRecyclerAdapter.notifyItemRemoved(position);
    }


    private void init() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        final List<ChannelEntity> items = new ArrayList<>();
        for (int i = 0; i < 18; i++) {
            ChannelEntity entity = new ChannelEntity();
            entity.setName("频道" + i);
            items.add(entity);
        }
        final List<ChannelEntity> otherItems = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            ChannelEntity entity = new ChannelEntity();
            entity.setName("其他" + i);
            otherItems.add(entity);
        }

        GridLayoutManager manager = new GridLayoutManager(this, 4);
        mRecyclerView.setLayoutManager(manager);

        ItemDragHelperCallback callback = new ItemDragHelperCallback();
        final ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(mRecyclerView);

        final ChannelAdapter adapter = new ChannelAdapter(this, helper, items, otherItems);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int viewType = adapter.getItemViewType(position);
                return viewType == ChannelAdapter.TYPE_MY || viewType == ChannelAdapter.TYPE_OTHER ? 1 : 4;
            }
        });
        mRecyclerView.setAdapter(adapter);

//        adapter.setOnMyChannelItemClickListener(new ChannelAdapter.OnMyChannelItemClickListener() {
//            @Override
//            public void onItemClick(View v, int position) {
//                Toast.makeText(ChannelActivity.this, items.get(position).getName(), Toast.LENGTH_SHORT).show();
//            }
//        });
    }

}
