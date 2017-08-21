package com.example.myapplication.ui.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.myapplication.R;

/**
 * Created by xieH on 2017/1/7 0007.
 */
public class CardViewActivity extends AppCompatActivity {

    private ImageView mImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardview);

        initView();
    }

    public void initView() {
        mImageView = (ImageView) findViewById(R.id.imageView);

        String url = "http://eggou.oss-cn-shenzhen.aliyuncs.com/images/upload/20170520/14952708856855.jpg";

        Glide.with(this)
                .load(url)
                .listener(requestListener)
                .into(mImageView);

    }

    private RequestListener<String, GlideDrawable> requestListener = new RequestListener<String, GlideDrawable>() {
        @Override
        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
            // todo log exception

            System.out.println("h-------" + e);

            // important to return false so the error placeholder can be placed
            return false;
        }

        @Override
        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
            return false;
        }
    };
}
