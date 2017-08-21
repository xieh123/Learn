package com.example.myapplication.ui.main;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.utils.AudioRecordUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class RecordActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView timeTv, recordTv, pauseTv, endTv;
    private ImageView progressIv, playIv;

    private AudioRecordUtils mAudioUtils;

    // 录音最短时间
    private static final int MIN_INTERVAL_TIME = 700;
    // 录音最长时间
    private static final int MAX_INTERVAL_TIME = 60000;

    // 录音的文件夹路径
    private String savePath = null;

    // 录音文件的路径
    private String mAudioPath = null;

    // 录音起始时间
    private long mStartTime;

    private long recordTime;
    private long audioTime;

    private File mAudioFile;

    private Handler mHandler;

    private ObjectAnimator objectAnimator;


    /////

    /**
     * 是否暂停标志位
     **/
    private boolean isPause = false;
    /**
     * 在暂停状态中
     **/
    private boolean inThePause = false;

    /**
     * 记录需要合成的几段amr语音文件
     **/
    private ArrayList<String> amrList = new ArrayList<>();

    private SimpleDateFormat format = new SimpleDateFormat("mm:ss");

    /**
     * 录音播放时的动画背景
     */
    private AnimationDrawable animationDrawable;

    // 提供访问控制音量和钤声模式的操作
    private AudioManager audioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        mHandler = new Handler();
        mAudioUtils = new AudioRecordUtils();

        initView();


    }

    public void initView() {

        timeTv = (TextView) findViewById(R.id.time_tv);
        recordTv = (TextView) findViewById(R.id.record_tv);
        pauseTv = (TextView) findViewById(R.id.pause_tv);
        endTv = (TextView) findViewById(R.id.end_tv);

        progressIv = (ImageView) findViewById(R.id.progress_iv);
        playIv = (ImageView) findViewById(R.id.play_iv);

        recordTv.setOnClickListener(this);
        pauseTv.setOnClickListener(this);
        endTv.setOnClickListener(this);

        playIv.setOnClickListener(this);

        pauseTv.setEnabled(false);


        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        // 设置默认外放模式
        audioManager.setSpeakerphoneOn(true);


        animationDrawable = (AnimationDrawable) playIv.getDrawable();
       // playIv.setImageDrawable(animationDrawable.getFrame(2));

        mAudioUtils.setOnPlayListener(new AudioRecordUtils.OnPlayListener() {
            @Override
            public void stopPlay() {
                animationDrawable.stop();
               // playIv.setImageDrawable(animationDrawable.getFrame(2));

                System.out.println("---stop----");
            }

            @Override
            public void starPlay() {
             //   playIv.setImageDrawable(animationDrawable.getFrame(0));
                animationDrawable.start();

                System.out.println("---start----");
            }
        });

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.record_tv:
                initAudioPath();
                startRecord();
                break;
            case R.id.pause_tv:
                onPauseRecord();
                break;
            case R.id.end_tv:
                stopRecord();
                break;
            case R.id.play_iv:
                playAudio();
                break;
            default:
                break;
        }
    }


    /**
     * 初始化录音文件
     */
    public void initAudioPath() {
        String savePath = mAudioUtils.getSavePath();
        mAudioPath = savePath + System.currentTimeMillis() + ".amr";

        mAudioFile = new File(mAudioPath);
    }

    /**
     * 开始录音
     */
    private void startRecord() {

        mStartTime = System.currentTimeMillis();

        mAudioUtils.setAudioPath(mAudioPath);
        mAudioUtils.startRecord();

        mHandler.postDelayed(mRunnable, 300);

        recordTv.setText("正在录音");
        pauseTv.setEnabled(true);
        endTv.setEnabled(true);
        playIv.setVisibility(View.INVISIBLE);
        startAnimator(progressIv);
    }

    /**
     * 暂停录音
     */
    public void onPauseRecord() {

        isPause = true;
        // 已经暂停过了，再次点击按钮 开始录音，录音状态在录音中
        if (inThePause) {
            audioTime += recordTime;

            startRecord();
            inThePause = false;
            pauseTv.setText("暂停");
        } else { // 正在录音，点击暂停,现在录音状态为暂停
            // 当前正在录音的文件名
            amrList.add(mAudioFile.getPath());
            inThePause = true;
            stopRecording();
            pauseTv.setText("继续");
        }
    }

    /**
     * 停止录音
     */
    private void stopRecord() {

        // 这里写暂停处理的 文件！加上list里面 语音合成起来
        if (isPause) {
            // 在暂停状态按下结束键,处理list就可以了
            if (inThePause) {
                mAudioPath = mAudioUtils.getInputCollection(amrList, false);
            } else { //在正在录音时，处理list里面的和正在录音的语音
                amrList.add(mAudioFile.getPath());
                stopRecording();
                mAudioPath = mAudioUtils.getInputCollection(amrList, true);
            }
            //还原标志位
            isPause = false;
            inThePause = false;
        } else {  //若录音没有经过任何暂停
            stopRecording();
        }

        recordTv.setText("开始录音");
        pauseTv.setText("暂停");
        pauseTv.setEnabled(false);
        endTv.setEnabled(false);
        playIv.setVisibility(View.VISIBLE);
        stopAnimator();

        amrList.clear();
        audioTime = 0l;
    }

    /**
     * 停止正在录音
     */
    private void stopRecording() {

        mHandler.removeCallbacks(mRunnable);

        if (mAudioUtils != null) {
            mAudioUtils.stopRecord();
        }
    }

//    /**
//     * 录音完成（达到最长时间或用户决定录音完成）
//     */
//    private void finishRecord() {
//
//        stopRecording();
//        long intervalTime = System.currentTimeMillis() - mStartTime;
//        if (intervalTime < MIN_INTERVAL_TIME) {
//
//            File file = new File(audioPath);
//            file.delete();
//            return;
//        } else {
//
//        }
//    }

    /**
     * 播放录音
     */
    public void playAudio() {
        mAudioUtils.startPlay(mAudioPath, timeTv);
    }

    public void startAnimator(View view) {
        objectAnimator = ObjectAnimator.ofFloat(view, "rotation", 0f, 360f);
        objectAnimator.setDuration(3000);
        objectAnimator.start();

    }

    public void stopAnimator() {
        if (objectAnimator != null) {
            objectAnimator.cancel();
        }
    }

    /**
     * 根据变化来改变音量控件的背景
     */
    Runnable mRunnable = new Runnable() {

        @Override
        public void run() {

            if (System.currentTimeMillis() - mStartTime >= MAX_INTERVAL_TIME) {
                // 如果超过最长录音时间
                //   finishRecord();

                mHandler.removeCallbacks(mRunnable);
            } else {

                recordTime = System.currentTimeMillis() - mStartTime;

                if (isPause) {
                    timeTv.setText(format.format(new Date(audioTime + recordTime)));
                } else {
                    timeTv.setText(format.format(new Date(recordTime)));
                }

                mHandler.postDelayed(mRunnable, 300);
            }
        }
    };


}
