package com.example.myapplication.ui.tab1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.widget.ExpandableTextView;

/**
 * Created by xieH on 2017/5/2 0002.
 */
public class ExpandableTextViewActivity extends AppCompatActivity {

    private TextView mContentTv;
    private TextView mExpandTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expandable_textview);

        initView();
    }

    public void initView() {
        ExpandableTextView etvLeft = (ExpandableTextView) findViewById(R.id.etv_left);
        ExpandableTextView etvRight = (ExpandableTextView) findViewById(R.id.etv_right);

        etvLeft.setText(getString(R.string.test_content));
        etvRight.setText(getString(R.string.test_content));

        mContentTv = (TextView) findViewById(R.id.content_tv);
        mExpandTv = (TextView) findViewById(R.id.expand_tv);

        mExpandTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mContentTv.getMaxLines() == 2) {
                    mContentTv.setMaxLines(100);
                    mExpandTv.setText("收起");
                } else {
                    mContentTv.setMaxLines(2);
                    mExpandTv.setText("展开");
                }
            }
        });
    }
}
