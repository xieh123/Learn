package com.example.myapplication.ui.tab2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.example.myapplication.R;

/**
 * Created by xieH on 2017/11/7 0007.
 */
public class TestActivity extends AppCompatActivity {


    private EditText mEditText;
    private boolean isAuto = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        initView();
    }

    private void initView() {
        mEditText = (EditText) findViewById(R.id.editText);

        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isAuto && before > 0) {
                    mEditText.setText("");
                    isAuto = false;
                } else {

                }

                System.out.println("hh---------" + s + "----------" + start + "-----------" + before + "---------" + count);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

         mEditText.setText("123456");
         isAuto = true;
    }

    public void newActivity(View v) {
        Intent intent = new Intent(this, ViewActivity.class);
        startActivity(intent);
    }
}
