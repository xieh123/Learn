package com.example.myapplication.base;

import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.receiver.ForceOfflineReceiver;

import butterknife.ButterKnife;

/**
 * Created by xieH on 2017/3/21 0021.
 */
public abstract class BaseActivity extends AppCompatActivity {

    public static final int STATE_NONE = 0;
    public static final int STATE_REFRESH = 1;
    public static final int STATE_LOAD_MORE = 2;
    public static final int STATE_NO_MORE = 3;
    public static final int STATE_PRESS_NONE = 4; // 正在下拉但还没有到刷新的状态
    public static int mState = STATE_NONE;

    public TextView backTv;
    public TextView titleTv;
    public TextView menuTv;


    private ForceOfflineReceiver mForceOfflineReceiver;

    protected abstract int getLayoutId();

    protected abstract void initView();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            // Translucent status bar
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        ButterKnife.bind(this);
        ActivityCollector.addActivity(this);


        initView();

        initToolbar();
    }

    public void initToolbar() {
//        backTv = (TextView) findViewById(R.id.toolbar_back_tv);
//        titleTv = (TextView) findViewById(R.id.toolbar_title_tv);
//        menuTv = (TextView) findViewById(R.id.toolbar_menu_tv);
//
//        if (backTv != null) {
//            backTv.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    onBackClick();
//                }
//            });
//        }
    }

    public void onBackClick() {
        onBackPressed();
    }

    public void setToolbarTitle(int resId) {
        if (resId != 0) {
            setToolbarTitle(getString(resId));
        }
    }

    public void setToolbarTitle(String title) {
        if (TextUtils.isEmpty(title)) {
            title = getString(R.string.app_name);
        }

        if (titleTv != null) {
            titleTv.setText(title);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.example.myapplication.FORCE_OFFLINE");

        mForceOfflineReceiver = new ForceOfflineReceiver();
        registerReceiver(mForceOfflineReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mForceOfflineReceiver != null) {
            unregisterReceiver(mForceOfflineReceiver);
            mForceOfflineReceiver = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        ActivityCollector.finishActivity(this);
    }
}
