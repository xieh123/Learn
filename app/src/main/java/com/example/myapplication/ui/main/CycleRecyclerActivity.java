package com.example.myapplication.ui.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.myapplication.R;
import com.example.myapplication.adapter.CycleRecyclerAdapter;
import com.example.myapplication.model.Item;
import com.example.myapplication.widget.AutoScrollRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xieH on 2016/12/21 0021.
 */
public class CycleRecyclerActivity extends AppCompatActivity {

    AutoScrollRecyclerView mRecyclerView;

    List<Item> itemList = new ArrayList<>();
    CycleRecyclerAdapter mCycleRecyclerAdapter;

    private int[] cardIds = new int[]{R.drawable.card_01, R.drawable.card_02, R.drawable.card_03,
            R.drawable.card_04, R.drawable.card_05, R.drawable.card_06, R.drawable.card_07,
            R.drawable.card_08, R.drawable.card_09, R.drawable.card_10};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cycle_recycler);

        initView();
    }

    public void initView() {

        mRecyclerView = (AutoScrollRecyclerView) findViewById(R.id.cycle_recycler_rv);

        for (int i = 0; i < 10; i++) {

            Item item = new Item();

            item.setResourceId(cardIds[i]);

            itemList.add(item);
        }

        mCycleRecyclerAdapter = new CycleRecyclerAdapter(this, itemList);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mCycleRecyclerAdapter);

        mRecyclerView.start();

    }

    public void test(View v) {
        mRecyclerView.scrollToPosition(0);
    }


    @Override
    protected void onStop() {
        super.onStop();

        mRecyclerView.stop();
    }

    /**
     * 方法1
     * <p>
     * 是否滑到底部
     *
     * @return
     */
    public boolean isSlideToBottom(RecyclerView recyclerView) {

        System.out.println("extent---" + recyclerView.computeVerticalScrollExtent()); // 当前屏幕显示的区域高度     838
        System.out.println("offset---" + recyclerView.computeVerticalScrollOffset()); // 当前屏幕之前滑过的距离     0~8642
        System.out.println("range---" + recyclerView.computeVerticalScrollRange());   // 整个View控件的高度        9480

        if (recyclerView != null) {
            if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset()
                    >= recyclerView.computeVerticalScrollRange())
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


    /**
     * 方法3
     * <p>
     * 是否滑到底部
     * <p>
     * 当屏幕中最后一个子项lastVisibleItemPosition等于所有子项个数totalItemCount - 1，那么RecyclerView就到达了底部。
     * 但是，我在这种方法中发现了极为极端的情况，就是当totalItemCount等于1，而这个子项的高度比屏幕还要高
     *
     * @return
     */
    public static boolean isVisBottom(RecyclerView recyclerView) {

        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

        //屏幕中最后一个可见子项的position
        int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();

        //当前屏幕所看到的子项个数
        int visibleItemCount = layoutManager.getChildCount();

        //当前RecyclerView的所有子项个数
        int totalItemCount = layoutManager.getItemCount();

        //RecyclerView的滑动状态
        int state = recyclerView.getScrollState();
        if (visibleItemCount > 0 && lastVisibleItemPosition == totalItemCount - 1 && state == recyclerView.SCROLL_STATE_IDLE) {
            return true;
        } else {
            return false;
        }
    }


}
