package com.example.myapplication.ui.tab1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.myapplication.R;
import com.example.myapplication.utils.ColorUtils;
import com.example.myapplication.utils.ImageUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by xieH on 2017/5/17 0017.
 */
public class View33Activity extends AppCompatActivity {

    private ImageView mImageView, mImageView11, mImageView22;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view33);

        initView();
    }

    public void initView() {
        mImageView = (ImageView) findViewById(R.id.imageView);
        mImageView11 = (ImageView) findViewById(R.id.imageView11);
        mImageView22 = (ImageView) findViewById(R.id.imageView22);

        mergeImage();
        mergeImage11();
        mergeImage22();
    }

    public void mergeImage() {
        final String filePath = Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator + "HHH" + File.separator + "images" + File.separator + "test11" + File.separator;

        String imageName = String.format("image%d.png", 65);
        String fileName = filePath + imageName;

        Bitmap bitmap = BitmapFactory.decodeFile(fileName);

        bitmap = ColorUtils.bitmapTransport(bitmap).copy(Bitmap.Config.ARGB_8888, true);

        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.welcome_thirdly);
        Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.welcome_thirdly);
        Bitmap bitmap3 = BitmapFactory.decodeResource(getResources(), R.drawable.welcome_thirdly);

        bitmap1 = ImageUtils.scaleWithWH(bitmap1, 120, 90);
        bitmap2 = ImageUtils.scaleWithWH(bitmap2, 120, 90);
        bitmap3 = ImageUtils.scaleWithWH(bitmap3, 120, 90);

        Paint paint = new Paint();
        Canvas canvas = new Canvas(bitmap);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap1, 97f, 242f, paint);
        canvas.drawBitmap(bitmap2, 226f, 242f, paint);
        canvas.drawBitmap(bitmap3, 353f, 242f, paint);

        paint.setXfermode(null);
        canvas.save(Canvas.ALL_SAVE_FLAG);
        canvas.restore();

        bitmap = bitmapAddImage44(bitmap, imageName);

        mImageView.setImageBitmap(bitmap);
    }

    public void mergeImage11() {
        final String filePath = Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator + "HHH" + File.separator + "images" + File.separator + "test22" + File.separator;

        String imageName = String.format("image%d.png", 82);
        String fileName = filePath + imageName;

        Bitmap bitmap = BitmapFactory.decodeFile(fileName);

        bitmap = ColorUtils.bitmapTransport(bitmap).copy(Bitmap.Config.ARGB_8888, true);

        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.welcome_thirdly);
        Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.welcome_thirdly);
        Bitmap bitmap3 = BitmapFactory.decodeResource(getResources(), R.drawable.welcome_thirdly);

        bitmap1 = ImageUtils.scaleWithWH(bitmap1, 150, 112);
        bitmap2 = ImageUtils.scaleWithWH(bitmap2, 120, 90);
        bitmap3 = ImageUtils.scaleWithWH(bitmap3, 120, 90);

        Paint paint = new Paint();
        Canvas canvas = new Canvas(bitmap);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap2, 226f, 242f, paint);
        canvas.drawBitmap(bitmap3, 353f, 242f, paint);
        canvas.drawBitmap(bitmap1, 83f, 230f, paint);


        paint.setXfermode(null);
        canvas.save(Canvas.ALL_SAVE_FLAG);
        canvas.restore();

        bitmap = bitmapAddImage44(bitmap, imageName);

        mImageView11.setImageBitmap(bitmap);
    }

    public void mergeImage22() {
        final String filePath = Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator + "HHH" + File.separator + "images" + File.separator + "test11" + File.separator;

        String imageName = String.format("image%d.png", 93);
        String fileName = filePath + imageName;

        Bitmap bitmap = BitmapFactory.decodeFile(fileName);

        //  bitmap = ColorUtils.bitmapTransport(bitmap).copy(Bitmap.Config.ARGB_8888, true);

        bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);

        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.welcome_thirdly);

        bitmap1 = ImageUtils.scaleWithWH(bitmap1, 240, 180);

        Paint paint = new Paint();
        Canvas canvas = new Canvas(bitmap);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap1, 72f, 188f, paint);


        paint.setXfermode(null);
        canvas.save(Canvas.ALL_SAVE_FLAG);
        canvas.restore();

        mImageView22.setImageBitmap(bitmap);
    }

    public void image2Video(View v) {
        Image2Video();
    }

    //////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////
    public void Image2Video() {
        final String filePath = Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator + "HHH" + File.separator + "images" + File.separator + "test11" + File.separator;

        final String tempFilePath = Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator + "HHH" + File.separator + "images" + File.separator + "test22" + File.separator;

        new Thread(new Runnable() {
            @Override
            public void run() {
                int paddingLeft = 0;
                int paddingTop = 0;
                int size = 0;

                String imageName;
                String fileName;

                Bitmap bitmap;

                for (int i = 1; i < 101; i++) {
                    imageName = String.format("image%d.png", i);

                    if (i > 75 && i < 112) {
                        fileName = tempFilePath + imageName;
                        bitmap = BitmapFactory.decodeFile(fileName);
                        bitmap = ColorUtils.bitmapTransport(bitmap);
                    } else {
                        fileName = filePath + imageName;
                        bitmap = BitmapFactory.decodeFile(fileName);
                    }

                    if (i < 63) {

                    } else if (i < 82) {
                        bitmap = bitmapAddImage11(bitmap);
                    } else if (i < 93) {
                        bitmap = bitmapAddImage22(bitmap);
                    } else {
                        bitmap = bitmapAddImage33(bitmap, i);
                    }

                    if (i < 30) {

                    } else if (i < 60) {
                        paddingLeft = 48;
                        paddingTop = 250;
                        size = 14;
                        bitmap = drawText(bitmap, "我是百毒", size, Color.BLACK, paddingLeft, paddingTop);
                    } else {
                        paddingLeft = 100;
                        paddingTop = 116;
                        size = 12;
                        bitmap = drawText(bitmap, "我是百毒", size, Color.BLACK, paddingLeft, paddingTop);
                    }

                    if (i > 75 && i < 112) {
                        bitmap = bitmapAddImage44(bitmap, imageName);
                    }

                    saveBitmap2File(bitmap, i);

                    System.out.println("hh------" + i);
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                int paddingLeft = 0;
                int paddingTop = 0;
                int size = 0;

                String imageName;
                String fileName;

                Bitmap bitmap;

                for (int i = 101; i < 191; i++) {
                    imageName = String.format("image%d.png", i);

                    if (i > 75 && i < 112) {
                        fileName = tempFilePath + imageName;
                        bitmap = BitmapFactory.decodeFile(fileName);
                        bitmap = ColorUtils.bitmapTransport(bitmap);
                    } else {
                        fileName = filePath + imageName;
                        bitmap = BitmapFactory.decodeFile(fileName);
                    }

                    if (i < 63) {

                    } else if (i < 82) {
                        bitmap = bitmapAddImage11(bitmap);
                    } else if (i < 93) {
                        bitmap = bitmapAddImage22(bitmap);
                    } else {
                        bitmap = bitmapAddImage33(bitmap, i);
                    }

                    if (i < 30) {

                    } else if (i < 60) {
                        paddingLeft = 48;
                        paddingTop = 250;
                        size = 14;
                        bitmap = drawText(bitmap, "我是百毒", size, Color.BLACK, paddingLeft, paddingTop);
                    } else {
                        paddingLeft = 100;
                        paddingTop = 116;
                        size = 12;
                        bitmap = drawText(bitmap, "我是百毒", size, Color.BLACK, paddingLeft, paddingTop);
                    }

                    if (i > 75 && i < 112) {
                        bitmap = bitmapAddImage44(bitmap, imageName);
                    }

                    saveBitmap2File(bitmap, i);

                    System.out.println("hh------" + i);
                }
            }
        }).start();
    }

    public Bitmap bitmapAddImage11(Bitmap bitmap) {
        bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);

        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.card_03);
        Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.card_01);
        Bitmap bitmap3 = BitmapFactory.decodeResource(getResources(), R.drawable.card_02);

        bitmap1 = ImageUtils.scaleWithWH(bitmap1, 120, 90);
        bitmap2 = ImageUtils.scaleWithWH(bitmap2, 120, 90);
        bitmap3 = ImageUtils.scaleWithWH(bitmap3, 120, 90);

        Paint paint = new Paint();
        Canvas canvas = new Canvas(bitmap);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap1, 97f, 242f, paint);
        canvas.drawBitmap(bitmap2, 226f, 242f, paint);
        canvas.drawBitmap(bitmap3, 353f, 242f, paint);

        paint.setXfermode(null);
        canvas.save(Canvas.ALL_SAVE_FLAG);
        canvas.restore();

        return bitmap;
    }

    public Bitmap bitmapAddImage22(Bitmap bitmap) {
        bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);

        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.card_03);
        Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.card_01);
        Bitmap bitmap3 = BitmapFactory.decodeResource(getResources(), R.drawable.card_02);

        bitmap1 = ImageUtils.scaleWithWH(bitmap1, 150, 112);
        bitmap2 = ImageUtils.scaleWithWH(bitmap2, 120, 90);
        bitmap3 = ImageUtils.scaleWithWH(bitmap3, 120, 90);

        Paint paint = new Paint();
        Canvas canvas = new Canvas(bitmap);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap2, 226f, 242f, paint);
        canvas.drawBitmap(bitmap3, 353f, 242f, paint);
        canvas.drawBitmap(bitmap1, 83f, 230f, paint);

        paint.setXfermode(null);
        canvas.save(Canvas.ALL_SAVE_FLAG);
        canvas.restore();

        return bitmap;
    }

    public Bitmap bitmapAddImage33(Bitmap bitmap, int index) {
        bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);

        Bitmap bitmap1 = null;

        if (index > 92 && index < 128) {
            bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.card_03);
        } else if (index > 128 && index < 158) {
            bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.card_01);
        } else if (index > 158) {
            bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.card_02);
        } else {
            return bitmap;
        }

        bitmap1 = ImageUtils.scaleWithWH(bitmap1, 240, 180);

        Paint paint = new Paint();
        Canvas canvas = new Canvas(bitmap);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap1, 72f, 188f, paint);

        paint.setXfermode(null);
        canvas.save(Canvas.ALL_SAVE_FLAG);
        canvas.restore();

        return bitmap;
    }

    public Bitmap bitmapAddImage44(Bitmap bitmap, String imageName) {
        String filePath11 = Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator + "HHH" + File.separator + "images" + File.separator + "test11" + File.separator;

        String fileName = filePath11 + imageName;

        bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);

        Bitmap bitmap1 = BitmapFactory.decodeFile(fileName);

        Paint paint = new Paint();
        Canvas canvas = new Canvas(bitmap);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OVER));
        canvas.drawBitmap(bitmap1, 0f, 0f, paint);

        paint.setXfermode(null);
        canvas.save(Canvas.ALL_SAVE_FLAG);
        canvas.restore();

        return bitmap;
    }

    public static Bitmap drawText(Bitmap bitmap, String text, int size, int color, int paddingLeft, int paddingTop) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
        paint.setTextSize(size);
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        return drawTextToBitmap(bitmap, text, paint, bounds, paddingLeft, paddingTop);
    }

    /**
     * 图片上绘制文字
     */
    private static Bitmap drawTextToBitmap(Bitmap bitmap, String text, Paint paint, Rect bounds, int paddingLeft, int paddingTop) {
        android.graphics.Bitmap.Config bitmapConfig = bitmap.getConfig();

        paint.setDither(true); // 获取跟清晰的图像采样
        paint.setFilterBitmap(true);// 过滤一些
        if (bitmapConfig == null) {
            bitmapConfig = android.graphics.Bitmap.Config.ARGB_8888;
        }
        bitmap = bitmap.copy(bitmapConfig, true);
        Canvas canvas = new Canvas(bitmap);

        canvas.drawText(text, paddingLeft, paddingTop, paint);
        return bitmap;
    }

    /**
     * 保存Bitmap到sdcard
     */
    public static String saveBitmap2File(Bitmap bitmap, int i) {
        String filePath = Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator + "HHH" + File.separator + "images" + File.separator;
        String imageName = String.format("image%d.jpg", i);
        String fileName = filePath + "test" + File.separator + imageName;

        File file = new File(fileName);
        if (file.exists()) {
            file.delete();
        }

        try {
            FileOutputStream fos = new FileOutputStream(fileName);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileName;
    }
}
