package com.example.myapplication.service;


import android.app.Dialog;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewTreeObserver;

import com.example.myapplication.R;
import com.example.myapplication.adapter.BaseRecyclerAdapter;
import com.example.myapplication.model.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xieH on 2018/4/24 0024.
 */
public class CommentActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    private BaseRecyclerAdapter<Item> mBaseRecyclerAdapter;

    private int previousKeyboardHeight = -1;

    private Dialog dialog = null;

    private List<Item> mItemList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initView();
    }

    private void initView() {
        // 增删改插方法去刷新,如果Item高度固定,增加该属性能够提高效率
        mRecyclerView.setHasFixedSize(true);


        getWindow().getDecorView().getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        Rect rect = new Rect();
                        getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
                        int displayHeight = rect.bottom - rect.top;
                        int height = getWindow().getDecorView().getHeight();
                        int keyboardHeight = height - displayHeight;
                        if (previousKeyboardHeight != keyboardHeight) {
                            boolean hide = (double) displayHeight / height > 0.8;
                            if (hide) {
//                        if (arrays[arrays.size - 1] is BottomClass){
//                            arrays.removeAt(arrays.size - 1)
//                            adapter ?.notifyDataSetChanged()
//                        }
                                dialog.dismiss();
                            }
                        }
                    }
                });

    }

//    private void showInputComment(View view, final int position) {
//        // RV中评论区起始Y的位置
//        final int rvInputY = getY(view);
//        final int rvInputHeight = view.getHeight();
//
//        dialog = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar);
//        dialog.setContentView(R.layout.dialog_comment);
//        dialog.show();
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                // 对话框中的输入框Y的位置
//                int dialogY = getY(dialog.findViewById((R.id.dialog_layout_comment)));
//
//                if (position == mItemList.size() - 1) {
//                    // 更新底部高度为键盘高度
//                }
//
//                mRecyclerView.smoothScrollBy(0, rvInputY - (dialogY - rvInputHeight));
//            }
//        }, 300);
//    }

    private int getY(View view) {
        int[] rect = new int[2];
        view.getLocationOnScreen(rect);
        return rect[1];
    }
}
