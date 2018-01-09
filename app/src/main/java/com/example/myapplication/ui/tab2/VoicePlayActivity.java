package com.example.myapplication.ui.tab2;

import android.media.session.PlaybackState;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by xieH on 2017/11/30 0030.
 */
public class VoicePlayActivity extends AppCompatActivity {

    private static final String TAG = "hhh";

    private SimpleExoPlayer mVoicePlayer;

    private boolean isPlay = false;

    private static final String mMusicUrl = "http://odaixbvs7.bkt.clouddn.com/MySoul.mp3";
    private static final String mMusicUrl2 = "http://odaixbvs7.bkt.clouddn.com/Thetruththatyouleave.mp3";
    private static final String mMusicPath = "/sdcard/Music/终于等到你.mp3";

    private static String mFileDir = Environment.getExternalStorageDirectory().getAbsolutePath()
            + File.separator + "HHH";


    private Timeline.Window window;

    ///////////

    private Button mPlayBtn;
    private TextView mCurrentTimeTv;
    private TextView mAllTimeTv;
    private SeekBar mTimeSeekBar;
    private TextView mIndexTv;

    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_play);

        initView();

        initPlayer();
    }

    private void initView() {
        mPlayBtn = (Button) findViewById(R.id.voice_play_bt);
        mCurrentTimeTv = (TextView) findViewById(R.id.voice_play_current_time_tv);
        mAllTimeTv = (TextView) findViewById(R.id.voice_play_all_time_tv);
        mTimeSeekBar = (SeekBar) findViewById(R.id.voice_play_time_seek_bar);
        mIndexTv = (TextView) findViewById(R.id.voice_play_index_tv);

//
//        DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
//        TrackSelection.Factory selectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
//        TrackSelector trackSelector = new DefaultTrackSelector(selectionFactory);
//        mVoicePlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector);
//        try {
//            DataSpec dataSpec = new DataSpec(RawResourceDataSource.buildRawResourceUri(this.resId));
//            RawResourceDataSource rawResourceDataSource = new RawResourceDataSource(this);
//            rawResourceDataSource.open(dataSpec);
//            DataSource.Factory factory = () -> rawResourceDataSource;
//
//            ExtractorMediaSource mediaSource = new ExtractorMediaSource(rawResourceDataSource.getUri(),
//                    factory, new DefaultExtractorsFactory(), null, null);
//            LoopingMediaSource loopingMediaSource = new LoopingMediaSource(mediaSource);
//            mVoicePlayer.prepare(loopingMediaSource);
//            mVoicePlayer.addListener(this);
//            mVoicePlayer.setPlayWhenReady(true);
//        } catch (RawResourceDataSource.RawResourceDataSourceException e) {
//            e.printStackTrace();
//        }


    }

    private void initPlayer() {
        // 1.创建一个默认TrackSelector，测量播放过程中的带宽。 如果不需要，可以为null。
        DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        // 从MediaSource中选出media提供给可用的Render S来渲染,在创建播放器时被注入
        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
        // 2.创建一个默认的LoadControl
        // Create a default LoadControl 控制MediaSource缓存media
        DefaultLoadControl loadControl = new DefaultLoadControl();

        // 3.创建播放器
        mVoicePlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector, loadControl);

        // 设置监听器
        mVoicePlayer.addListener(eventListener);

        //////////////
        DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this, "applicationName"), bandwidthMeter);
        DefaultExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();

        //装载多个资源
        MediaSource[] mediaSources = new MediaSource[4];
//        mediaSources[0] = new ExtractorMediaSource(Uri.parse(mMusicPath), dataSourceFactory, extractorsFactory, null, null);
        mediaSources[0] = new ExtractorMediaSource(Uri.parse(mMusicUrl), dataSourceFactory, extractorsFactory, null, null);
        mediaSources[1] = new ExtractorMediaSource(Uri.parse(mMusicUrl2), dataSourceFactory, extractorsFactory, null, null);
        mediaSources[2] = new ExtractorMediaSource(Uri.parse(mMusicUrl), dataSourceFactory, extractorsFactory, null, null);
        mediaSources[3] = new ExtractorMediaSource(Uri.parse(mMusicUrl2), dataSourceFactory, extractorsFactory, null, null);

        ConcatenatingMediaSource mediaSource = new ConcatenatingMediaSource(mediaSources);

        //设置资源
