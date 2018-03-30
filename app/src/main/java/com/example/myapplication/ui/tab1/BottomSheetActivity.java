package com.example.myapplication.ui.tab1;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.myapplication.R;
import com.example.myapplication.widget.FullSheetDialogFragment;

/**
 * Created by xieH on 2017/3/7 0007.
 */
public class BottomSheetActivity extends AppCompatActivity {

    private BottomSheetBehavior mBehavior;

    private View mBottomSheetView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_sheet);

        initView();
    }

    public void initView() {
        mBottomSheetView = findViewById(R.id.bottom_sheets_nestedScrollView);

        mBehavior = BottomSheetBehavior.from(mBottomSheetView);
        mBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                // bottomSheet 状态的改变
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                // 拖拽中的回调，根据slideOffset可以做一些动画
            }
        });
    }

    public void bottomSheet(View v) {
        if (mBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        } else {
            mBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        }
    }

    public void bottomSheetDialog(View v) {
        BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(this);
        View view = View.inflate(this, R.layout.dialog_bottom_sheet, null);
        mBottomSheetDialog.setContentView(view);
        mBottomSheetDialog.show();
    }

    public void bottomSheetDialogFragment(View v) {
        new FullSheetDialogFragment().show(getSupportFragmentManager(), null);
    }
}
