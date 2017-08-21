package com.example.myapplication.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class LazyFragment extends Fragment {

    protected boolean isVisible;

    private boolean isFirst = true;

//    protected Unbinder unbinder;

    private View mView;

    protected abstract int getLayoutId();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(getLayoutId(), null);
//        unbinder = ButterKnife.bind(this, view);

        return mView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        unbinder.unbind();
    }

    public View getContentView() {
        return mView;
    }

    /**
     * 在ViewPager中使用Fragment时调用
     * <p>
     * 在这里实现Fragment数据的懒加载.
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisible = isVisibleToUser;

        if (isVisibleToUser) {
            if (isFirst) {
                onLazyLoad();
                isFirst = false;
            }
            onVisible();
        } else {
            onInvisible();
        }
    }

//    /**
//     *
//     * @param hidden
//     */
//    @Override
//    public void onHiddenChanged(boolean hidden) {
//        super.onHiddenChanged(hidden);
//        this.isVisible = hidden;
//
//        if (hidden) {
//            if (isFirst) {
//                onLazyLoad();
//                isFirst = false;
//            }
//            onVisible();
//        } else {
//            onInvisible();
//        }
//    }

    protected void onVisible() {

    }

    protected void onInvisible() {

    }

    protected void onLazyLoad() {

    }
}
