package com.example.myapplication.util.voice;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;

import java.io.IOException;

/**
 * Created by xieH on 2017/11/8 0008.
 */
public class VoiceUtils {

    private static volatile VoiceUtils instance = null;

    private Context mContext;

    private MediaPlayer mMediaPlayer = null;
    private AssetFileDescriptor fd = null;

    private boolean isPlaying;

    public VoiceUtils(Context context) {
        this.mContext = context.getApplicationContext();
    }

    public static VoiceUtils getInstance(Context context) {
        if (instance == null) {
            synchronized (VoiceUtils.class) {
                if (instance == null) {
                    instance = new VoiceUtils(context);
                }
            }
        }
        return instance;
    }

    public void play(String amount, boolean isSuccess) {
        String voiceStr = null;
        // 如果是TRUE  就播放"收款成功"这句话
        if (isSuccess) {
            voiceStr = "$" + MoneyFormat.format(Double.valueOf(String.format("%.2f", Double.parseDouble(amount))));
        } else {
            voiceStr = MoneyFormat.format(Double.valueOf(String.format("%.2f", Double.parseDouble(amount))));

        }

        playVoiceList(0, voiceStr);
    }

    public void playVoiceList(final int index, final String voiceStr) {
        setPlaying(true);

        if (mMediaPlayer == null) {
            mMediaPlayer = new MediaPlayer();
        }

        // 播放完成触发此事件
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                int newIndex = index;
                if (index < voiceStr.length() - 1) {
                    mMediaPlayer.reset();
                    newIndex++;
                    playVoiceList(newIndex, voiceStr);
                } else {
                    setPlaying(false);

                    mMediaPlayer.stop();
                    mMediaPlayer.release(); // 释放音频资源
                    mMediaPlayer = null;
                }
            }
        });

        try {
            String voiceChar = voiceStr.substring(index, index + 1);
            switch (voiceChar) {
                case "零":
                    fd = mContext.getAssets().openFd("voice/sound0.wav");
                    break;
                case "壹":
                    fd = mContext.getAssets().openFd("voice/sound1.wav");
                    break;
                case "贰":
                    fd = mContext.getAssets().openFd("voice/sound2.wav");
                    break;
                case "叁":
                    fd = mContext.getAssets().openFd("voice/sound3.wav");
                    break;
                case "肆":
                    fd = mContext.getAssets().openFd("voice/sound4.wav");
                    break;
                case "伍":
                    fd = mContext.getAssets().openFd("voice/sound5.wav");
                    break;
                case "陆":
                    fd = mContext.getAssets().openFd("voice/sound6.wav");
                    break;
                case "柒":
                    fd = mContext.getAssets().openFd("voice/sound7.wav");
                    break;
                case "捌":
                    fd = mContext.getAssets().openFd("voice/sound8.wav");
                    break;
                case "玖":
                    fd = mContext.getAssets().openFd("voice/sound9.wav");
                    break;
                case "拾":
                    fd = mContext.getAssets().openFd("voice/soundshi.wav");
                    break;
                case "佰":
                    fd = mContext.getAssets().openFd("voice/soundbai.wav");
                    break;
                case "仟":
                    fd = mContext.getAssets().openFd("voice/soundqian.wav");
                    break;
                case "角":
                    fd = mContext.getAssets().openFd("voice/soundjiao.wav");
                    break;
                case "分":
                    fd = mContext.getAssets().openFd("voice/soundfen.wav");
                    break;
                case "元":
                    fd = mContext.getAssets().openFd("voice/soundyuan.wav");
                    break;
                case "整":
                    fd = mContext.getAssets().openFd("voice/soundzheng.wav");
                    break;
                case "万":
                    fd = mContext.getAssets().openFd("voice/soundwan.wav");
                    break;
                case "亿":
                    fd = mContext.getAssets().openFd("voice/soundyi.wav");
                    break;
                case "$":
                    fd = mContext.getAssets().openFd("voice/soundsuccess.wav");
                    break;
                default:
                    break;
            }

            mMediaPlayer.setDataSource(fd.getFileDescriptor(), fd.getStartOffset(), fd.getLength());
        } catch (IOException e) {
            e.printStackTrace();
        }

        //下面这三句是控制语速，但是只适用于Android6.0 以上，以下的就会报错，所以这个功能下次更新时解决
//        PlaybackParams pbp = new PlaybackParams();
//        pbp.setSpeed(1.5F);
//        mediaPlayer.setPlaybackParams(pbp);

        try {
            mMediaPlayer.prepare();
            mMediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setPlaying(boolean playing) {
        this.isPlaying = playing;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

}
