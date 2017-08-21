package com.example.myapplication.widget;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;

/**
 * Created by xieH on 2017/3/7 0007.
 */
public class FullSheetDialogFragment extends BottomSheetDialogFragment implements View.OnClickListener{

    private BottomSheetBehavior mBehavior;

    private Button mCloseBtn;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        View view = View.inflate(getContext(), R.layout.dialog_bottom_sheet, null);
        dialog.setContentView(view);
        mBehavior = BottomSheetBehavior.from((View) view.getParent());

        mCloseBtn = (Button) view.findViewById(R.id.bottom_sheet_close_bt);

        mCloseBtn.setOnClickListener(this);

        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        mBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);//全屏展开
    }

    @Override
    public void onClick(View view) {
        mBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
    }
}
