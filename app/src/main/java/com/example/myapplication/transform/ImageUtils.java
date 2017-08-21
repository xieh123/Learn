package com.example.myapplication.transform;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.myapplication.R;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by xieh on 16/6/4.
 */
public class ImageUtils {

    /**
     * 请求相册
     */
    public static final int GET_IMAGE_BY_SDCARD = 0;

    /**
     * 请求相机
     */
    public static final int GET_IMAGE_BY_CAMERA = 1;

    /**
     * 请求裁剪
     */
    public static final int GET_IMAGE_BY_CROP = 2;

    /**
     * 通过imageMogr2接口改变图片的分辨率
     */
    public static final String THUMB_500X = "?imageMogr2/thumbnail/500x";

    public static final String THUMB_300X = "?imageMogr2/thumbnail/300x";

    public static final String THUMB_200X = "?imageMogr2/thumbnail/200x";

    // 将原图调下大小并指定输出格式, 输出等比缩放并居中裁剪的 100x100 PNG 格式图片
    public static final String HH = "?imageView2/1/w/100/h/100/format/png.png";


    /**
     * 获取文件的本地存储路径
     *
     * @return
     */
    public static String getSavePath() {

        String savePath = null;

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            savePath = Environment.getExternalStorageDirectory().getAbsolutePath()
                    + File.separator + "HHH" + File.separator + "images" + File.separator;
        } else {
            savePath = Environment.getRootDirectory().getAbsolutePath()
                    + File.separator + "HHH" + File.separator + "images" + File.separator;
        }

        File file = new File(savePath);
        if (!file.exists()) {
            file.mkdirs();
        }

        return savePath;
    }

    /**
     * 获取图片路径
     *
     * @param uri
     * @param context
     * @return
     */
    public static String getImagePath(Uri uri, Context context) {

        String[] projection = {MediaStore.MediaColumns.DATA};
        Cursor cursor = context.getContentResolver().query(uri, projection,
                null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            int columIndex = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            String ImagePath = cursor.getString(columIndex);
            cursor.close();
            return ImagePath;
        }

        return uri.toString();
    }

    /**
     * 保存图片
     *
     * @param bitmap
     */
    public static String saveBitmap(Bitmap bitmap) {

        String imagePath = getSavePath() + System.currentTimeMillis() + ".jpg";

        File file = new File(imagePath);
        if (file.exists()) {
            file.delete();
        }

        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return imagePath;
    }

    /**
     * 加载图片
     *
     * @param context
     * @param imageView
     * @param resourceId
     */
    public static void loadImage(Context context, ImageView imageView, int resourceId) {
        Glide.with(context).load(resourceId)
                .crossFade()
                .into(imageView);
    }

    /**
     * 加载图片
     *
     * @param context
     * @param imageView
     * @param url
     */
    public static void loadImage(Context context, ImageView imageView, String url) {
        Glide.with(context).load(url)
                .crossFade()
                .into(imageView);
    }

    /**
     * 加载图片
     *
     * @param context
     * @param imageView
     * @param file
     */
    public static void loadImage(Context context, ImageView imageView, File file) {
        Glide.with(context).load(file)
                .crossFade()
                .into(imageView);
    }

    /**
     * 加载圆角图片
     *
     * @param context
     * @param imageView
     * @param resourceId
     */
    public static void loadCornersImage(Context context, ImageView imageView, int resourceId) {
        Glide.with(context).load(resourceId)
                .crossFade()
                .bitmapTransform(new RoundedCornersTransformation(context, 10, 0))
                .into(imageView);
    }

    /**
     * 加载圆角图片
     *
     * @param context
     * @param imageView
     * @param url
     */
    public static void loadCornersImage(Context context, ImageView imageView, String url) {
        Glide.with(context).load(url)
                .crossFade()
                .placeholder(R.mipmap.ic_launcher)
                .bitmapTransform(new RoundedCornersTransformation(context, 10, 0))
                .into(imageView);
    }

    /**
     * 加载圆角图片
     *
     * @param context
     * @param imageView
     * @param file
     */
    public static void loadCornersImage(Context context, ImageView imageView, File file) {
        Glide.with(context).load(file)
                .crossFade()
                .bitmapTransform(new RoundedCornersTransformation(context, 10, 0))
                .into(imageView);
    }

    /**
     * 加载圆角图片
     *
     * @param context
     * @param imageView
     * @param resourceId
     */
    public static void loadCircleImage(Context context, ImageView imageView, int resourceId) {
        Glide.with(context).load(resourceId)
                .crossFade()
                .bitmapTransform(new CropCircleTransformation(context))
                .into(imageView);
    }

    /**
     * 加载圆角图片
     *
     * @param context
     * @param imageView
     * @param url
     */
    public static void loadCircleImage(Context context, ImageView imageView, String url) {
        Glide.with(context).load(url)
                .crossFade()
                .bitmapTransform(new CropCircleTransformation(context))
                .into(imageView);
    }

    /**
     * 加载圆角图片
     *
     * @param context
     * @param imageView
     * @param file
     */
    public static void loadCircleImage(Context context, ImageView imageView, File file) {
        Glide.with(context).load(file)
                .crossFade()
                .bitmapTransform(new CropCircleTransformation(context))
                .into(imageView);
    }

    /**
     * 加载图片
     *
     * @param context
     * @param url
     * @param target
     */
    public static void loadBitmap(Context context, String url, SimpleTarget<Bitmap> target) {
        Glide.with(context).load(url)
                .asBitmap()
                .into(target);
    }

    /**
     * 取消所有正在下载或等待下载的任务。
     *
     * @param context
     */
    public static void pauseRequests(Context context) {
        Glide.with(context).pauseRequests();
    }

    /**
     * 恢复所有任务
     *
     * @param context
     */
    public static void resumeRequests(Context context) {
        Glide.with(context).resumeRequests();
    }


}
