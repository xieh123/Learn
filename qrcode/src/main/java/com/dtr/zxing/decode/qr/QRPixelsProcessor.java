package com.dtr.zxing.decode.qr;

import android.graphics.Bitmap;

/**
 * Created by xieH on 2017/1/13 0013.
 */
public class QRPixelsProcessor implements PixelsProcessor {

    @Override
    public Bitmap create(int[] pixels, int width, int height) {
        final Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }
}
