package com.example.myapplication.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Administrator on 2016/11/24 0024.
 */
public class FileUtils {

    public static String getFolderName(String name) {
        File mediaStorageDir = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), name);

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return "";
            }
        }
        return mediaStorageDir.getAbsolutePath();
    }

    /**
     * 判断sd卡是否可以用
     */
    private static boolean isSDAvailable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    public static String getNewFilePath(Context context, String folderName) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA);

        String timeStamp = simpleDateFormat.format(new Date());

        String path;
        if (isSDAvailable()) {
            path = getFolderName(folderName) + File.separator + timeStamp + ".jpg";
        } else {
            path = context.getFilesDir().getPath() + File.separator + timeStamp + ".jpg";
        }

        if (TextUtils.isEmpty(path)) {
            return null;
        }

        return path;
    }

    /**
     * 保存Bitmap到sdcard
     *
     * @param path
     * @param bitmap
     */
    public static void saveBitmap2File(String path, Bitmap bitmap) {
        long time = System.currentTimeMillis();
        String file = path + File.separator + time + ".jpg";

        try {
            FileOutputStream fos = new FileOutputStream(file);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
            Log.i("file--", "save bitmap success...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
