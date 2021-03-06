package com.example.myapplication.ui.tab2;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xieH on 2017/6/25 0025.
 */
public class SceneTransitionAnimationActivity extends AppCompatActivity {

    private ImageView mImageView, mImageView11;
    private TextView mTextView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scene_transition_animation);

        initView();
    }

    private void initView() {
        mImageView = (ImageView) findViewById(R.id.imageView);
        mTextView = (TextView) findViewById(R.id.textView);

        mImageView11 = (ImageView) findViewById(R.id.imageView11);
    }

    public void next(View v) {
        Intent intent = new Intent(this, SecondActivity.class);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            List<Pair<View, String>> pairs = new ArrayList<>();
            pairs.add(new Pair<View, String>(mImageView, SecondActivity.TAB_1));
            pairs.add(new Pair<View, String>(mTextView, SecondActivity.TAB_2));

            pairs.add(new Pair<View, String>(mImageView11, SecondActivity.TAB_3));

            Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(SceneTransitionAnimationActivity.this, pairs.toArray(new Pair[pairs.size()])).toBundle();
            startActivity(intent, bundle);
        } else {
            startActivity(intent);
        }
    }
}