//        mVoicePlayer.prepare(mediaSource);

        // MediaSource代表要播放的媒体。
//        ExtractorMediaSource mMediaSource = new ExtractorMediaSource(Uri.parse(mMusicUrl), dataSourceFactory, extractorsFactory, null, null);
        mVoicePlayer.prepare(mediaSource);

        window = new Timeline.Window();
    }

    /**
     * 播放事件监听
     */
    private ExoPlayer.EventListener eventListener = new ExoPlayer.EventListener() {
        @Override
        public void onTimelineChanged(Timeline timeline, Object manifest) {
            Log.e(TAG, "播放: onTimelineChanged 周期总数 " + timeline);
        }

        @Override
        public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
            Log.e(TAG, "播放: onTracksChanged");
        }

        @Override
        public void onLoadingChanged(boolean isLoading) {
            Log.e(TAG, "播放: onLoadingChanged  " + mVoicePlayer.getBufferedPosition());


            //设置二级进度条
            mTimeSeekBar.setSecondaryProgress((int) (mVoicePlayer.getBufferedPosition() / 1000));
        }

        @Override
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
            Log.e(TAG, "onPlayerStateChanged: playWhenReady = " + String.valueOf(playWhenReady)
                    + " playbackState = " + playbackState);
            switch (playbackState) {
                case PlaybackState.STATE_PLAYING:
                    //初始化播放点击事件并设置总时长
                    initPlayVideo();
                    Log.e(TAG, "播放状态: 准备 playing");
                    break;
                case PlaybackState.STATE_BUFFERING:
                    Log.e(TAG, "播放状态: 缓存完成 playing");
                    break;
                case PlaybackState.STATE_CONNECTING:
                    Log.e(TAG, "播放状态: 连接 CONNECTING");
                    break;
                case PlaybackState.STATE_ERROR:
                    // 错误
                    Log.e(TAG, "播放状态: 错误 STATE_ERROR");
                    break;
                case PlaybackState.STATE_FAST_FORWARDING:
                    Log.e(TAG, "播放状态: 播放完毕");

                    pausePlay();// 暂停播放
                    break;
                case PlaybackState.STATE_NONE:
                    Log.e(TAG, "播放状态: 无 STATE_NONE");
                    break;
                case PlaybackState.STATE_PAUSED:
                    Log.e(TAG, "播放状态: 暂停 PAUSED");
                    break;
                case PlaybackState.STATE_REWINDING:
                    Log.e(TAG, "播放状态: 倒回 REWINDING");
                    break;
                case PlaybackState.STATE_SKIPPING_TO_NEXT:
                    Log.e(TAG, "播放状态: 跳到下一个");
                    break;
                case PlaybackState.STATE_SKIPPING_TO_PREVIOUS:
                    Log.e(TAG, "播放状态: 跳到上一个");
                    break;
                case PlaybackState.STATE_SKIPPING_TO_QUEUE_ITEM:
                    Log.e(TAG, "播放状态: 跳到指定的Item");
                    break;
                case PlaybackState.STATE_STOPPED:
                    Log.e(TAG, "播放状态: 停止的 STATE_STOPPED");
                    break;
                default:
                    break;
            }
        }

        @Override
        public void onPlayerError(ExoPlaybackException error) {
            Log.e(TAG, "播放: onPlayerError  ");
        }

        @Override
        public void onPositionDiscontinuity() {
            Log.e(TAG, "播放: onPositionDiscontinuity  ");
            resetInfo();
        }

        @Override
        public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
            Log.e(TAG, "播放: onPlaybackParametersChanged  ");
        }
    };

    /**
     * 初始化播放
     */
    private void initPlayVideo() {
        resetInfo();

        mTimeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // 是暂停的开始播放
                if (!mVoicePlayer.getPlayWhenReady()) {
                    continuePlay(); // 继续播放
                }
                mVoicePlayer.seekTo(seekBar.getProgress() * 1000);
            }
        });
    }

    public void resetInfo() {
        mAllTimeTv.setText(getFormatDateTime(mVoicePlayer.getDuration()));
        //设置总时长
        mTimeSeekBar.setMax((int) (mVoicePlayer.getDuration() / 1000));

        mIndexTv.setText(String.format("第%d首", mVoicePlayer.getCurrentWindowIndex() + 1));
    }

    public void play(View v) {
        if (mVoicePlayer.getPlayWhenReady()) {
            pausePlay();// 暂停播放
        } else {
            continuePlay();// 继续播放
        }
    }

    /**
     * 暂停播放
     */
    private void pausePlay() {
        mVoicePlayer.setPlayWhenReady(false);

        isPlay = false;
        mHandler.removeCallbacks(mRunnable);
        mPlayBtn.setText("play");
    }

    /**
     * 继续播放
     */
    private void continuePlay() {
        mVoicePlayer.setPlayWhenReady(true);

        // 开始读取进度
        isPlay = true;
        mHandler.postDelayed(mRunnable, 500);
        mPlayBtn.setText("pause");
    }


    /**
     * 上一首
     */
    public void previous(View v) {
        Timeline timeline = mVoicePlayer.getCurrentTimeline();
        if (timeline.isEmpty()) {
            return;
        }
        int windowIndex = mVoicePlayer.getCurrentWindowIndex();
        timeline.getWindow(windowIndex, window);
        windowIndex--;
        if (windowIndex >= 0) {
            mVoicePlayer.seekTo(windowIndex, C.TIME_UNSET);
            continuePlay();
        } else {
            Toast.makeText(this, "已经是第一首", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * 下一首
     */
    public void next(View v) {
        Timeline timeline = mVoicePlayer.getCurrentTimeline();
        if (timeline.isEmpty()) {
            return;
        }

        int windowIndex = mVoicePlayer.getCurrentWindowIndex();
        Log.e(TAG, "windowIndex:" + windowIndex);
        windowIndex++;
        if (windowIndex < timeline.getWindowCount()) {
            mVoicePlayer.seekTo(windowIndex, C.TIME_UNSET);
            continuePlay();
        } else {
            Toast.makeText(this, "已经最后一首", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * 快退
     */
    private void rewind() {
//        if (rewindMs <= 0) {
//            return;
//        }
//        mVoicePlayer.seekTo(Math.max(mVoicePlayer.getCurrentPosition() - rewindMs, 0));
    }

    /**
     * 快进
     */
    private void fastForward() {
//        if (fastForwardMs <= 0) {
//            return;
//        }
//        long durationMs = mVoicePlayer.getDuration();
//        long seekPositionMs = mVoicePlayer.getCurrentPosition() + fastForwardMs;
//        if (durationMs != C.TIME_UNSET) {
//            seekPositionMs = Math.min(seekPositionMs, durationMs);
//        }
//        mVoicePlayer.seekTo(seekPositionMs);
    }

    /**
     * 重载资源
     *
     * @param url
     */
    private void reLoadSourcePlay(String url) {
//        Log.e(TAG, "重载资源file1---:" + url);
//        //重载资源
//        videoSource.releaseSource();
//        seekBar.setMax(0);
//        seekBar.setProgress(0);
//        allPlayerTime.setText("00:00");
//        ExtractorMediaSource extractorMediaSource =
//                new ExtractorMediaSource(Uri.parse(url), aitripFactory,
//                        extractorsFactory, null, null);
//        mVoicePlayer.prepare(extractorMediaSource);
//        //设置文字
////        allPlayerTime.setText(TimeUtils.secToTime((int) (mVoicePlayer.getDuration() / 1000)));
//        //设置总时长
////        seekBar.setMax((int) (mVoicePlayer.getDuration() / 100));
//        continuePlay();//开始播放
    }

    /**
     * 开启线程读取进度
     */
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            if (isPlay) {
                // 获取播放时间
                mCurrentTimeTv.setText(getFormatDateTime(mVoicePlayer.getCurrentPosition()));

                int currentTime = (int) (mVoicePlayer.getCurrentPosition() / 1000);
                mTimeSeekBar.setProgress(currentTime);
                mHandler.postDelayed(this, 500);
            }
        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 释放资源
        if (mVoicePlayer != null) {
            isPlay = false;
            mVoicePlayer.stop();
            mVoicePlayer.release();
            mVoicePlayer = null;
        }
        mHandler.removeCallbacks(mRunnable);
    }


    private SimpleDateFormat sDateFormat = new SimpleDateFormat("mm:ss");

    public String getFormatDateTime(long dateTime) {
        return sDateFormat.format(new Date(dateTime));
    }

}
