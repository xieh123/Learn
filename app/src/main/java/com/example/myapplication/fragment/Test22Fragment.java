package com.example.myapplication.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.myapplication.R;

/**
 * Created by xieH on 2017/4/20 0020.
 */
public class Test22Fragment extends LazyFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_test22;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        System.out.println("hhh-------22---onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        System.out.println("hhh--------22--onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        System.out.println("hhh-------22---onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        System.out.println("hhh-------22---onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        System.out.println("hhh-------22---onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        System.out.println("hhh-------22---onDestroy");
    }

    @Override
    protected void onVisible() {
        super.onVisible();
        System.out.println("hhh-------22---onVisible");
    }

    @Override
    protected void onInvisible() {
        super.onInvisible();
        System.out.println("hhh-------22---onInvisible");
    }

    @Override
    protected void onLazyLoad() {
        super.onLazyLoad();

        System.out.println("hhh-------22---onLazyLoad");
    }
}
