package com.example.myapplication.ui.tab2;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.elyeproj.loaderviewlibrary.LoaderTextView;
import com.example.myapplication.R;
import com.example.myapplication.widget.ShimmerLayout;

/**
 * Created by xieH on 2017/6/20 0020.
 */
public class LoaderViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loader_view);

        initView();

        loadData();
    }

    public void initView() {

        ShimmerLayout shimmerLayout = (ShimmerLayout) findViewById(R.id.shimmer_layout);
        shimmerLayout.startShimmerAnimation();

    }

    private void loadData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                postLoadData();
            }
        }, 5000);
    }

    private void postLoadData() {
        ((TextView) findViewById(R.id.txt_name)).setText("Mr. Donald Trump");
        ((TextView) findViewById(R.id.txt_title)).setText("Presidency Candidate of United State");
        ((TextView) findViewById(R.id.txt_phone)).setText("+001 2345 6789");
        ((TextView) findViewById(R.id.txt_email)).setText("donald.trump@donaldtrump.com");
        ((ImageView) findViewById(R.id.image_icon)).setImageResource(R.drawable.welcome_thirdly);
    }

    public void resetLoader(View view) {
        ((LoaderTextView) findViewById(R.id.txt_name)).resetLoader();
        ((LoaderTextView) findViewById(R.id.txt_title)).resetLoader();
        ((LoaderTextView) findViewById(R.id.txt_phone)).resetLoader();
        ((LoaderTextView) findViewById(R.id.txt_email)).resetLoader();
//        ((LoaderImageView) findViewById(R.id.image_icon)).resetLoader();
        loadData();
    }
}
