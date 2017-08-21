package com.xieh.imagepicker.crop;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.xieh.imagepicker.util.Util;

public class CropLayout extends FrameLayout {

    private int mCropWidth = 500; // 设置裁剪宽度
    private int mCropHeight = 500; // 设置裁剪高度

    private ZoomImageView mZoomImageView;
    private CropFloatView mCropView;

    public CropLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mZoomImageView = new ZoomImageView(context);
        mCropView = new CropFloatView(context);
        ViewGroup.LayoutParams lp = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        this.addView(mZoomImageView, lp);
        this.addView(mCropView, lp);
    }

    public ZoomImageView getImageView() {
        return mZoomImageView;
    }


    public Bitmap cropBitmap() {
        return mZoomImageView.cropBitmap();
    }

    public void setCropWidth(int mCropWidth) {
        this.mCropWidth = mCropWidth;
        mCropView.setCropWidth(mCropWidth);
        mZoomImageView.setCropWidth(mCropWidth);
    }

    public void setCropHeight(int mCropHeight) {
        this.mCropHeight = mCropHeight;
        mCropView.setCropHeight(mCropHeight);
        mZoomImageView.setCropHeight(mCropHeight);
    }

    public void start() {
        int height = Util.getScreenHeight(getContext());
        int width = Util.getScreenWidth(getContext());
        int mHOffset = (width - mCropWidth) / 2;
        int mVOffset = (height - mCropHeight) / 2;
        mZoomImageView.setHOffset(mHOffset);
        mZoomImageView.setVOffset(mVOffset);
        mCropView.setHOffset(mHOffset);
        mCropView.setVOffset(mVOffset);
    }
}
