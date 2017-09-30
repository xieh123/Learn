package com.example.myapplication.ui.tab1;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.example.myapplication.R;
import com.example.myapplication.adapter.SlidingTabViewPagerAdapter;
import com.example.myapplication.fragment.Test11Fragment;
import com.example.myapplication.fragment.Test44Fragment;

/**
 * Created by xieH on 2017/8/18 0018.
 */
public class ViewPager11Activity extends AppCompatActivity {

    private ViewPager mViewPager;
    private SlidingTabViewPagerAdapter mSlidingTabViewPagerAdapter;
    public TabLayout mTabLayout;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager11);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // Translucent status bar
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }


        initView();
    }

    private void initView() {

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mSlidingTabViewPagerAdapter = new SlidingTabViewPagerAdapter(getSupportFragmentManager());

        // 添加Fragment
        mSlidingTabViewPagerAdapter.addFragment(new Test44Fragment(), "test44");
        mSlidingTabViewPagerAdapter.addFragment(new MyFragment(), "test11");
        mSlidingTabViewPagerAdapter.addFragment(new FindFragment(), "test22");
        mSlidingTabViewPagerAdapter.addFragment(new Test11Fragment(), "test33");


        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setAdapter(mSlidingTabViewPagerAdapter);

        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);

        // 给TabLayout设置关联ViewPager，如果设置了ViewPager，那么ViewPagerAdapter中的getPageTitle()方法返回的就是Tab上的标题
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
