package com.example.myapplication.ui.tab1;

import android.animation.ArgbEvaluator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.internal.NavigationMenuView;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;

import com.example.myapplication.R;
import com.example.myapplication.adapter.SlidingTabViewPagerAdapter;
import com.example.myapplication.base.BaseActivity;
import com.example.myapplication.fragment.Test11Fragment;
import com.example.myapplication.fragment.Test22Fragment;
import com.example.myapplication.transform.DepthPageTransformer;

/**
 * Created by xieH on 2017/3/23 0023.
 */
public class ViewPagerActivity extends BaseActivity {

    private NavigationView mNavigationView;

    private ViewPager viewPager;
    private SlidingTabViewPagerAdapter viewPagerAdapter;
    public TabLayout tabLayout;

    private String[] tabTitles = new String[]{"我的", "发现"};

    private int[] backgroundColors;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_viewpager;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();

        backgroundColors = new int[]{
                ContextCompat.getColor(this, R.color.colorPrimary),
                ContextCompat.getColor(this, R.color.colorAccent),
                ContextCompat.getColor(this, R.color.yellow),
                ContextCompat.getColor(this, R.color.green),
                ContextCompat.getColor(this, R.color.purple)};

    }

    @Override
    public void initView() {
        mNavigationView = (NavigationView) findViewById(R.id.navigationView);
        mNavigationView.inflateHeaderView(R.layout.fragment_find);

        disableNavigationViewScrollbar(mNavigationView);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPagerAdapter = new SlidingTabViewPagerAdapter(getSupportFragmentManager());

        // 添加Fragment
        viewPagerAdapter.addFragment(new MyFragment(), tabTitles[0]);
        viewPagerAdapter.addFragment(new FindFragment(), tabTitles[1]);
        viewPagerAdapter.addFragment(new Test11Fragment(), "test11");
        viewPagerAdapter.addFragment(new Test22Fragment(), "test22");

        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(viewPagerAdapter);

        viewPager.setPageTransformer(true, new DepthPageTransformer());

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);

        // 给TabLayout设置关联ViewPager，如果设置了ViewPager，那么ViewPagerAdapter中的getPageTitle()方法返回的就是Tab上的标题
        tabLayout.setupWithViewPager(viewPager);


//        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
//        tabLayout.getTabAt(0).setText(tabTitles[0]);
//
//        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
//        tabLayout.getTabAt(1).setText(tabTitles[1]);

        // view.performClick();


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                int colorUpdate = (Integer) new ArgbEvaluator().evaluate(positionOffset, backgroundColors[position], backgroundColors[position == 2 ? position : position + 1]);
                viewPager.setBackgroundColor(colorUpdate);
            }

            @Override
            public void onPageSelected(int position) {
                viewPager.setBackgroundColor(backgroundColors[position]);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    /**
     * 去掉滚动条
     *
     * @param navigationView
     */
    private void disableNavigationViewScrollbar(NavigationView navigationView) {
        if (navigationView != null) {
            NavigationMenuView navigationMenuView = (NavigationMenuView) navigationView.getChildAt(0);
            if (navigationMenuView != null) {
                navigationMenuView.setVerticalScrollBarEnabled(false);
            }
        }
    }

}
