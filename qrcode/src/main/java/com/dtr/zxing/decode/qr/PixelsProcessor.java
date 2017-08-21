package com.dtr.zxing.decode.qr;

import android.graphics.Bitmap;

/**
 * Created by xieH on 2017/1/13 0013.
 */
public interface PixelsProcessor {
    Bitmap create(int[] pixels, int width, int height);
}
