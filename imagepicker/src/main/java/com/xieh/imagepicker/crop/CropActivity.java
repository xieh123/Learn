package com.xieh.imagepicker.crop;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.bumptech.glide.Glide;
import com.xieh.imagepicker.R;
import com.xieh.imagepicker.base.BaseActivity;
import com.xieh.imagepicker.config.SelectOptions;
import com.xieh.imagepicker.util.StreamUtil;

import java.io.FileOutputStream;


/**
 * 裁剪图片
 */
public class CropActivity extends BaseActivity implements View.OnClickListener {
    private CropLayout mCropLayout;
    private static SelectOptions mOption;

    public static void show(Activity mContext, SelectOptions options) {
        Intent intent = new Intent(mContext, CropActivity.class);
        mOption = options;
        mContext.startActivityForResult(intent, 0x04);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.imagepicker_activity_crop;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        initData();
    }

    public void initView() {
        mCropLayout = (CropLayout) findViewById(R.id.cropLayout);
    }

    protected void initData() {
        Glide.with(this)
                .load(mOption.getSelectedImages().get(0))
                .into(mCropLayout.getImageView());

        mCropLayout.setCropWidth(mOption.getCropWidth());
        mCropLayout.setCropHeight(mOption.getCropHeight());
        mCropLayout.start();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tv_crop) {
            Bitmap bitmap = null;
            FileOutputStream os = null;
            try {
                bitmap = mCropLayout.cropBitmap();
                String path = getFilesDir() + "/crop.jpg";
                os = new FileOutputStream(path);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
                os.flush();
                os.close();

                Intent intent = new Intent();
                intent.putExtra("crop_path", path);
                setResult(RESULT_OK, intent);
                finish();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (bitmap != null) bitmap.recycle();
                StreamUtil.close(os);
            }
        } else if (view.getId() == R.id.tv_cancel) {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        mOption = null;
        super.onDestroy();
    }
}
