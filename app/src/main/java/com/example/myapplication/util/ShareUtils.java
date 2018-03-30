package com.example.myapplication.util;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by xieH on 2018/2/28 0028.
 */
public class ShareUtils {


    /**
     * 分享文字
     *
     * @param context
     * @param text
     */
    public static void shareText(Context context, String text) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, text);
        intent.setType("text/plain");
        // 设置分享列表的标题，并且每次都显示分享列表
        context.startActivity(Intent.createChooser(intent, "分享到"));
    }

    /**
     * 分享单张图片
     *
     * @param context
     * @param imagePath
     */
    public static void shareImage(Context context, String imagePath) {
        Uri imageUri = Uri.fromFile(new File(imagePath));

        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
        shareIntent.setType("image/*");
        context.startActivity(Intent.createChooser(shareIntent, "分享到"));
    }


    /**
     * 分享多张图片
     *
     * @param context
     * @param imagePathList
     */
    public static void shareImages(Context context, ArrayList<String> imagePathList) {
        Intent share_intent = new Intent();
        ArrayList<Uri> imageUris = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            for (String imagePath : imagePathList) {
                Uri imageContentUri = getImageContentUri(context, new File(imagePath));
                imageUris.add(imageContentUri);
            }
        } else {
            for (String imagePath : imagePathList) {
                imageUris.add(Uri.fromFile(new File(imagePath)));
            }

        }

        // 设置分享行为
        share_intent.setAction(Intent.ACTION_SEND_MULTIPLE);
        // 设置分享内容的类型
        share_intent.setType("image/png");
        share_intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        share_intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris);
        context.startActivity(Intent.createChooser(share_intent, "Share"));
    }

    /**
     * 获取图片的绝对的分享地址
     *
     * @param context
     * @param imageFile
     * @return content Uri
     */
    public static Uri getImageContentUri(Context context, File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID}, MediaStore.Images.Media.DATA + "=? ",
                new String[]{filePath}, null);
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }
}
