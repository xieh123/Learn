package com.example.myapplication.fragment;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.webkit.WebView;

import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.myapplication.R;
import com.example.myapplication.adapter.ViewPagerAdapter;
import com.example.myapplication.model.Item;
import com.example.myapplication.transform.AlphaAndScalePageTransformer;
import com.example.myapplication.transform.ImageUtils;
import com.example.myapplication.util.StatusBarCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xieH on 2017/8/18 0018.
 */
public class Test44Fragment extends LazyFragment {

    private ViewPager mViewPager;
    private ViewPagerAdapter mViewPagerAdapter;
    private List<Item> mItemList = new ArrayList<>();

    private String[] urls = new String[]{
            "http://pic19.nipic.com/20120324/3484432_092618805000_2.jpg",
            "http://img3.tuniucdn.com/images/2011-03-29/L/LFXLzoSGG9g753SH.jpg",
            "http://pic3.nipic.com/20090603/2781538_100414093_2.jpg",
            "http://pic.nipic.com/2008-03-01/2008319174451_2.jpg",
            "http://img01.sogoucdn.com/app/a/100520093/013d20860a59d114-e452007590d91dbc-e655209caf5a45fc1143eefe707a62ab.jpg",
            "http://pic24.nipic.com/20121029/3822951_090444696000_2.jpg",
            "http://img4.duitang.com/uploads/item/201209/20/20120920165508_EuenZ.jpeg",
            "http://img01.sogoucdn.com/app/a/100520093/ac75323d6b6de243-503c0c74be6ae02f-bd063b042a12e0bf0776668bccfddea3.jpg"
    };

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_test44;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView();
    }

    private void initView() {
        mViewPager = (ViewPager) getContentView().findViewById(R.id.viewpager);

        for (int i = 0; i < 8; i++) {
            Item item = new Item();
            item.setUrl(urls[i]);

            mItemList.add(item);
        }

        mViewPagerAdapter = new ViewPagerAdapter(getActivity(), mItemList);
        mViewPager.setAdapter(mViewPagerAdapter);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ImageUtils.loadBitmap(getActivity(), mItemList.get(position).getUrl(), new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        //  changeStatusBarColor(resource);
                    }
                });
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setPageMargin(40);
        mViewPager.setPageTransformer(true, new AlphaAndScalePageTransformer());

    }

    /**
     * 最好在 Activity 使用
     *
     * @param bitmap
     */
    private void changeStatusBarColor(Bitmap bitmap) {
        int pixel = bitmap.getPixel(bitmap.getWidth() / 2, 0); // 取图片最上面的中间色图
        int redValue = Color.red(pixel);
        int blueValue = Color.blue(pixel);
        int greenValue = Color.green(pixel);
        int alpha = Color.alpha(pixel);

        // StatusBarCompat为修改状态栏工具类
        StatusBarCompat.compat(getActivity(), Color.argb(alpha, redValue, greenValue, blueValue));
    }

    @Override
    public void onPause() {
        super.onPause();

        StatusBarCompat.Clean(getActivity(), Color.RED);
    }

    private void changeStatusBarColorByWebView() {
        WebView mWebView = new WebView(getActivity());

        Bitmap bitmap = getBitmapFromView(mWebView);
        if (null != bitmap) {
            int pixel = bitmap.getPixel(200, 5);
            int redValue = Color.red(pixel);
            int greenValue = Color.green(pixel);
            int blueValue = Color.blue(pixel);
            int alpha = Color.alpha(pixel);
            bitmap.recycle();

            StatusBarCompat.compat(getActivity(), Color.argb(alpha, redValue, greenValue, blueValue));
        }
    }

    /**
     * 获取view的bitmap
     *
     * @param v
     * @return
     */
    public static Bitmap getBitmapFromView(View v) {
        Bitmap b = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.RGB_565);
        Canvas c = new Canvas(b);
        v.layout(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
        Drawable bgDrawable = v.getBackground();
        if (bgDrawable != null) {
            bgDrawable.draw(c);
        } else {
            c.drawColor(Color.WHITE);
        }
        v.draw(c);
        return b;
    }

}
