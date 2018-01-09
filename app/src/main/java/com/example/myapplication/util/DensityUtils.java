package com.example.myapplication.util;

import android.content.Context;

/**
 * Created by xieH on 2016/12/1 0001.
 */
public class DensityUtils {


    private DensityUtils() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

//    /**
//     * dp转px
//     */
//    public static int dp2px(Context context, float dpVal) {
//        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
//                dpVal, context.getResources().getDisplayMetrics());
//    }
//
//    /** * sp转px */
//    public static int sp2px(Context context, float spVal) {
//        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
//                spVal, context.getResources().getDisplayMetrics());
//    }
//
//    /**
//     * px转dp
//     */
//    public static float px2dp(Context context, float pxVal) {
//        final float scale = context.getResources().getDisplayMetrics().density;
//        return (pxVal / scale);
//    }
//
//    /** * px转sp */
//    public static float px2sp(Context context, float pxVal) {
//        return (pxVal / context.getResources().getDisplayMetrics().scaledDensity);
//    }


    /**
     * 将px值转换为dip或dp值，保证尺寸大小不变
     *
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     *
     * @param context
     * @param dipValue
     * @return
     */
    public static int dp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param context
     * @param spValue
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}
