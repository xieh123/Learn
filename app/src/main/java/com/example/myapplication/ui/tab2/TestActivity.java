package com.example.myapplication.ui.tab2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.example.myapplication.R;
import com.example.myapplication.fragment.TestFragment;
import com.example.myapplication.model.Answer;
import com.example.myapplication.model.Question;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xieH on 2017/11/7 0007.
 */
public class TestActivity extends AppCompatActivity {


    private EditText mEditText;
    private boolean isAuto = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        initView();
    }

    private void initView() {
        mEditText = (EditText) findViewById(R.id.editText);

        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isAuto && before > 0) {
                    mEditText.setText("");
                    isAuto = false;
                } else {

                }

                System.out.println("hh---------" + s + "----------" + start + "-----------" + before + "---------" + count);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mEditText.setText("123456");
        isAuto = true;


        //   mHandler.postDelayed(mRunnable, 1000);
    }

    Handler mHandler = new Handler();
    Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            addFragment(null);
            mHandler.postDelayed(mRunnable, 1000);
        }
    };

    public void newActivity(View v) {
        Intent intent = new Intent(this, ViewActivity.class);
        startActivity(intent);
    }

    private boolean isShow = false;
    private TestFragment mTestFragment = new TestFragment();

    private String[] msgs = new String[]{
            "1.2004年的夏季奥运会在哪里举行",
            "2.奥运会五环的颜色自左向右的颜色排序为？",
            "12.在节目 《国家宝藏》 中出现的 《千里江山图》 作者是"
    };

    private int index = 0;

    public void addFragment(View v) {
        Question question = new Question();
        Answer answer = new Answer();

        question.setDesc("hhhhhhhhhhhhhhhhhhhhhh");

        List<String> optionList = new ArrayList<>();
        optionList.add("hrhrhr");
        optionList.add("vdrgfrgr");
        optionList.add("rryhg5y5");

        answer.setOptionList(optionList);
        mTestFragment.setData(1, question, answer);

        if (isShow) {
            getSupportFragmentManager().beginTransaction()
                    .remove(mTestFragment)
                    .commit();
            isShow = false;
        } else {
            if (index >= msgs.length) {
                index = 0;
            }
            mTestFragment.setMsg(msgs[index]);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frameLayout, mTestFragment)
                    .commit();
            isShow = true;
            index++;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mHandler.removeCallbacks(mRunnable);
    }
}
