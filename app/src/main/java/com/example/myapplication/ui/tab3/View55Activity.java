package com.example.myapplication.ui.tab3;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.myapplication.R;

/**
 * Created by xieH on 2018/4/3 0003.
 */
public class View55Activity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view55);

        initView();
    }

    private void initView() {


//        TextUtils.isDigitsOnly()  是否是纯数字

        // 增加文本，不要再使用getText()方法拿到旧的字符串再拼接
        // textView.append("bbb");


        // 利用 context 和该style生成 ContextThemeWrapper,利用ContextThemeWrapper生产Builder对象
//        ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(this, R.style.BaseDialog);
//        AlertDialog.Builder builder = new AlertDialog.Builder(contextThemeWrapper);
//        Dialog dialog = builder.create();
    }
}
