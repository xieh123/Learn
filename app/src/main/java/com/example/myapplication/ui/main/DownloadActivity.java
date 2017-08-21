package com.example.myapplication.ui.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.example.myapplication.R;
import com.example.myapplication.download.DownloadService;
import com.example.myapplication.widget.NoticeView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xieH on 2017/1/17 0017.
 */
public class DownloadActivity extends AppCompatActivity {

    private TextSwitcher mSwitcher;

    private int count = 0;

    private String[] contents = new String[]{
            "大促销下单拆福袋，亿万新年红包随便拿",
            "家电五折团，抢十亿无门槛现金红包",
            "星球大战剃须刀首发送200元代金券"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        initView();
    }

    public void initView() {

        NoticeView noticeView = (NoticeView) findViewById(R.id.notice_view);
        List<String> notices = new ArrayList<>();
        notices.add("大促销下单拆福袋，亿万新年红包随便拿");
        notices.add("家电五折团，抢十亿无门槛现金红包");
        notices.add("星球大战剃须刀首发送200元代金券");
        noticeView.addNotice(notices);
        noticeView.startFlipping();

        /////////////////
        mSwitcher = (TextSwitcher) findViewById(R.id.textSwitcher);

        mSwitcher.setFactory(mFactory);

//        Animation in = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
//        Animation out = AnimationUtils.loadAnimation(this, android.R.anim.fade_out);
//        mSwitcher.setInAnimation(in);
//        mSwitcher.setOutAnimation(out);

        mSwitcher.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.notify_in));
        mSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.notify_out));

        ///
        Button nextButton = (Button) findViewById(R.id.button);
        nextButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                count++;
                mSwitcher.setText(contents[count % contents.length] + String.valueOf(count));
            }
        });

        // Set the initial text without an animation
        mSwitcher.setCurrentText("内容内容" + String.valueOf(count));
    }

    private ViewSwitcher.ViewFactory mFactory = new ViewSwitcher.ViewFactory() {

        @Override
        public View makeView() {
            // Create a new TextView
            TextView textView = new TextView(DownloadActivity.this);
            textView.setTextSize(15);
            textView.setTextColor(Color.BLACK);
            return textView;
        }
    };

    public void download(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("您当前使用的不是wifi环境，更新会产生一些网络流量，是否继续下载？");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(DownloadActivity.this, DownloadService.class);
                startService(intent);
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        builder.show();
    }


}
