package com.example.myapplication.ui.tab2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.util.voice.MoneyFormat;
import com.example.myapplication.util.voice.VoiceUtils;

/**
 * Created by xieH on 2017/11/8 0008.
 */
public class VoiceBroadcastActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = VoiceBroadcastActivity.class.getSimpleName();

    private EditText mAmountEt;
    private TextView mTextView;
    private Button mFormatBtn;
    private Button mPlayBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_broadcast);

        initView();
    }

    private void initView() {
        mAmountEt = (EditText) findViewById(R.id.editText);
        mTextView = (TextView) findViewById(R.id.textView);
        mFormatBtn = (Button) findViewById(R.id.button);
        mPlayBtn = (Button) findViewById(R.id.button1);

        mFormatBtn.setOnClickListener(this);
        mPlayBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String amount = mAmountEt.getText().toString().trim();
        if (!TextUtils.isEmpty(amount)) {
            mTextView.setText(MoneyFormat.format(Double.valueOf(amount)));
            Log.e(TAG, "hh-----" + MoneyFormat.format(Double.valueOf(amount)));

            switch (v.getId()) {
                case R.id.button:
                    break;
                case R.id.button1:
                    VoiceUtils.getInstance(this).play(amount, false);
                    break;
                default:
                    break;
            }
        }
    }
}
