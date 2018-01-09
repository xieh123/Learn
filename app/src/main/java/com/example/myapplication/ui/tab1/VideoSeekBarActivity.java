package com.example.myapplication.ui.tab1;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.VideoView;

import com.example.myapplication.R;
import com.example.myapplication.util.DensityUtils;
import com.example.myapplication.widget.VideoSeekBarView;

import java.util.HashMap;

/**
 * Created by xieH on 2017/5/23 0023.
 */
public class VideoSeekBarActivity extends AppCompatActivity {

    VideoView mVideoView;

    VideoSeekBarView mVideoSeekBarView;

    LinearLayout mThumbImagesLl;
    private Bitmap[] mThumbBitmaps;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == 1) {
                Bitmap bitmap = (Bitmap) msg.obj;
                ImageView imageView = new ImageView(VideoSeekBarActivity.this);
                imageView.setImageBitmap(bitmap);
                mThumbImagesLl.addView(imageView);
            } else if (msg.what == 2) {
                mVideoSeekBarView.setPlayMarkDuration(mVideoDuration / 1000);
                mVideoSeekBarView.startAnimatePlay();
            }
        }
    };

    /**
     * 视频时长
     */
    private float mVideoDuration;

    private float mStartTime;
    private float mEndTime;

    //////////////////////////////////////
    /////////////////////////////////////
//    private VideoSeekBar mVideoSeekBar;


    private String videoUrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_seekbar);

        initView();

    }

    public void initView() {
        mVideoView = (VideoView) findViewById(R.id.video_crop_videoView);
        mVideoSeekBarView = (VideoSeekBarView) findViewById(R.id.videoSeekBarView);
        mThumbImagesLl = (LinearLayout) findViewById(R.id.video_crop_thumb_image_ll);

        ///////////////////////////////////
        ///////////////////////////////////
//        mVideoSeekBar = (VideoSeekBar) findViewById(R.id.VideoSeekBar);

//        videoUrl = Environment.getExternalStorageDirectory().getAbsolutePath()
//                + File.separator + "HHH" + File.separator + "input.mp4";

        videoUrl = "http://mpv.videocc.net/ce0812b122/a/ce0812b122bf0fb49d79ebd97cbe98fa_1.mp4";


//        mVideoSeekBar.setVideoUrl(true, videoUrl);

        ////////////////////
        ///////////////////////////////////
        //设置视频控制器
//        mVideoView.setMediaController(new MediaController(this));

        //播放完成回调
        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mVideoView.start();
            }
        });

        Uri uri = Uri.parse(videoUrl);
        mVideoView.setVideoURI(uri);
        mVideoView.start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                buildThumbsToLocal(videoUrl, DensityUtils.dp2px(VideoSeekBarActivity.this, 30), DensityUtils.dp2px(VideoSeekBarActivity.this, 60));
            }
        }).start();

        mVideoSeekBarView.setOnMarkMoveListener(new VideoSeekBarView.OnMarkMoveListener() {
            @Override
            public void onMarkMoveListener(int leftMark, int rightMark, int total) {
                mStartTime = (float) leftMark / total * mVideoDuration;
                mEndTime = (float) rightMark / total * mVideoDuration;
            }
        });
    }


    /**
     * 生成缩略图（来自本地视频）
     */
    public Bitmap[] buildThumbsToLocal(String videoUrl, int width, int height) {
        MediaMetadataRetriever mediaRetriever = new MediaMetadataRetriever();
        try {
            if (Build.VERSION.SDK_INT >= 14) {
                mediaRetriever.setDataSource(videoUrl, new HashMap<String, String>());
            } else {
                mediaRetriever.setDataSource(videoUrl);
            }
            // 设置视频的路径

            // 取得视频的长度(单位为毫秒)
            String vTime = mediaRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
            // 保存视频总长度(毫秒)
            mVideoDuration = Float.valueOf(vTime);

            mHandler.sendEmptyMessage(2);

            // 1秒取1帧
            int thumbCount = (int) mVideoDuration / 1000;

            // 初始化缩略图容器
            mThumbBitmaps = new Bitmap[thumbCount];
            // 遍历生成缩略图
            for (int i = 0; i < thumbCount; i++) {
                // 计算时间（秒数）
                long timeUs = i * 1000 * 1000;
                // 获取生成缩略图
                Bitmap bitmap = mediaRetriever.getFrameAtTime(timeUs, MediaMetadataRetriever.OPTION_CLOSEST_SYNC);
                // 保存缩略图
                mThumbBitmaps[i] = ThumbnailUtils.extractThumbnail(bitmap, width, height, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);

                Message message = Message.obtain();
                message.what = 1;
                message.obj = mThumbBitmaps[i];

                mHandler.sendMessage(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // 释放构造器资源
                mediaRetriever.release();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

        return mThumbBitmaps;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 销毁资源
        if (mThumbBitmaps != null) {
            for (int i = 0; i < mThumbBitmaps.length; i++) {
                Bitmap bitmap = mThumbBitmaps[i];
                if (bitmap != null) {
                    if (bitmap != null && !bitmap.isRecycled()) {
                        try {
                            bitmap.recycle();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    bitmap = null;
                }
            }
        }

    }
}
