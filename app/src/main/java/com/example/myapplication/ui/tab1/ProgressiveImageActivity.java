package com.example.myapplication.ui.tab1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.util.HttpUrlFetcher;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by xieH on 2017/4/27 0027.
 */
public class ProgressiveImageActivity extends AppCompatActivity {

    private static final String BITMAP = "bitmap";

    private ImageView mImageView, mImageView11;

    private String url = "http://www.reasoft.com/tutorials/web/img/progress.jpg";
    HttpUrlFetcher httpUrl = new HttpUrlFetcher();
    private boolean isCancel;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bitmap bitmap = msg.getData().getParcelable(BITMAP);
            if (bitmap != null) {
                mImageView.setImageBitmap(bitmap);
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progressine_image);

        // 加载JPEG图片

        intView();
    }

    public void intView() {
        mImageView = (ImageView) findViewById(R.id.progressive_image_iv);
        mImageView11 = (ImageView) findViewById(R.id.progressive_image11_iv);

        loadImage(null);

        Glide.with(this)
                .load(url)
                .crossFade()
                .into(mImageView11);
    }

    public void loadImage(View v) {
        isCancel = false;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    InputStream inputStream = httpUrl.loadData(new URL(url));
                    byte[] mByte = new byte[httpUrl.getSize()];
                    byte lastOne = 0;
                    byte lastTwo = 0;
                    int offset = 0;
                    while (!isCancel) {
                        // 本次读取的字节
                        byte[] get = getBytes(inputStream);
                        // 放入本次读取的数据
                        System.arraycopy(get, 0, mByte, offset, get.length);
                        offset = offset + get.length;
                        // 记录最后两位字符
                        lastOne = mByte[offset - 1];
                        lastTwo = mByte[offset - 2];
                        // 替换掉最后两个字节为FFD9,否则无法转化成bitmap
                        mByte[offset - 2] = -1;
                        mByte[offset - 1] = -39;
                        // 生成bitmap
                        Bitmap result = BitmapFactory.decodeByteArray(mByte, 0, offset);
                        // 还原最后两个字节
                        mByte[offset - 2] = lastTwo;
                        mByte[offset - 1] = lastOne;


                        Message message = Message.obtain();
                        Bundle bundle = new Bundle();
                        bundle.putParcelable(BITMAP, result);
                        message.setData(bundle);
                        handler.sendMessage(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    public static byte[] getBytes(InputStream is) throws IOException {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        // 这里设置每次读取的数量,设置小一点是为了让效果更明显
        byte[] buffer = new byte[10]; // 用数据装 1024
        int len = -1;

        // 要实现比较理想的渐进式加载效果,其实不应该写死每次读取量,应该是根据FFDA来判断读到第几帧了
        if ((len = is.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        } else {
            is.close();
        }

        // 关闭流一定要记得。
        outStream.close();

        return outStream.toByteArray();
    }

    public void hide(View v) {
        isCancel = true;
        handler.removeCallbacks(null);
        mImageView.setImageResource(R.mipmap.ic_launcher);
    }

}
