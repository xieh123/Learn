package com.example.myapplication.utils;

import android.graphics.Bitmap;
import android.graphics.Color;

/**
 * Created by xieH on 2017/5/16 0016.
 */
public class ColorUtils {


    /**
     * 将bitmap中的某种颜色值替换成新的颜色
     *
     * @param oldBitmap
     * @param oldColor
     * @param newColor
     * @return
     */
    public static Bitmap replaceBitmapColor(Bitmap oldBitmap, int oldColor, int newColor) {
        Bitmap mBitmap = oldBitmap.copy(Bitmap.Config.ARGB_8888, true);
        // 循环获得bitmap所有像素点
        int mBitmapWidth = mBitmap.getWidth();
        int mBitmapHeight = mBitmap.getHeight();

        for (int i = 0; i < mBitmapHeight; i++) {
            for (int j = 0; j < mBitmapWidth; j++) {
                // 获得Bitmap 图片中每一个点的color颜色值
                // 在这说明一下 如果color 是全透明或者全黑 返回值为 0
                // getPixel()不带透明通道 getPixel32()才带透明部分 所以全透明是0x00000000
                // 而不透明黑色是0xFF000000 如果不计算透明部分就都是0了
                int color = mBitmap.getPixel(j, i);
                if (color == oldColor) {
                    mBitmap.setPixel(j, i, 0);  // 替换颜色
                }
            }
        }

        return mBitmap;
    }


    ////////////////////////////////////////////////////////////
    private static int COLOR_RANGE = 215; //图片中接近白色的程度

    //判断当前点是否为白色
    private static boolean colorInRange(int color) {
        int red = (color & 0xff0000) >> 16;
        int green = (color & 0x00ff00) >> 8;
        int blue = (color & 0x0000ff);
        if (red >= COLOR_RANGE && green >= COLOR_RANGE && blue >= COLOR_RANGE)
            return true;
        return false;
    }

    /**
     * 将白色背景的Bitmap变为透明Bitmap
     *
     * @param bitmap
     * @return
     */
    public static Bitmap bitmapTransport(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] argb = new int[width * height];
        bitmap.getPixels(argb, 0, width, 0, 0, width, height);
        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++) {
                int index = y * width + x;
                int r = Color.red(argb[index]);
                int g = Color.green(argb[index]);
                int b = Color.blue(argb[index]);
                if (colorInRange(argb[index])) {
                    argb[index] = 0x00000000 | (r << 16) | (g << 8) | b;
                } else {
                    argb[index] = 0xFF000000 | (r << 16) | (g << 8) | b;
                }
            }
        return Bitmap.createBitmap(argb, width, height, Bitmap.Config.ARGB_8888);
    }
}
