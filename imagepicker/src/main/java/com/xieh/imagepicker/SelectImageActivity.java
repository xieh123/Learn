package com.xieh.imagepicker;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xieh.imagepicker.adapter.ImageAdapter;
import com.xieh.imagepicker.base.BaseActivity;
import com.xieh.imagepicker.bean.Image;
import com.xieh.imagepicker.bean.ImageFolder;
import com.xieh.imagepicker.config.SelectOptions;
import com.xieh.imagepicker.crop.CropActivity;
import com.xieh.imagepicker.util.DialogHelper;
import com.xieh.imagepicker.util.Util;
import com.xieh.imagepicker.widget.ImageFolderView;
import com.xieh.imagepicker.widget.SpaceGridItemDecoration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * 图片选择器
 */
public class SelectImageActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks, View.OnClickListener {

    private static final int RC_CAMERA_PERM = 0x03;
    private static final int RC_EXTERNAL_STORAGE = 0x04;

    private ImageView mBackIv;
    private TextView mDoneTv;

    private LinearLayout mSelectFolderLl;
    private TextView mSelectFolderTv;
    private TextView mPreviewTv;

    private ImageFolderView mImageFolderView;

    private RecyclerView mRecyclerView;
    private ImageAdapter mImageAdapter;
    private List<Image> mImageList = new ArrayList<>();

    private List<Image> mSelectedImages;

    private String mCamImageName;

    private List<ImageFolder> mImageFolderList = new ArrayList<>();

    private static SelectOptions mOption;

    public static void show(Context context, SelectOptions options) {
        mOption = options;
        context.startActivity(new Intent(context, SelectImageActivity.class));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.imagepicker_activity_select_image;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestExternalStorage();

        initView();
    }

    public void initView() {
        mBackIv = (ImageView) findViewById(R.id.toolbar_back_iv);
        mDoneTv = (TextView) findViewById(R.id.toolbar_done_tv);

        mSelectFolderLl = (LinearLayout) findViewById(R.id.select_image_folder_ll);
        mSelectFolderTv = (TextView) findViewById(R.id.select_image_folder_tv);

        mPreviewTv = (TextView) findViewById(R.id.select_image_preview_tv);
        mImageFolderView = (ImageFolderView) findViewById(R.id.select_image_folder_view);

        mRecyclerView = (RecyclerView) findViewById(R.id.select_image_rv);

        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mRecyclerView.addItemDecoration(new SpaceGridItemDecoration(Util.dipTopx(this, 1)));

        mImageAdapter = new ImageAdapter(this, mImageList);
        mRecyclerView.setAdapter(mImageAdapter);
        mRecyclerView.setItemAnimator(null);

        mImageAdapter.setOnItemClickListener(new ImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Object data, int position) {
                if (mOption.isHasCam()) {
                    if (position != 0) {
                        handleSelectChange(position);
                    } else {
                        if (mSelectedImages.size() < mOption.getSelectCount()) {
                            requestCamera();
                        } else {
                            Toast.makeText(SelectImageActivity.this, "最多只能选择 " + mOption.getSelectCount() + " 张图片", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    handleSelectChange(position);
                }
            }
        });

        mBackIv.setOnClickListener(this);
        mDoneTv.setOnClickListener(this);
        mSelectFolderLl.setOnClickListener(this);
        mPreviewTv.setOnClickListener(this);
    }

    public void initData() {
        if (mOption == null) {
            mOption = new SelectOptions.Builder()
                    .setHasCam(true)
                    .setSelectCount(9)
                    .setSelectedImages(new String[2])
                    .build();
        }

        mSelectedImages = new ArrayList<>();

        if (mOption.getSelectCount() > 1 && mOption.getSelectedImages() != null) {
            List<String> images = mOption.getSelectedImages();
            for (String s : images) {
                // checkShare file exists
                if (s != null && new File(s).exists()) {
                    Image image = new Image();
                    image.setSelect(true);
                    image.setPath(s);
                    mSelectedImages.add(image);
                }
            }
        }

        getSupportLoaderManager().initLoader(0, null, mCursorLoader);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.toolbar_back_iv) {
            finish();
        } else if (v.getId() == R.id.toolbar_done_tv) {
            mOption.getCallback().doSelected(Util.toArrayList(mSelectedImages));
            SelectImageActivity.this.finish();
        } else if (v.getId() == R.id.select_image_folder_ll) {
            showPopupFolderList();
        } else if (v.getId() == R.id.select_image_preview_tv) {
            if (mSelectedImages.size() > 0) {
                ImageGalleryActivity.show(SelectImageActivity.this, Util.toArray(mSelectedImages), 0, false);
            }
        }
    }

