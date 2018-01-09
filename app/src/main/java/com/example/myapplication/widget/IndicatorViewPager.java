package com.example.myapplication.widget;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.myapplication.R;
import com.example.myapplication.model.Item;
import com.example.myapplication.transform.ImageUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xieH on 2017/9/30 0030.
 */
public class IndicatorViewPager extends FrameLayout {

    private Context mContext;

    private ViewPager mIndicatorViewPager;

    private ViewPagerAdapter mIndicatorViewPagerAdapter;

    private LinearLayout mIndicatorLayout;

    private ImageView[] mIndicatorView;

    private int mCurrentItem;
    private int mCount = 3;

    private Handler mHandler;

    private boolean isPause = false;

    private List<Item> mItemList = new ArrayList<>();

    public IndicatorViewPager(Context context) {
        this(context, null);
    }

    public IndicatorViewPager(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IndicatorViewPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;

        if (!isInEditMode()) {
            init();
        }
    }

    private void init() {
        inflate(mContext, R.layout.layout_indicator_viewpager, this);

        mIndicatorViewPager = (ViewPager) findViewById(R.id.indicator_viewpager);
        mIndicatorLayout = (LinearLayout) findViewById(R.id.indicator_ll);

        mIndicatorViewPager.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        isPause = true;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        isPause = true;
                        break;
                    case MotionEvent.ACTION_UP:
                        isPause = false;
                        break;
                    default:
                        isPause = false;
                        break;
                }
                return false;
            }
        });

        mHandler = new Handler();
    }

    private void initIndicatorLayout() {
        mIndicatorView = new ImageView[mCount];
        mIndicatorLayout.removeAllViews();
        for (int i = 0; i < mCount; i++) {
            mIndicatorView[i] = new ImageView(mContext);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(10, 10);
            params.setMargins(0, 0, 10, 0);
            mIndicatorView[i].setLayoutParams(params);
            mIndicatorView[i].setImageResource(R.drawable.circle_select);

            mIndicatorLayout.addView(mIndicatorView[i]);
        }

        mIndicatorView[0].setImageResource(R.drawable.circle_normal);
    }

    public void initViewPagerListener() {
        mIndicatorViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mCurrentItem = position;

                if (position == 0) {
                    // 当视图在第一个时，将页面号设置为图片的最后一张所在的页面号。
                    mCurrentItem = mCount;
                } else if (position == mCount + 1) {
                    // 当视图在最后一个时,将页面号设置为图片的第一张所在的页面号。
                    mCurrentItem = 1;
                }
                // 设置视图跳转，false:不显示跳转过程的动画
                if (mCurrentItem != position) {
                    mIndicatorViewPager.setCurrentItem(mCurrentItem, false);
                    return;
                }
                // 改变所有导航的背景图片为：未选中
                for (int i = 0; i < mCount; i++) {
                    mIndicatorView[i].setImageResource(R.drawable.circle_normal);
                }
                // 改变当前背景图片为：选中
                mIndicatorView[mCurrentItem - 1].setImageResource(R.drawable.circle_select);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    public void setViewpagerData(List<Item> itemList) {
        if (itemList == null) {
            return;
        }

        this.mItemList = itemList;
        this.mCount = itemList.size();
        initIndicatorLayout();
        initViewPagerListener();

        // 列表前后各添加多一项
        Item item = new Item();
        mItemList.add(0, item);
        mItemList.add(item);

        if (mIndicatorViewPagerAdapter == null) {
            mIndicatorViewPagerAdapter = new ViewPagerAdapter(mContext, mItemList);
        } else {
            mIndicatorViewPagerAdapter.refresh(mItemList);
        }

        mIndicatorViewPager.setAdapter(mIndicatorViewPagerAdapter);
        mIndicatorViewPager.setOffscreenPageLimit(mItemList.size() - 1);
        mIndicatorViewPager.setCurrentItem(1);
    }

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            if (!isPause) {
                int index = mIndicatorViewPager.getCurrentItem();
                index = index % mCount + 1;

                if (index == 1) {
                    mIndicatorViewPager.setCurrentItem(index, false);
                } else {
                    mIndicatorViewPager.setCurrentItem(index, true);
                }
            }

            mHandler.postDelayed(this, 3000);
        }
    };

    public void setAutoScroll(boolean isAutoScroll) {
        if (isAutoScroll) {
            mHandler.postDelayed(mRunnable, 3000);
        } else {
            mHandler.removeCallbacks(mRunnable);
        }
    }

    public class ViewPagerAdapter extends PagerAdapter {
        private Context mContext;

        private List<Item> mItemList;

        public ViewPagerAdapter(Context context, List<Item> itemList) {
            this.mContext = context;
            this.mItemList = itemList;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            final Item item = mItemList.get(position);
            View view = View.inflate(mContext, R.layout.item_indicator_viewpager, null);

            ImageView mImageView = (ImageView) view.findViewById(R.id.item_indicator_viewpager_iv);
            if (!TextUtils.isEmpty(item.getUrl())) {
                ImageUtils.loadImage(mContext, mImageView, item.getUrl());
            }

            mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            return mItemList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }

        public void refresh(List<Item> itemList) {
            if (itemList == null) {
                return;
            }

            this.mItemList = itemList;
            notifyDataSetChanged();
        }
    }

}
