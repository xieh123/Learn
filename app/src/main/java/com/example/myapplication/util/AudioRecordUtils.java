package com.example.myapplication.util;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.Handler;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * Created by xieh on 2015/12/30.
 */
public class AudioRecordUtils {

    private final static String TAG = "AudioUtil";

    // 要播放的声音的路径
    private String mAudioPath;

    // 是否正在录音
    private boolean isRecording;
    // 是否正在播放
    private boolean isPlaying;

    private MediaRecorder mRecorder = null;
    private MediaPlayer mPlayer;
    private OnPlayListener listener;


    private TextView timeView;
    private Handler handler = new Handler();

    private SimpleDateFormat format = new SimpleDateFormat("mm:ss");


    /**
     * 录音保存的根路径
     *
     * @return
     */
    public String getSavePath() {

        String savePath = Environment
                .getExternalStorageDirectory().getAbsolutePath()
                + File.separator
                + "HHH"
                + File.separator
                + "Audio"
                + File.separator;

        File file = new File(savePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        return savePath;
    }

    /**
     * 设置录音文件的路径
     *
     * @param mAudioPath
     */
    public void setAudioPath(String mAudioPath) {
        this.mAudioPath = mAudioPath;
    }

    /**
     * 播放声音结束时调用
     *
     * @param listener
     */
    public void setOnPlayListener(OnPlayListener listener) {
        this.listener = listener;
    }

    // 初始化 录音器
    private void initRecorder() {
        if (mRecorder == null) {
            mRecorder = new MediaRecorder();
            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mRecorder.setOutputFile(mAudioPath);
            isRecording = true;
        }
    }

    /**
     * 开始录音，并保存到文件中
     */
    public void startRecord() {
        initRecorder();
        try {
            mRecorder.prepare();
            mRecorder.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 停止录音
     */
    public void stopRecord() {
        if (mRecorder != null) {
            try {
                mRecorder.stop();
                mRecorder.release();
                mRecorder = null;
                isRecording = false;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 停止播放
     */
    public void stopPlay() {

        handler.removeCallbacks(runnable);

        if (mPlayer != null) {

            if (timeView != null) {
//                int len = mPlayer.getDuration() / 1000;
//                if (len < 10) {
//
//                } else {
//                    timeView.setText(len);
//                }

                timeView.setText(format.format(new Date(mPlayer.getDuration())));
            }

            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
            isPlaying = false;
            if (listener != null) {
                listener.stopPlay();
            }
        }
    }

    /**
     * 暂停播放
     */
    public void pausePlay() {

        handler.removeCallbacks(runnable);

        if (mPlayer != null && mPlayer.isPlaying()) {
            mPlayer.pause();
        }
    }

    /**
     * 继续播放
     */
    public void resumePlay() {

        handler.postDelayed(runnable, 1000);

        if (mPlayer != null) {
            mPlayer.start();
        }
    }


    /**
     * 开始播放
     *
     * @param audioPath 录音文件路径
     * @param timeView  倒计时TextView
     */
    public void startPlay(String audioPath, TextView timeView) {

        this.timeView = timeView;

        if (!isPlaying) {
            if (!audioPath.equals("")) {
                mPlayer = new MediaPlayer();
                try {
                    mPlayer.setDataSource(audioPath);
                    mPlayer.prepare();
                    if (timeView != null) {
//                        int len = (mPlayer.getDuration() + 500) / 1000;
//                        timeView.setText(len + "\"");

                        timeView.setText("00:00");
                        handler.postDelayed(runnable, 1000);
                    }
                    mPlayer.start();
                    if (listener != null) {
                        listener.starPlay();
                    }
                    isPlaying = true;
                    mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            stopPlay();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {

            }
        } else {
            stopPlay();
        }
    }

    public boolean isPlaying() {
        return isPlaying;
    }


    Runnable runnable = new Runnable() {
        @Override
        public void run() {
//            int len = mPlayer.getCurrentPosition() / 1000;
//            if (len < 10) {
//                timeView.setText("0" + len + "\"");
//            } else {
//                timeView.setText(len + "\"");
//            }

            timeView.setText(format.format(new Date(mPlayer.getCurrentPosition())));

            handler.postDelayed(runnable, 1000);
        }
    };

    /**
     * 开始播放
     */
    public void startPlay() {
        startPlay(mAudioPath, null);
    }

    public interface OnPlayListener {
        /**
         * 播放录音结束时调用
         */
        void stopPlay();

        /**
         * 播放录音开始时调用
         */
        void starPlay();
    }


    ////////////////////////////////

    /**
     * @param list
     * @param isAddLastRecord 是否需要添加list之外的最新录音，一起合并
     * @return 将合并的流用字符保存, 返回文件地址
     */
    public String getInputCollection(List list, boolean isAddLastRecord) {

        String savePath = getSavePath();
        String mAudioPath = savePath + System.currentTimeMillis() + ".amr";

        // 创建音频文件,合并的文件放这里
        File file1 = new File(mAudioPath);
        FileOutputStream fileOutputStream = null;
        if (!file1.exists()) {
            try {
                file1.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        try {
            fileOutputStream = new FileOutputStream(file1);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //list里面为暂停录音 所产生的 几段录音文件的名字，中间几段文件的减去前面的6个字节头文件
        for (int i = 0; i < list.size(); i++) {
            File file = new File((String) list.get(i));

            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                byte[] myByte = new byte[fileInputStream.available()];
                //文件长度
                int length = myByte.length;

                //头文件
                if (i == 0) {
                    while (fileInputStream.read(myByte) != -1) {
                        fileOutputStream.write(myByte, 0, length);
                    }
                } else {  //之后的文件，去掉头文件就可以了
                    while (fileInputStream.read(myByte) != -1) {
                        fileOutputStream.write(myByte, 6, length - 6);
                    }
                }

                fileOutputStream.flush();
                fileInputStream.close();

                System.out.println("合成文件长度：" + file1.length());
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        try {
            fileOutputStream.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return mAudioPath;
    }


}
