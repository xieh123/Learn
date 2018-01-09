package com.example.myapplication.ui.tab2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.model.Item;
import com.example.myapplication.util.CacheManager;
import com.example.myapplication.util.MD5Utils;
import com.google.gson.Gson;

/**
 * Created by xieH on 2017/6/27 0027.
 */
public class CacheActivity extends AppCompatActivity {

    private String CACHE_NAME = getClass().getSimpleName();

    private EditText mTitleEt, mUrlEt;
    private TextView mTitleTv, mUrlTv;

    private String title, url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cache);

        CACHE_NAME = MD5Utils.encrypt(CACHE_NAME);

        initView();

    }

    private void initView() {
        mTitleEt = (EditText) findViewById(R.id.title_et);
        mUrlEt = (EditText) findViewById(R.id.url_et);

        mTitleTv = (TextView) findViewById(R.id.title_tv);
        mUrlTv = (TextView) findViewById(R.id.url_tv);

    }

    public void save(View v) {
        title = mTitleEt.getText().toString();
        url = mUrlEt.getText().toString();

        Item item = new Item();

        item.setTitle(title);
        item.setUrl(url);

        CacheManager.saveJsonToFile(this, CACHE_NAME, new Gson().toJson(item).toString());

        Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
    }

    public void read(View v) {
        Item item = CacheManager.readJsonFromFile(this, CACHE_NAME, Item.class);

        if (item != null) {
            mTitleTv.setText(item.getTitle());
            mUrlTv.setText(item.getUrl());
        }

    }
}
