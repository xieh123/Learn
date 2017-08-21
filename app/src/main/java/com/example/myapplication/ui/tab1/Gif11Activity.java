package com.example.myapplication.ui.tab1;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.gifencoder.AnimatedGifEncoder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.myapplication.R;
import com.example.myapplication.utils.ImageUtils;
import com.example.myapplication.utils.gif.GifImageDecoder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by xieH on 2017/4/14 0014.
 */
public class Gif11Activity extends AppCompatActivity {

    private ImageView mImageview01, mImageview02;


    private LinearLayout mImageLl;

    private GifImageDecoder gifDecoder;


    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == 1) {
                int index = msg.arg1;

                ImageView iv_image = new ImageView(Gif11Activity.this);
                iv_image.setPadding(5, 5, 5, 5);
                ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(200, 200);
                iv_image.setLayoutParams(layoutParams);
                iv_image.setImageBitmap(gifDecoder.getFrame(index));
                mImageLl.addView(iv_image);
            } else if (msg.what == 2) {
                String path = msg.obj.toString();

                Glide.with(Gif11Activity.this)
                        .load(path)
                        .asGif()
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .placeholder(R.drawable.ic_placeholder)
                        .error(R.drawable.ic_error)
                        .into(mImageview02);
            }
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gif11);

        initView();

        setData();
    }

    public void initView() {
        mImageview01 = (ImageView) findViewById(R.id.gif_01_iv);
        mImageview02 = (ImageView) findViewById(R.id.gif_02_iv);

        mImageLl = (LinearLayout) findViewById(R.id.gif_ll);

        Glide.with(this)
                .load(R.drawable.test)
                .asGif()  // 添加这个时，加载的图片，如果不是gif，则跳转到error
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)  // 加载gif时，需要为其添加缓存策略,其中缓存策略可以为:Source及None,None及为不缓存,Source缓存原型.如果为ALL和Result就不行;默认为All.
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_error)
                .into(mImageview01);
    }

    public void setData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    gifDecoder = new GifImageDecoder();
                    gifDecoder.read(Gif11Activity.this.getResources().getAssets().open("test.gif"));
                    int size = gifDecoder.getFrameCount();

                    for (int i = 0; i < size; i++) {
                        Message message = new Message();

                        message.what = 1;
                        message.arg1 = i;

                        mHandler.sendMessage(message);

//                gifFrame.nextFrame();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void createGif(View v) {

        new Thread(new Runnable() {
            @Override
            public void run() {

                int size = gifDecoder.getFrameCount();
                int delay = gifDecoder.getDelay(1);

                String path = null;

                try {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    AnimatedGifEncoder localAnimatedGifEncoder = new AnimatedGifEncoder();
                    localAnimatedGifEncoder.start(baos); // start
                    localAnimatedGifEncoder.setRepeat(0); // 设置生成gif的开始播放时间。0为立即开始播放
                    localAnimatedGifEncoder.setDelay(delay);

                    for (int i = 0; i < size; i++) {
                        Bitmap bitmap = ImageUtils.drawTextToLeftTop(Gif11Activity.this, gifDecoder.getFrame(i), "哈哈哈哈哈", 12,
                                ContextCompat.getColor(Gif11Activity.this, R.color.color_02), 0, 0);

                        localAnimatedGifEncoder.addFrame(bitmap);
                    }

                    localAnimatedGifEncoder.finish(); // finish

                    File file = new File(Environment.getExternalStorageDirectory().getPath() + "/HHH");
                    if (!file.exists()) file.mkdirs();
                    path = Environment.getExternalStorageDirectory().getPath() + "/HHH/" + "456" + ".gif";

                    File gif = new File(path);

                    if (gif.exists()) {
                        gif.delete();
                    }

                    FileOutputStream fos = new FileOutputStream(path);
                    baos.writeTo(fos);
                    baos.flush();
                    fos.flush();
                    baos.close();
                    fos.close();

                    Message message = new Message();

                    message.what = 2;
                    message.obj = path;

                    mHandler.sendMessage(message);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
