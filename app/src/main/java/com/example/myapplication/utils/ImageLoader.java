package com.example.myapplication.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.LruCache;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 对图片进行管理的工具类。
 *
 * @author xieh
 */
public class ImageLoader {

    /**
     * 图片缓存技术的核心类，用于缓存所有下载好的图片，在程序内存达到设定值时会将最少最近使用的图片移除掉。
     */
    private static LruCache<String, Bitmap> mMemoryCache;

    /**
     * ImageLoader的实例。
     */
    private static ImageLoader mImageLoader;

    private ImageLoader() {
        // 获取应用程序最大可用内存
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemory / 8;
        // 设置图片缓存大小为程序最大可用内存的1/8
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getByteCount();
            }
        };
    }

    /**
     * 获取ImageLoader的实例。
     *
     * @return ImageLoader的实例。
     */
    public static ImageLoader getInstance() {
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader();
        }
        return mImageLoader;
    }

    /**
     * 将一张图片存储到LruCache中。
     *
     * @param key    LruCache的键，这里传入图片的URL地址。
     * @param bitmap LruCache的键，这里传入从网络上下载的Bitmap对象。
     */
    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemoryCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }

    /**
     * 从LruCache中获取一张图片，如果不存在就返回null。
     *
     * @param key LruCache的键，这里传入图片的URL地址。
     * @return 对应传入键的Bitmap对象，或者null。
     */
    public Bitmap getBitmapFromMemoryCache(String key) {
        return mMemoryCache.get(key);
    }

    /**
     * 计算源图像的大小与目标图像的比率
     *
     * @param options
     * @param reqWidth
     * @return
     */
    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth) {
        // 源图片的宽度
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (width > reqWidth) {
            // 计算出实际宽度和目标宽度的比率
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = widthRatio;
        }
        return inSampleSize;
    }

    /**
     * 压缩图片，指定宽度
     * <p>
     * 对源图像进行压缩，对图像按自己的需要进行相应比例的缩放，得到缩略图
     * Options属性值inSampleSize表示缩略图大小为原始图片大小的几分之一；
     * 比如其值为2，则取出的缩略图的宽和高都是原始图片的1/2，图片大小就为原始大小的1/4。
     *
     * @param pathName
     * @param reqWidth
     * @return
     */
    public static Bitmap decodeSampledBitmapFromResource(String pathName, int reqWidth) {
        // 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
        final BitmapFactory.Options options = new BitmapFactory.Options();
        // 通知BitmapFactory类只须返回该图像的大小信息,而无须尝试解码图像本身
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pathName, options);

        // 调用上面定义的方法计算inSampleSize值
        options.inSampleSize = calculateInSampleSize(options, reqWidth);

        // 使用获取到的inSampleSize值再次解析图片，对它进行真正的解码，得到相应的缩放比例的图片缩放图
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(pathName, options);

    }

    /**
     * 压缩图片，指定图片路径、压缩的高度和宽度
     * <p>
     * 对源图像进行压缩，对图像按自己的需要进行相应比例的缩放，得到缩略图
     * Options属性值inSampleSize表示缩略图大小为原始图片大小的几分之一；
     * 比如其值为2，则取出的缩略图的宽和高都是原始图片的1/2，图片大小就为原始大小的1/4。
     *
     * @param pathName
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static Bitmap decodeSampledBitmapFromResource(String pathName, int reqWidth, int reqHeight) {
        // 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
        final BitmapFactory.Options options = new BitmapFactory.Options();
        // 通知BitmapFactory类只须返回该图像的大小信息,而无须尝试解码图像本身
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pathName, options);

        // 调用下面定义的方法计算inSampleSize值
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // 使用获取到的inSampleSize值再次解析图片，对它进行真正的解码，得到相应的缩放比例的图片缩放图
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(pathName, options);

    }

    /**
     * 指定高度和宽度对源图片进行压缩
     *
     * @param res
     * @param resId
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {

        // 给定的BitmapFactory设置解码的参数
        final BitmapFactory.Options options = new BitmapFactory.Options();
        // 从解码器中获取原始图片的宽高，这样避免了直接申请内存空间
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth,
                reqHeight);

        // 压缩完后便可以将inJustDecodeBounds设置为false了。
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    /**
     * 指定图片的缩放比例
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // 原始图片的宽、高
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        // if (height > reqHeight || width > reqWidth) {
        // //这里有两种压缩方式，可供选择。
        // /**
        // * 压缩方式二
        // */
        // // final int halfHeight = height / 2;
        // // final int halfWidth = width / 2;
        // // while ((halfHeight / inSampleSize) > reqHeight
        // // && (halfWidth / inSampleSize) > reqWidth) {
        // // inSampleSize *= 2;
        // // }
        //
        /**
         * 压缩方式一
         */
        // 计算压缩的比例：分为宽高比例
        final int heightRatio = Math.round((float) height / (float) reqHeight);
        final int widthRatio = Math.round((float) width / (float) reqWidth);
        inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        // }

        return inSampleSize;
    }


    /**
     * 将bitmap保存到本地
     *
     * @param bitmap
     * @param imagePath 原图片路径
     * @return
     */
    public static String getThumbImagePath(Bitmap bitmap, String imagePath) {

        String thumbFilePath = getThumbImagePath(imagePath);

        File file = new File(thumbFilePath);

        if (!file.exists()) {
            // 保存图片到本地路径中
            if (bitmap != null) {
                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                    fos.flush();
                    fos.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

        return thumbFilePath;
    }


    /**
     * 获取图片的本地存储路径，优先存储在SD卡 SD卡不存在则存储在手机自带内部存储中
     *
     * @param imageUrl 图片的URL地址。
     * @return 图片的本地存储路径。
     */
    public static String getImagePath(String imageUrl) {
        int lastSlashIndex = imageUrl.lastIndexOf("/");
        String imageName = imageUrl.substring(lastSlashIndex + 1);
        String imageDir = null;

        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            // SD卡存储路径
            imageDir = Environment.getExternalStorageDirectory().getPath()
                    + File.separator + "ProjectE" + File.separator + "images"
                    + File.separator;
        } else {
            System.out.println("SD卡不存在");
            // 手机自带存储路径
            imageDir = Environment.getRootDirectory().getPath()
                    + File.separator + "ProjectE" + File.separator + "images"
                    + File.separator;
        }

        File file = new File(imageDir);
        if (!file.exists()) {
            file.mkdirs();
        }
        String imagePath = imageDir + imageName;
        return imagePath;
    }

    /**
     * 获取缩略图片的本地存储路径，优先存储在SD卡 SD卡不存在则存储在手机自带内部存储中
     *
     * @param imagePath 图片的地址。
     * @return 图片的本地存储路径。
     */
    public static String getThumbImagePath(String imagePath) {
        int lastSlashIndex = imagePath.lastIndexOf(File.separator);
        String imageName = imagePath.substring(lastSlashIndex + 1);
        String imageDir = null;

        String thumbFileName = "";

        if (imageName.contains("thumb")) {
            thumbFileName = imageName;
        } else {
            thumbFileName = "thumb_" + imageName;
        }

        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            // SD卡存储路径
            imageDir = Environment.getExternalStorageDirectory().getPath()
                    + File.separator + "ProjectE" + File.separator + "images"
                    + File.separator;
        } else {
            System.out.println("SD卡不存在");
            // 手机自带存储路径
            imageDir = Environment.getRootDirectory().getPath()
                    + File.separator + "ProjectE" + File.separator + "images"
                    + File.separator;
        }

        File file = new File(imageDir);
        if (!file.exists()) {
            file.mkdirs();
        }

        String thumbFilePath = imageDir + thumbFileName;
        return thumbFilePath;
    }

}
