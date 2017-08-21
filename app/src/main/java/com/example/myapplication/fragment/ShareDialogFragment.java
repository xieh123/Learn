package com.example.myapplication.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.myapplication.R;
import com.example.myapplication.ui.tab1.Test01Activity;


/**
 * Created by xieH on 2016/9/7.
 */
public class ShareDialogFragment extends DialogFragment implements View.OnClickListener {

    private View mTest01View, mTest02View;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // 使用不带Theme的构造器, 获得的dialog边框距离屏幕仍有几毫米的缝隙。
        Dialog dialog = new Dialog(getActivity(), R.style.BottomDialog);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Content前设定
        dialog.setContentView(R.layout.fragment_share);
        dialog.setCanceledOnTouchOutside(true); // 外部点击取消

        // 设置宽度为屏宽, 靠近屏幕底部。
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.BOTTOM; // 底部显示
        lp.width = WindowManager.LayoutParams.MATCH_PARENT; // 宽度持平
        window.setAttributes(lp);


        mTest01View = dialog.findViewById(R.id.share_test01_tv);
        mTest02View = dialog.findViewById(R.id.share_test02_tv);


        mTest01View.setOnClickListener(this);
        mTest02View.setOnClickListener(this);

        return dialog;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.share_test01_tv:
                Intent intent = new Intent(getActivity(), Test01Activity.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.share_test02_tv:
                break;
            default:
                break;
        }


        // dismiss 后 onActivityResult 就失效无法接收到数据了
        // this.dismiss();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK)
            return;


        if (requestCode == 1) {

            System.out.println("onActivityResult");


        } else {

        }

        // dismiss 放在这里保证onActivityResult生效
        this.dismiss();
    }
}