    private void handleSelectSizeChange(int size) {
        if (size > 0) {
            mPreviewTv.setEnabled(true);
            mDoneTv.setEnabled(true);
            mDoneTv.setText(String.format("%s(%s/%s)", "完成", size, mOption.getSelectCount()));
            mPreviewTv.setText(String.format("%s(%s)", "预览", size));
        } else {
            mPreviewTv.setEnabled(false);
            mDoneTv.setEnabled(false);
            mDoneTv.setText("完成");
            mPreviewTv.setText("预览");
        }
    }

    private void handleSelectChange(int position) {
        Image image = mImageList.get(position);
        // 如果是多选模式
        final int selectCount = mOption.getSelectCount();
        if (selectCount > 1) {
            if (image.isSelect()) {
                image.setSelect(false);
                mSelectedImages.remove(image);
                mImageAdapter.notifyItemChanged(position);
            } else {
                if (mSelectedImages.size() == selectCount) {
                    Toast.makeText(SelectImageActivity.this, "最多只能选择 " + selectCount + " 张照片", Toast.LENGTH_SHORT).show();
                } else {
                    image.setSelect(true);
                    mSelectedImages.add(image);
                    mImageAdapter.notifyItemChanged(position);
                }
            }
            handleSelectSizeChange(mSelectedImages.size());
        } else {
            mSelectedImages.add(image);
            handleResult();
        }
    }

    private void handleResult() {
        if (mSelectedImages.size() != 0) {
            if (mOption.isCrop()) {
                List<String> selectedImage = mOption.getSelectedImages();
                selectedImage.clear();
                selectedImage.add(mSelectedImages.get(0).getPath());
                mSelectedImages.clear();
                CropActivity.show(this, mOption);
            } else {
                mOption.getCallback().doSelected(Util.toArrayList(mSelectedImages));
                finish();
            }
        }
    }

    /**
     * 完成选择
     */
    public void onSelectComplete() {
        handleResult();
    }

    /**
     * 创建弹出的相册
     */
    private void showPopupFolderList() {
        mImageFolderView.setCallback(new ImageFolderView.Callback() {
            @Override
            public void onSelect(ImageFolder model) {
                addImagesToAdapter(model.getImages());
                mRecyclerView.scrollToPosition(0);
                mSelectFolderTv.setText(model.getName());
            }
        });
        mImageFolderView.setAdapterData(mImageFolderList);
        mImageFolderView.toggle();
    }

