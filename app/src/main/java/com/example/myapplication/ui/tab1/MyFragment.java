package com.example.myapplication.ui.tab1;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.myapplication.R;
import com.example.myapplication.fragment.LazyFragment;

/**
 * Created by xieH on 2017/3/23 0023.
 */
public class MyFragment extends LazyFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }
}
