package com.xieh.imagepicker;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.xieh.imagepicker.adapter.ViewPagerAdapter;
import com.xieh.imagepicker.base.BaseActivity;
import com.xieh.imagepicker.util.BitmapUtil;
import com.xieh.imagepicker.util.StreamUtil;

import java.io.File;
import java.util.concurrent.Future;


/**
 * 图片预览Activity
 */
public class ImageGalleryActivity extends BaseActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {

    public static final String KEY_IMAGE = "images";
    public static final String KEY_COOKIE = "cookie_need";
    public static final String KEY_POSITION = "position";
    public static final String KEY_NEED_SAVE = "save";

    private ViewPager mViewPager;
    private TextView mIndexTv;
    private ImageView mSaveIv;

    private String[] mImageSources;
    private int mCurPosition;
    private boolean mNeedSaveLocal;
    private boolean mNeedCookie;

    public static void show(Context context, String images) {
        show(context, images, true);
    }

    public static void show(Context context, String images, boolean needSaveLocal) {
        if (images == null)
            return;
        show(context, new String[]{images}, 0, needSaveLocal);
    }

    public static void show(Context context, String images, boolean needSaveLocal, boolean needCookie) {
        if (images == null)
            return;
        show(context, new String[]{images}, 0, needSaveLocal, needCookie);
    }

    public static void show(Context context, String[] images, int position) {
        show(context, images, position, true);
    }

    public static void show(Context context, String[] images, int position, boolean needSaveLocal) {
        show(context, images, position, needSaveLocal, false);
    }

    public static void show(Context context, String[] images, int position, boolean needSaveLocal, boolean needCookie) {
        if (images == null || images.length == 0)
            return;
        Intent intent = new Intent(context, ImageGalleryActivity.class);
        intent.putExtra(KEY_IMAGE, images);
        intent.putExtra(KEY_POSITION, position);
        intent.putExtra(KEY_NEED_SAVE, needSaveLocal);
        intent.putExtra(KEY_COOKIE, needCookie);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_image_gallery;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();

        initData();
    }

    public void initView() {
        mViewPager = (ViewPager) findViewById(R.id.image_gallery_viewPager);
        mIndexTv = (TextView) findViewById(R.id.image_gallery_index_tv);
        mSaveIv = (ImageView) findViewById(R.id.image_gallery_save_iv);

        mViewPager.addOnPageChangeListener(this);

        if (mNeedSaveLocal) {
            mSaveIv.setOnClickListener(this);
        } else {
            mSaveIv.setVisibility(View.GONE);
        }
    }

    public void initData() {
        Bundle bundle = getIntent().getExtras();

        mImageSources = bundle.getStringArray(KEY_IMAGE);
        mCurPosition = bundle.getInt(KEY_POSITION, 0);
        mNeedSaveLocal = bundle.getBoolean(KEY_NEED_SAVE, true);
        mNeedCookie = bundle.getBoolean(KEY_COOKIE, false);

        int len = mImageSources.length;
        if (mCurPosition < 0 || mCurPosition >= len) {
            mCurPosition = 0;
        }

        // If only one, we not need the text to show
        if (len == 1) {
            mIndexTv.setVisibility(View.GONE);
        }

        mViewPager.setAdapter(new ViewPagerAdapter(this, mImageSources));
        mViewPager.setCurrentItem(mCurPosition);
        // First we call to init the TextView
        onPageSelected(mCurPosition);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.image_gallery_save_iv) {
            saveToFile();
        }
    }

    private void saveToFile() {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(this, "没有外部存储！", Toast.LENGTH_SHORT).show();
            return;
        }

        String path = mImageSources[mCurPosition];

        Object urlOrPath = path;

        // In this save max image size is source
        final Future<File> future = Glide.with(this)
                .load(urlOrPath)
                .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    File sourceFile = future.get();
                    if (sourceFile == null || !sourceFile.exists())
                        return;
                    String extension = BitmapUtil.getExtension(sourceFile.getAbsolutePath());
                    String extDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                            .getAbsolutePath() + File.separator + "MyApplication";

                    File extDirFile = new File(extDir);
                    if (!extDirFile.exists()) {
                        if (!extDirFile.mkdirs()) {
                            // If mk dir error
                            callSaveStatus(false, null);
                            return;
                        }
                    }
                    final File saveFile = new File(extDirFile, String.format("IMG_%s.%s", System.currentTimeMillis(), extension));
                    final boolean isSuccess = StreamUtil.copyFile(sourceFile, saveFile);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            callSaveStatus(isSuccess, saveFile);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void callSaveStatus(boolean success, File savePath) {
        if (success) {
            // notify
            Uri uri = Uri.fromFile(savePath);
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
            Toast.makeText(ImageGalleryActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(ImageGalleryActivity.this, "保存失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mCurPosition = position;
        mIndexTv.setText(String.format("%s/%s", (position + 1), mImageSources.length));
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

}
