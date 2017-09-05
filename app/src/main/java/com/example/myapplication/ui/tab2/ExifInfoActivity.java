package com.example.myapplication.ui.tab2;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.media.ExifInterface;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.transform.ImageUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xieH on 2017/9/1 0001.
 */
public class ExifInfoActivity extends AppCompatActivity {

    private ExifInterface mExifInterface;

    private List<String> mInfoList = new ArrayList<>();

    private String mImageFilePath;

    private TextView mTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exif_info);

        initView();
    }

    private void initView() {
        mTextView = (TextView) findViewById(R.id.textView);

    }

    /**
     * 相册
     */
    public void album(View v) {
        if (Build.VERSION.SDK_INT < 19) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");

            startActivityForResult(Intent.createChooser(intent, "选择图片"), ImageUtils.GET_IMAGE_BY_SDCARD);
        } else {
            Intent intent = new Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");

            startActivityForResult(Intent.createChooser(intent, "选择图片"), ImageUtils.GET_IMAGE_BY_SDCARD);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK)
            return;

        if (requestCode == ImageUtils.GET_IMAGE_BY_SDCARD) {
            if (data != null) {
                Uri uri = data.getData();

                mImageFilePath = ImageUtils.getImagePath(uri, ExifInfoActivity.this);

                // 兼容小米手机等
                String start = "file://";
                if (mImageFilePath.startsWith(start)) {
                    mImageFilePath = mImageFilePath.substring(start.length(), mImageFilePath.length());
                }

                getExifInfo(mImageFilePath);
            }
        }
    }

    /**
     * 需要设置存储位置信息，才会保存位置信息
     *
     * @param path
     */
    private void getExifInfo(String path) {
        mInfoList.clear();

        try {
            mExifInterface = new ExifInterface(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (mExifInterface == null)
            return;

        // jpg包含的mExifInterface常用信息
        mInfoList.add("TAG_MAKE: " + mExifInterface.getAttribute(ExifInterface.TAG_MAKE));
        mInfoList.add("TAG_MODEL: " + mExifInterface.getAttribute(ExifInterface.TAG_MODEL));
        mInfoList.add("TAG_DATETIME: " + mExifInterface.getAttribute(ExifInterface.TAG_DATETIME));
        mInfoList.add("TAG_ARTIST: " + mExifInterface.getAttribute(ExifInterface.TAG_ARTIST));  // API24，艺术家
        mInfoList.add("TAG_COPYRIGHT: " + mExifInterface.getAttribute(ExifInterface.TAG_COPYRIGHT));  // API24,版权
        mInfoList.add("TAG_EXIF_VERSION: " + mExifInterface.getAttribute(ExifInterface.TAG_EXIF_VERSION));  // API24,Exif版本
        mInfoList.add("TAG_FLASH: " + mExifInterface.getAttribute(ExifInterface.TAG_FLASH));  // 闪光灯
        mInfoList.add("TAG_ORIENTATION: " + mExifInterface.getAttribute(ExifInterface.TAG_ORIENTATION));

        mInfoList.add("TAG_IMAGE_WIDTH: " + mExifInterface.getAttribute(ExifInterface.TAG_IMAGE_WIDTH));
        mInfoList.add("TAG_IMAGE_LENGTH: " + mExifInterface.getAttribute(ExifInterface.TAG_IMAGE_LENGTH));

        // 相关经纬度信息
        mInfoList.add("TAG_GPS_ALTITUDE: " + mExifInterface.getAttribute(ExifInterface.TAG_GPS_ALTITUDE));  //海拔 Type is rational
        mInfoList.add("TAG_GPS_ALTITUDE_REF: " + mExifInterface.getAttributeInt(ExifInterface.TAG_GPS_ALTITUDE_REF, 0));  //海拔，单位m
        mInfoList.add("TAG_GPS_DATESTAMP: " + mExifInterface.getAttribute(ExifInterface.TAG_GPS_DATESTAMP));
        mInfoList.add("TAG_GPS_LONGITUDE: " + mExifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE));  //经度  Type is rational. Format is "num1/denom1,num2/denom2,num3/denom3".
        mInfoList.add("TAG_GPS_LONGITUDE_REF: " + mExifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF));  //经度  E：表示东经
        mInfoList.add("TAG_GPS_DEST_LONGITUDE: " + mExifInterface.getAttribute(ExifInterface.TAG_GPS_DEST_LONGITUDE));  // API24 //不知道干嘛用的
        mInfoList.add("TAG_GPS_DEST_LONGITUDE_REF: " + mExifInterface.getAttribute(ExifInterface.TAG_GPS_DEST_LONGITUDE_REF));  // API24 //不知道干嘛用的
        mInfoList.add("TAG_GPS_LATITUDE: " + mExifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE));  //  纬度    同经度
        mInfoList.add("TAG_GPS_LATITUDE_REF: " + mExifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE_REF));  // 纬度    N表示北纬
        mInfoList.add("TAG_GPS_DEST_LATITUDE: " + mExifInterface.getAttribute(ExifInterface.TAG_GPS_DEST_LATITUDE));  // 不知道干嘛用的
        mInfoList.add("TAG_GPS_DEST_LONGITUDE_REF: " + mExifInterface.getAttribute(ExifInterface.TAG_GPS_DEST_LONGITUDE_REF));  // 不知道干嘛用的

        // 其他信息
        mInfoList.add("TAG_USER_COMMENT: " + mExifInterface.getAttribute(ExifInterface.TAG_USER_COMMENT));  // api24   用户自己添加的信息

        String content = "";
        for (String str : mInfoList) {
            content += str + "\n";
        }
        mTextView.setText(content);
    }

    private void setExifStringInfo(String s) {
        mExifInterface.setAttribute(ExifInterface.TAG_MAKE, s);
        mExifInterface.setAttribute(ExifInterface.TAG_MODEL, s);
        mExifInterface.setAttribute(ExifInterface.TAG_DATETIME, s);
        mExifInterface.setAttribute(ExifInterface.TAG_ARTIST, s); // API24，艺术家
        mExifInterface.setAttribute(ExifInterface.TAG_COPYRIGHT, s); // API24,版权
        mExifInterface.setAttribute(ExifInterface.TAG_EXIF_VERSION, s); // API24,Exif版本
        mExifInterface.setAttribute(ExifInterface.TAG_FLASH, s);

        mExifInterface.setAttribute(ExifInterface.TAG_GPS_ALTITUDE, s);  // 海拔 Type is rational
        mExifInterface.setAttribute(ExifInterface.TAG_GPS_ALTITUDE_REF, s);
        mExifInterface.setAttribute(ExifInterface.TAG_GPS_DATESTAMP, s);
        mExifInterface.setAttribute(ExifInterface.TAG_GPS_LONGITUDE, s); // 经度  Type is rational. Format is "num1/denom1,num2/denom2,num3/denom3".
        mExifInterface.setAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF, s); // 经度  E：表示东经
        mExifInterface.setAttribute(ExifInterface.TAG_GPS_DEST_LONGITUDE, s); // API24 //不知道干嘛用的
        mExifInterface.setAttribute(ExifInterface.TAG_GPS_DEST_LONGITUDE_REF, s); // API24 //不知道干嘛用的
        mExifInterface.setAttribute(ExifInterface.TAG_GPS_LATITUDE, s);  //纬度 同经度
        mExifInterface.setAttribute(ExifInterface.TAG_GPS_LATITUDE_REF, s); // 纬度    N表示北纬
        mExifInterface.setAttribute(ExifInterface.TAG_GPS_DEST_LATITUDE, s); // 不知道干嘛用的
        mExifInterface.setAttribute(ExifInterface.TAG_GPS_DEST_LONGITUDE_REF, s); // 不知道干嘛用的
        // 其他信息
        mExifInterface.setAttribute(ExifInterface.TAG_USER_COMMENT, s); // api24   用户自己添加的信息
    }
}
