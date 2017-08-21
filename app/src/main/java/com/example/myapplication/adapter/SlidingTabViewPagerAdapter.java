package com.example.myapplication.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xieh on 16/4/15.
 */
public class SlidingTabViewPagerAdapter extends FragmentPagerAdapter {

    /**
     * 添加的Fragment的集合
     */
    private final List<Fragment> mFragments = new ArrayList<>();

    /**
     * 每个Fragment对应的title的集合
     */
    private final List<String> mTitles = new ArrayList<>();

    public SlidingTabViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    /**
     * @param fragment 添加Fragment
     * @param title Fragment的标题，即TabLayout中对应Tab的标题
     */
    public void addFragment(Fragment fragment, String title) {
        mFragments.add(fragment);
        mTitles.add(title);
    }

    @Override
    public Fragment getItem(int position) {
        // 得到对应position的Fragment
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        // 返回Fragment的数量
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // 得到对应position的Fragment的title
        return mTitles.get(position);
    }
}
