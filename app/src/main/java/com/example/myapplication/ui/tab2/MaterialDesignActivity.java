package com.example.myapplication.ui.tab2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.fragment.MaterialDesignFragment;

/**
 * Created by xieH on 2018/1/24 0024.
 */
public class MaterialDesignActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_design);

        initView();
    }

    private void initView() {

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout, new MaterialDesignFragment())
                .commit();
    }
}
