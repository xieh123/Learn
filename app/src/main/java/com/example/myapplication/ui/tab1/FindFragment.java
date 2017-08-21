package com.example.myapplication.ui.tab1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;
import com.example.myapplication.fragment.LazyFragment;
import com.example.myapplication.fragment.Test11Fragment;
import com.example.myapplication.fragment.Test22Fragment;
import com.example.myapplication.fragment.Test33Fragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xieH on 2017/3/23 0023.
 */
public class FindFragment extends LazyFragment implements View.OnClickListener {

    Button mFindBtn;

    int index = 0;

    private List<Fragment> mFragmentList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_find;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mFindBtn = (Button) getContentView().findViewById(R.id.find_bt);

        mFindBtn.setOnClickListener(this);

        mFragmentList.add(new Test11Fragment());
        mFragmentList.add(new Test22Fragment());
        mFragmentList.add(new Test33Fragment());
    }

    @Override
    public void onClick(View view) {

        if (index > 2)
            index = 0;

        getFragmentManager().beginTransaction()
                .replace(R.id.find_fl, mFragmentList.get(index))
        .commit();


        index++;
    }

}
