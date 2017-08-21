package com.example.myapplication.ui.tab1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.widget.editlayout.EditAdapter;
import com.example.myapplication.widget.editlayout.EditRecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by xieH on 2017/5/8 0008.
 */
public class EditLayoutActivity extends AppCompatActivity {

    private EditAdapter mAdapter;
    private TextView mEditTv;
    private boolean isEdit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editlayout);

        initView();
    }

    public void initView() {

        EditRecyclerView mRecyclerView = (EditRecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        String[] names = getResources().getStringArray(R.array.query_suggestions);
        List<String> mList = new ArrayList<>();
        Collections.addAll(mList, names);
        mAdapter = new EditAdapter(this, mList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mEditTv = (TextView) findViewById(R.id.tv_edit);
        mEditTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEdit) {
                    mEditTv.setText("编辑");
                    mAdapter.closeAll();
                } else {
                    mEditTv.setText("完成");
                    mAdapter.openLeftAll();
                }
                isEdit = !isEdit;
                mAdapter.setEdit(isEdit);
            }
        });


    }
}
