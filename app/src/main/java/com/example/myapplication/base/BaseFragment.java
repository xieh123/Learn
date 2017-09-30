package com.example.myapplication.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by xieH on 2017/8/10 0010.
 */
public abstract class BaseFragment extends Fragment {

    public static final int STATE_NONE = 0;
    public static final int STATE_REFRESH = 1;
    public static final int STATE_LOAD_MORE = 2;
    public static final int STATE_NO_MORE = 3;
    public static final int STATE_PRESS_NONE = 4; // 正在下拉但还没有到刷新的状态
    public int mState = STATE_NONE;

    protected Unbinder unbinder;

    public boolean isFirst = true;


    protected abstract int getLayoutId();

    protected abstract void initView();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(getLayoutId(), null);
        unbinder = ButterKnife.bind(this, view);

        initView();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /**
     * 使用ViewPager时，实现Fragment数据的懒加载.
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser) {
            if (isFirst) {
                initData();
                isFirst = false;
            }
            onVisible();
        } else {
            onInvisible();
        }
    }

    protected void onVisible() {
    }

    protected void onInvisible() {
    }

    /**
     * 第一次可见时调用
     */
    protected void initData() {
    }
}
