package com.example.myapplication.ui.main;

import android.content.Intent;
import android.graphics.Path;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.myapplication.R;
import com.example.myapplication.svgandroid.SVGParser;
import com.example.myapplication.widget.PathAnimView;

/**
 * Created by xieH on 2017/1/4 0004.
 */
public class PathAnimActivity extends AppCompatActivity {

    private PathAnimView mPathAnimView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path_anim);

        initView();
    }

    public void initView() {
        mPathAnimView = (PathAnimView) findViewById(R.id.path_anim_pathAnimView);

        // 还在完善中，工具类简单的SVG可以转path，复杂点的就乱了
        SVGParser svgParser = new SVGParser();

        String str = getString(R.string.qianbihua);

        try {
            Path path = SVGParser.parsePath(str);
            mPathAnimView.setSourcePath(path);
        } catch (Exception e) {
            e.printStackTrace();
        }

        mPathAnimView.setInfinite(true);

        mPathAnimView.startAnim(10000);
    }

    public void button(View v) {
        Intent intent = new Intent(this, SVGHandWritingActivity.class);
        startActivity(intent);
    }

    public void button11(View v) {
        Intent intent = new Intent(this, PathTestActivity.class);
        startActivity(intent);
    }
}
