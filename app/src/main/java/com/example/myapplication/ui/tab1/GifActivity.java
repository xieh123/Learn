package com.example.myapplication.ui.tab1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.myapplication.R;
import com.example.myapplication.widget.GifView;

/**
 * Created by xieH on 2017/2/17 0017.
 */
public class GifActivity extends AppCompatActivity {


    private GifView mGifView;

    private ImageView mImageView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gif);

        initView();
    }

    public void initView() {

        mGifView = (GifView) findViewById(R.id.gif_gifView);
        mGifView.setMovieResource(R.drawable.gif_image);

        mImageView = (ImageView) findViewById(R.id.gif_iv);

        String url = "http://www.jcodecraeer.com/uploads/20160918/1474161740949108.gif";

        Glide.with(this)
                .load(R.drawable.gif_image)
                .asGif()  // 添加这个时，加载的图片，如果不是gif，则跳转到error
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)  // 加载gif时，需要为其添加缓存策略,其中缓存策略可以为:Source及None,None及为不缓存,Source缓存原型.如果为ALL和Result就不行;默认为All.
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_error)
                .into(mImageView);
    }



}
