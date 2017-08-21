package com.example.myapplication.ui.tab2;

import android.media.session.PlaybackState;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.example.myapplication.R;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

/**
 * Created by xieH on 2017/7/13 0013.
 */
public class GuideActivity extends AppCompatActivity {

    private Button button1, button2, button3, button4, button5;


    private String url = "";
    private MediaSource videoSource;
    private SimpleExoPlayer player;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        initView();

    }

    private void initView() {
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        button5 = (Button) findViewById(R.id.button5);


        // 1.创建一个默认TrackSelector
        DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);

        // 2.创建一个默认的LoadControl
        LoadControl loadControl = new DefaultLoadControl();
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this, "yourApplicationName"), bandwidthMeter);
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
        url = "http://mpv.videocc.net/ce0812b122/a/ce0812b122bf0fb49d79ebd97cbe98fa_1.mp4";
        //test mp4
        videoSource = new ExtractorMediaSource(Uri.parse(url), dataSourceFactory, extractorsFactory, null, null);

        // 3.创建播放器
        player = ExoPlayerFactory.newSimpleInstance(this, trackSelector, loadControl);

        SimpleExoPlayerView simpleExoPlayerView = (SimpleExoPlayerView) findViewById(R.id.simpleExoPlayerView);
        // 将player关联到View上
        simpleExoPlayerView.setPlayer(player);

        // 需要在 prepare 之前添加
        player.addListener(eventListener);

        player.prepare(videoSource);
        // Prepare the player with the source.


    }

    /**
     * 播放事件监听
     */
    private ExoPlayer.EventListener eventListener = new ExoPlayer.EventListener() {
        @Override
        public void onTimelineChanged(Timeline timeline, Object manifest) {
            System.out.println("播放: onTimelineChanged 周期总数 " + timeline);
        }

        @Override
        public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
            System.out.println("播放: TrackGroupArray  ");
        }

        @Override
        public void onLoadingChanged(boolean isLoading) {
            // 设置二级进度条
//            seekBar.setSecondaryProgress((int) (player.getBufferedPosition() / 1000));
        }

        @Override
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
            switch (playbackState) {
                case PlaybackState.STATE_PLAYING:
                    // 初始化播放点击事件并设置总时长
//                    initPlayVideo();
                    System.out.println("播放状态: 准备 playing");
                    break;
                case PlaybackState.STATE_BUFFERING:
                    System.out.println("播放状态: 缓存完成 playing");
                    break;
                case PlaybackState.STATE_CONNECTING:
                    System.out.println("播放状态: 连接 CONNECTING");
                    break;
                case PlaybackState.STATE_ERROR:// 错误
                    System.out.println("播放状态: 错误 STATE_ERROR");
                    break;
                case PlaybackState.STATE_FAST_FORWARDING:
                    System.out.println("播放状态: 快速传递");
                    pausePlay();// 暂停播放
                    break;
                case PlaybackState.STATE_NONE:
                    System.out.println("播放状态: 无 STATE_NONE");
                    break;
                case PlaybackState.STATE_PAUSED:
                    System.out.println("播放状态: 暂停 PAUSED");
                    break;
                case PlaybackState.STATE_REWINDING:
                    System.out.println("播放状态: 倒回 REWINDING");
                    break;
                case PlaybackState.STATE_SKIPPING_TO_NEXT:
                    System.out.println("播放状态: 跳到下一个");
                    break;
                case PlaybackState.STATE_SKIPPING_TO_PREVIOUS:
                    System.out.println("播放状态: 跳到上一个");
                    break;
                case PlaybackState.STATE_SKIPPING_TO_QUEUE_ITEM:
                    System.out.println("播放状态: 跳到指定的Item");
                    break;
                case PlaybackState.STATE_STOPPED:
                    System.out.println("播放状态: 停止的 STATE_STOPPED");
                    break;
                default:
                    break;
            }
        }

        @Override
        public void onPlayerError(ExoPlaybackException error) {
            System.out.println("播放: onPlayerError  ");
        }

        @Override
        public void onPositionDiscontinuity() {
            System.out.println("播放: onPositionDiscontinuity  ");
        }
    };

    /**
     * 暂停播放
     */
    private void pausePlay() {
        player.setPlayWhenReady(false);
    }

    /**
     * 继续播放
     */
    private void continuePlay() {
        player.setPlayWhenReady(true);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.release();
    }
}
