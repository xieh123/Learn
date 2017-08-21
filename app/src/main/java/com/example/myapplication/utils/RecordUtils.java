package com.example.myapplication.utils;

import android.content.Context;
import android.hardware.Camera;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.view.SurfaceHolder;

import java.io.File;

/**
 * Created by xieH on 2016/12/26 0026.
 */
public class RecordUtils implements SurfaceHolder.Callback {


    private Context mContext;

    /**
     * 屏幕分辨率
     */
    public static final int VIDEO_WITH = 640;
    public static final int VIDEO_HEIGHT = 480;

    /**
     * 视频保存的目录
     */
    private File mVideoFile;

    /**
     * 录制视频
     */
    private MediaRecorder mMediaRecorder;
    private SurfaceHolder mSurfaceHolder;

    private Camera mCamera;

    /**
     * 是否正在录制
     */
    private boolean isRecording;

    private OnSaveSuccessListener onSaveSuccessListener;


    //////////////////////
    private MediaPlayer mPlayer;

    // 是否正在播放
    private boolean isPlaying;

    private OnPlayListener listener;

    public RecordUtils(Context mContext) {
        this.mContext = mContext;
    }

    public void setCamera(Camera camera) {
        this.mCamera = camera;
    }

    public void setSurfaceHolder(SurfaceHolder surfaceHolder) {
        this.mSurfaceHolder = surfaceHolder;
    }

    /**
     * 开始录制
     */
    public void startRecord() {

        // 没有外置存储，停止录制
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return;
        }

        try {
            mMediaRecorder = new MediaRecorder();

            mCamera.unlock();
            mMediaRecorder.setCamera(mCamera);

            // 从相机采集视频
            mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
            // 从麦克风采集音频信息
            mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            // 设置视频格式
            mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);

            mMediaRecorder.setVideoSize(VIDEO_WITH, VIDEO_HEIGHT);

            // 每秒的帧数
            mMediaRecorder.setVideoFrameRate(24);
            // 编码格式
            mMediaRecorder.setVideoEncoder(MediaRecorder.VideoSource.DEFAULT);
            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            // 设置帧频率，然后就清晰了
            mMediaRecorder.setVideoEncodingBitRate(1 * 1024 * 1024 * 100);

            String mVideoPath = getRecorderPath();
            mVideoFile = new File(mVideoPath);

            mMediaRecorder.setOutputFile(mVideoFile.getAbsolutePath());
            mMediaRecorder.setPreviewDisplay(mSurfaceHolder.getSurface());

            // 解决录制视频，播放器横向问题  // 由于我们需要的是竖屏视频，设置相机90度旋转（默认横屏）
            mMediaRecorder.setOrientationHint(90);

            mMediaRecorder.prepare();

            // 正式录制
            mMediaRecorder.start();

            isRecording = true;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 停止录制，并且保存
     */
    public void stopRecordAndSave() {
        if (mMediaRecorder != null && isRecording) {
            try {
                mMediaRecorder.stop();
                isRecording = false;

                mMediaRecorder.release();
                mMediaRecorder = null;

                if (onSaveSuccessListener != null) {
                    onSaveSuccessListener.OnSaveSuccess(mVideoFile.getAbsolutePath());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 停止录制，不保存
     */
    public void stopRecordAndUnSave() {
        if (mMediaRecorder != null && isRecording) {
            try {
                mMediaRecorder.stop();
                isRecording = false;

                mMediaRecorder.release();
                mMediaRecorder = null;

                if (mVideoFile.exists()) {
                    // 不保存，直接删除
                    mVideoFile.delete();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        this.mSurfaceHolder = surfaceHolder;
        if (mPlayer != null) {
            mPlayer.setDisplay(surfaceHolder);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

    public interface OnSaveSuccessListener {
        public void OnSaveSuccess(String filePath);
    }

    public void setOnSaveSuccessListener(OnSaveSuccessListener onSaveSuccessListener) {
        this.onSaveSuccessListener = onSaveSuccessListener;
    }

    /**
     * 录制文件存放目录
     *
     * @return
     */
    private String getRecorderPath() {

        String mFileDir = Environment.getExternalStorageDirectory() +
                File.separator + "PingMinWeiShang" + File.separator + "recorder";

        File file = new File(mFileDir);
        if (!file.exists()) {
            file.mkdirs();
        }

        String mFilePath = file.getAbsolutePath() + File.separator + "video_" + System.currentTimeMillis() + ".mp4";

        // 临时写个文件地址
//        File mFileDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
        return mFilePath;
    }


    /**
     * 开始播放
     *
     * @param filePath 文件路径
     */
    public void startPlay(String filePath) {

        mSurfaceHolder.addCallback(this);

        if (!isPlaying) {
            if (!filePath.equals("")) {
                mPlayer = new MediaPlayer();
                try {
                    mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mPlayer.setDataSource(filePath);

                    mPlayer.prepare();
                    mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mediaPlayer) {
                            mPlayer.start();
                        }
                    });

                    //   mPlayer.setDisplay(mSurfaceHolder);

                    if (listener != null) {
                        listener.starPlay();
                    }
                    isPlaying = true;
                    mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            mPlayer.seekTo(0);
                            mPlayer.start();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            stopPlay();
        }
    }

    /**
     * 停止播放
     */
    public void stopPlay() {

        if (mPlayer != null) {

            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
            isPlaying = false;
            if (listener != null) {
                listener.stopPlay();
            }
        }
    }

    public interface OnPlayListener {
        /**
         * 播放结束时调用
         */
        void stopPlay();

        /**
         * 播放开始时调用
         */
        void starPlay();
    }

    public void setListener(OnPlayListener listener) {
        this.listener = listener;
    }
}
