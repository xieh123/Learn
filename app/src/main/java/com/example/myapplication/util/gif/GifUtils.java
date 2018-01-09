package com.example.myapplication.util.gif;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.bumptech.glide.gifencoder.AnimatedGifEncoder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by xieH on 2017/4/14 0014.
 */
public class GifUtils {

    /**
     * 生成Gif
     *
     * @param filename
     * @param paths
     * @param delay    帧之间间隔的时间
     * @return
     * @throws IOException
     */
    public static String createGif(String filename, List<String> paths, int delay) {
        String path = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            AnimatedGifEncoder localAnimatedGifEncoder = new AnimatedGifEncoder();
            localAnimatedGifEncoder.start(baos); // start
            localAnimatedGifEncoder.setRepeat(0); // 设置生成gif的开始播放时间。0为立即开始播放
            localAnimatedGifEncoder.setDelay(delay);

            if (paths.size() > 0) {
                for (int i = 0; i < paths.size(); i++) {
                    Bitmap bitmap = BitmapFactory.decodeFile(paths.get(i));
                    localAnimatedGifEncoder.addFrame(bitmap);
                }
            }
            localAnimatedGifEncoder.finish(); // finish

            File file = new File(Environment.getExternalStorageDirectory().getPath() + "/HHH");
            if (!file.exists()) file.mkdir();
            path = Environment.getExternalStorageDirectory().getPath() + "/HHH/" + filename + ".gif";
            FileOutputStream fos = new FileOutputStream(path);
            baos.writeTo(fos);
            baos.flush();
            fos.flush();
            baos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return path;
    }

    /**
     * 生成Gif
     *
     * @param filename
     * @param paths
     * @param delay    帧之间间隔的时间
     * @param width
     * @param height
     * @return
     * @throws IOException
     */
    public static String createGif(String filename, List<String> paths, int delay, int width, int height) {
        String path = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            AnimatedGifEncoder localAnimatedGifEncoder = new AnimatedGifEncoder();
            localAnimatedGifEncoder.start(baos); // start
            localAnimatedGifEncoder.setRepeat(0); // 设置生成gif的开始播放时间。0为立即开始播放
            localAnimatedGifEncoder.setDelay(delay);

            if (paths.size() > 0) {
                for (int i = 0; i < paths.size(); i++) {
                    Bitmap bitmap = BitmapFactory.decodeFile(paths.get(i));
                    Bitmap resizeBm = ImageUtils.resizeImage(bitmap, width, height);
                    localAnimatedGifEncoder.addFrame(resizeBm);
                }
            }
            localAnimatedGifEncoder.finish(); // finish

            File file = new File(Environment.getExternalStorageDirectory().getPath() + "/HHH");
            if (!file.exists()) file.mkdir();
            path = Environment.getExternalStorageDirectory().getPath() + "/HHH/" + filename + ".gif";
            FileOutputStream fos = new FileOutputStream(path);
            baos.writeTo(fos);
            baos.flush();
            fos.flush();
            baos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return path;
    }


}
