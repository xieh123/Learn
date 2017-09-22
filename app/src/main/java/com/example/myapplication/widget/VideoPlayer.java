package com.example.myapplication.widget;

import android.content.Context;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.Surface;
import android.view.TextureView;

import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.decoder.DecoderCounters;
import com.google.android.exoplayer2.video.VideoRendererEventListener;

/**
 * Created by xieH on 2017/9/16 0016.
 */
public class VideoPlayer extends TextureView implements VideoRendererEventListener {

    public VideoPlayer(Context context) {
        super(context);
    }

    public VideoPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VideoPlayer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onVideoEnabled(DecoderCounters counters) {

    }

    @Override
    public void onVideoDecoderInitialized(String decoderName, long initializedTimestampMs, long initializationDurationMs) {

    }

    @Override
    public void onVideoInputFormatChanged(Format format) {

    }

    @Override
    public void onDroppedFrames(int count, long elapsedMs) {

    }

    @Override
    public void onVideoSizeChanged(int width, int height, int unAppliedRotationDegrees, float pixelWidthHeightRatio) {
        int viewWidth = getWidth();
        int viewHeight = getHeight();

        float pivotX = viewWidth / 2f;
        float pivotY = viewHeight / 2f;

        Matrix transform = new Matrix();
        transform.postRotate(unAppliedRotationDegrees, pivotX, pivotY);
        if (unAppliedRotationDegrees == 90 || unAppliedRotationDegrees == 270) {
            float viewAspectRatio = (float) viewHeight / viewWidth;
            transform.postScale(1 / viewAspectRatio, viewAspectRatio, pivotX, pivotY);
        }
        setTransform(transform);
    }

    @Override
    public void onRenderedFirstFrame(Surface surface) {

    }

    @Override
    public void onVideoDisabled(DecoderCounters counters) {

    }
}
