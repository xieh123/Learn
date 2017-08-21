package com.example.myapplication.ui.tab1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.widget.ExpandableTextView;

/**
 * Created by xieH on 2017/5/2 0002.
 */
public class ExpandableTextViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expandable_textview);

        initView();
    }

    public void initView() {
        ExpandableTextView etvLeft = (ExpandableTextView) findViewById(R.id.etv_left);
        ExpandableTextView etvpRight = (ExpandableTextView) findViewById(R.id.etv_right);

        etvLeft.setText(getString(R.string.test_content));
        etvpRight.setText(getString(R.string.test_content));
    }
}
