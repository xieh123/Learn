package com.example.myapplication.ui.tab1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.myapplication.R;
import com.example.myapplication.fragment.ShareDialogFragment;

/**
 * Created by xieH on 2017/4/11 0011.
 */
public class FragmentActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
    }

    public void showDialogFragment(View v) {
        ShareDialogFragment shareDialogFragment = new ShareDialogFragment();
        shareDialogFragment.show(getSupportFragmentManager(), null);
    }


}
