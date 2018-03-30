package com.example.myapplication.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.model.Answer;
import com.example.myapplication.model.Question;
import com.example.myapplication.test.QuestionStruct;

/**
 * Created by xieH on 2018/1/10 0010.
 */
public class TestFragment extends LazyFragment {

    private TextView mTextView;
    private String msg = "";

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_test;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mTextView = (TextView) getContentView().findViewById(R.id.tvMessage);
    }

    public void setData(int state, Question q, Answer a) {

    }

    public void a(QuestionStruct questionStruct){

    }


    @Override
    public void onResume() {
        super.onResume();

        mTextView.setText(msg);
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
