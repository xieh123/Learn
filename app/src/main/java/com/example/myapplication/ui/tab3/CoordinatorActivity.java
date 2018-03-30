package com.example.myapplication.ui.tab3;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.adapter.SlidingTabViewPagerAdapter;
import com.example.myapplication.fragment.Test22Fragment;

/**
 * Created by xieH on 2018/3/12 0012.
 */
public class CoordinatorActivity extends AppCompatActivity {

    private SlidingTabViewPagerAdapter mSlidingTabViewPagerAdapter;

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator);

        initView();
    }

    private void initView() {
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);

        mSlidingTabViewPagerAdapter = new SlidingTabViewPagerAdapter(getSupportFragmentManager());
        mSlidingTabViewPagerAdapter.addFragment(new Test22Fragment(), "111");
        mSlidingTabViewPagerAdapter.addFragment(new Test22Fragment(), "222");
        mSlidingTabViewPagerAdapter.addFragment(new Test22Fragment(), "333");

        mViewPager.setAdapter(mSlidingTabViewPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);

    }
}
