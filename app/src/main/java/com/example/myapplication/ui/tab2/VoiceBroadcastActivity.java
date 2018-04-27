package com.example.myapplication.ui.tab2;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.util.voice.MoneyFormat;
import com.example.myapplication.util.voice.VoiceUtils;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * Created by xieH on 2017/11/8 0008.
 */
public class VoiceBroadcastActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = VoiceBroadcastActivity.class.getSimpleName();

    private EditText mAmountEt;
    private TextView mTextView;
    private Button mFormatBtn;
    private Button mPlayBtn;


    private EditText mPhoneEt;
    private TextView mTime11Tv, mTime22Tv, mTime33Tv, mTime44Tv;

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


        mPhoneEt = (EditText) findViewById(R.id.phone_et);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mPhoneEt.addTextChangedListener(new PhoneNumberFormattingTextWatcher(Locale.CHINA.getCountry()));
        }

        mTime11Tv = (TextView) findViewById(R.id.time11_tv);
        mTime22Tv = (TextView) findViewById(R.id.time22_tv);
        mTime33Tv = (TextView) findViewById(R.id.time33_tv);
        mTime44Tv = (TextView) findViewById(R.id.time44_tv);

        // 格式化时间，最后参数设定显示的格式
        String date11 = DateUtils.formatDateTime(this,
                System.currentTimeMillis(), DateUtils.FORMAT_SHOW_DATE
                        | DateUtils.FORMAT_SHOW_TIME
                        | DateUtils.FORMAT_SHOW_YEAR
                        | DateUtils.FORMAT_ABBREV_MONTH
                        | DateUtils.FORMAT_SHOW_WEEKDAY);
        mTime11Tv.setText(date11);


        // 返回相对于当前时间的最大区间表示的字符串：几(分钟,小时,天,周,月,年)前/后
        CharSequence date22 = DateUtils.getRelativeTimeSpanString(System.currentTimeMillis() - 1000 * 1000);
        mTime22Tv.setText(date22);

        // 返回相对于当前时间的一个时间字符串：在同一天显示时分；在不同一天，显示月日；在不同一年，显示年月日
        CharSequence date33 = DateUtils.getRelativeTimeSpanString(this, System.currentTimeMillis() + 10000 * 10000);
        mTime33Tv.setText(date33);

        // 返回两个时间值间的相距字符串   // 格式化一个时间区间,比如说电商的一个活动的活动时间就是由开始时间和结束时间组成
        String date44 = DateUtils.formatDateRange(this, System.currentTimeMillis(),
                System.currentTimeMillis() + 60 * 60 * 10000, DateUtils.FORMAT_SHOW_TIME);
        mTime44Tv.setText(date44);


        long currentTimeMillis = System.currentTimeMillis();
        // 两分钟前
        Log.d(TAG, "hh--- " + DateUtils.getRelativeTimeSpanString(currentTimeMillis - 2 * 60 * 1000));
        // 三个小时前
        Log.d(TAG, "hh--- " + DateUtils.getRelativeTimeSpanString(currentTimeMillis - 3 * 60 * 60 * 1000));
        // 一天前
        Log.d(TAG, "hh--- " + DateUtils.getRelativeTimeSpanString(currentTimeMillis - 28 * 60 * 60 * 1000));

        // minResolution：格式化的时候忽略的最小时间,比如说:如果该参数被设置成MINUTE_IN_MILLIS,小于一分钟的时间就会被忽略,传入的时间刚好是3秒前,由于小于一分钟就会被忽略而格式化成"0分钟前"
//        getRelativeTimeSpanString(long time, long now, long minResolution)

        // 判断传入时间是否跟今天是同一天
        //  DateUtils.isToday(long when)


        // 按拼音排序
        List<String> list = new ArrayList<>(Arrays.asList("翁", "啊", "好", "月"));
        Log.d(TAG, "hh--- before sort: " + list);
        Collections.sort(list, Collator.getInstance(Locale.SIMPLIFIED_CHINESE));
        Log.d(TAG, "hh--- after sort: " + list);


    }

    @Override
    public void onClick(View v) {
        String amount = mAmountEt.getText().toString().trim();
        if (!TextUtils.isEmpty(amount)) {
            mTextView.setText(MoneyFormat.format(Double.valueOf(amount)));
            Log.e(TAG, "hh---" + MoneyFormat.format(Double.valueOf(amount)));

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