    /**
     * 打开相机
     */
    private void toOpenCamera() {
        // 判断是否挂载了SD卡
        mCamImageName = null;
        String savePath = "";
        if (Util.hasSDCard()) {
            savePath = Util.getCameraPath();
            File saveDir = new File(savePath);
            if (!saveDir.exists()) {
                saveDir.mkdirs();
            }
        }

        // 没有挂载SD卡，无法保存文件
        if (TextUtils.isEmpty(savePath)) {
            Toast.makeText(SelectImageActivity.this, "无法保存照片，请检查SD卡是否挂载", Toast.LENGTH_LONG).show();
            return;
        }

        mCamImageName = Util.getSaveImageFullName();
        File out = new File(savePath, mCamImageName);
        Uri uri = Uri.fromFile(out);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, 0x03);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == AppCompatActivity.RESULT_OK) {
            switch (requestCode) {
                case 0x03: // 拍照
                    if (mCamImageName == null) {
                        return;
                    }
                    // 拍照完成通知系统添加照片
                    Uri localUri = Uri.fromFile(new File(Util.getCameraPath() + mCamImageName));
                    Intent localIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, localUri);
                    sendBroadcast(localIntent);
                    break;
                case 0x04: // 裁剪
                    if (data == null) {
                        return;
                    }

                    ArrayList<String> mImagePathList = new ArrayList<>();
                    mImagePathList.add(data.getStringExtra("crop_path"));

                    mOption.getCallback().doSelected(mImagePathList);
                    finish();
                    break;
                default:
                    break;
            }
        }
    }

    private LoaderManager.LoaderCallbacks<Cursor> mCursorLoader = new LoaderManager.LoaderCallbacks<Cursor>() {
        private final String[] IMAGE_PROJECTION = {
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.DATE_ADDED,
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.MINI_THUMB_MAGIC,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                MediaStore.Images.Media.SIZE
        };

        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            if (id == 0) {
                // 数据库光标加载器
                return new CursorLoader(SelectImageActivity.this,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_PROJECTION,
                        null, null, IMAGE_PROJECTION[2] + " DESC");
            }
            return null;
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, final Cursor data) {
            if (data != null) {
                final ArrayList<Image> images = new ArrayList<>();
                final List<ImageFolder> imageFolders = new ArrayList<>();

                final ImageFolder defaultFolder = new ImageFolder();
                defaultFolder.setName("全部照片");
                defaultFolder.setPath("");
                imageFolders.add(defaultFolder);

                int count = data.getCount();
                if (count > 0) {
                    data.moveToFirst();
                    do {
                        String path = data.getString(data.getColumnIndexOrThrow(IMAGE_PROJECTION[0]));
                        String name = data.getString(data.getColumnIndexOrThrow(IMAGE_PROJECTION[1]));
                        long dateTime = data.getLong(data.getColumnIndexOrThrow(IMAGE_PROJECTION[2]));
                        int id = data.getInt(data.getColumnIndexOrThrow(IMAGE_PROJECTION[3]));
                        String thumbPath = data.getString(data.getColumnIndexOrThrow(IMAGE_PROJECTION[4]));
                        String bucket = data.getString(data.getColumnIndexOrThrow(IMAGE_PROJECTION[5]));
                        int size = data.getInt(data.getColumnIndexOrThrow(IMAGE_PROJECTION[6]));

                        boolean show_flag = size > 1024 * 10; //是否大于10K

                        Image image = new Image();
                        image.setPath(path);
                        image.setName(name);
                        image.setDate(dateTime);
                        image.setId(id);
                        image.setThumbPath(thumbPath);
                        image.setFolderName(bucket);

                        images.add(image);

                        // 如果是新拍的照片
                        if (mCamImageName != null && mCamImageName.equals(image.getName())) {
                            image.setSelect(true);
                            mSelectedImages.add(image);
                        }

                        // 如果是被选中的图片
                        if (mSelectedImages.size() > 0) {
                            for (Image i : mSelectedImages) {
                                if (i.getPath().equals(image.getPath())) {
                                    image.setSelect(true);
                                }
                            }
                        }

                        File imageFile = new File(path);
                        File folderFile = imageFile.getParentFile();
                        ImageFolder folder = new ImageFolder();
                        folder.setName(folderFile.getName());
                        folder.setPath(folderFile.getAbsolutePath());
                        if (!imageFolders.contains(folder)) {
                            folder.getImages().add(image);
                            folder.setAlbumPath(image.getPath());// 默认相册封面
                            imageFolders.add(folder);
                        } else {
                            // 更新
                            ImageFolder f = imageFolders.get(imageFolders.indexOf(folder));
                            f.getImages().add(image);
                        }
                    } while (data.moveToNext());
                }

                addImagesToAdapter(images);
                defaultFolder.getImages().addAll(images);
                if (mOption.isHasCam()) {
                    defaultFolder.setAlbumPath(images.size() > 1 ? images.get(1).getPath() : null);
                } else {
                    defaultFolder.setAlbumPath(images.size() > 0 ? images.get(0).getPath() : null);
                }

                mImageFolderList = imageFolders;

                // 删除掉不存在的，在于用户选择了相片，又去相册删除
                if (mSelectedImages.size() > 0) {
                    List<Image> rs = new ArrayList<>();
                    for (Image i : mSelectedImages) {
                        File f = new File(i.getPath());
                        if (!f.exists()) {
                            rs.add(i);
                        }
                    }
                    mSelectedImages.removeAll(rs);
                }

                // If add new mCamera picture, and we only need one picture, we result it.
                if (mOption.getSelectCount() == 1 && mCamImageName != null) {
                    handleResult();
                }

                handleSelectSizeChange(mSelectedImages.size());
                // mErrorLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
            }
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {

        }
    };

    private void addImagesToAdapter(ArrayList<Image> images) {
        mImageList.clear();

        if (mOption.isHasCam()) {
            Image cam = new Image();
            mImageList.add(cam);
        }

        mImageList.addAll(images);

        if (mOption.getSelectCount() > 1) {
            mImageAdapter.refresh(mImageList, true);
        } else {
            mImageAdapter.refresh(mImageList, false);
        }
    }


    @AfterPermissionGranted(RC_CAMERA_PERM)
    public void requestCamera() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA)) {
            toOpenCamera();
        } else {
            EasyPermissions.requestPermissions(this, "", RC_CAMERA_PERM, Manifest.permission.CAMERA);
        }
    }

    @AfterPermissionGranted(RC_EXTERNAL_STORAGE)
    public void requestExternalStorage() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            initData();
        } else {
            EasyPermissions.requestPermissions(this, "", RC_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE);
        }
    }

    @Override
    protected void onDestroy() {
        mOption = null;
        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


    @Override
    public boolean shouldShowRequestPermissionRationale(String permission) {
        return false;
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (requestCode == RC_EXTERNAL_STORAGE) {
            DialogHelper.getConfirmDialog(this, "", "没有权限, 你需要去设置中开启读取手机存储权限.", "去设置", "取消", false, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(Settings.ACTION_APPLICATION_SETTINGS));
                    finish();
                }
            }, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            }).show();
        } else {
            // onCameraPermissionDenied();
            DialogHelper.getConfirmDialog(this, "", "没有权限, 你需要去设置中开启相机权限.", "去设置", "取消", false, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(Settings.ACTION_APPLICATION_SETTINGS));
                }
            }, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            }).show();
        }
    }

}
