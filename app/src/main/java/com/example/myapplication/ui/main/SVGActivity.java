package com.example.myapplication.ui.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.myapplication.R;
import com.example.myapplication.model.SVGModel;
import com.example.myapplication.svgandroid.CustomSVGParser;
import com.example.myapplication.svgandroid.SVG;
import com.jaredrummler.android.widget.AnimatedSvgView;

import java.util.HashMap;

/**
 * Created by xieH on 2016/11/30 0030.
 */
public class SVGActivity extends AppCompatActivity {

    private ImageView mImageView;

    //////////////

    private AnimatedSvgView mAnimatedSvgView;

    private int index = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_svg);

        initView();
    }

    public void initView() {
        mImageView = (ImageView) findViewById(R.id.svg_iv);
        mImageView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        HashMap<String, Integer> colorMap = new HashMap<>();
        colorMap.put("CN-34", getResources().getColor(R.color.color_01));
        colorMap.put("CN-50", getResources().getColor(R.color.color_02));

        SVG svg = CustomSVGParser.getSVGFromResource(getResources(), R.raw.china, colorMap);
        mImageView.setImageDrawable(svg.createPictureDrawable());

        /////////////////////

        mAnimatedSvgView = (AnimatedSvgView) findViewById(R.id.animatedSvgView);
        mAnimatedSvgView.postDelayed(new Runnable() {

            @Override
            public void run() {
                mAnimatedSvgView.start();
            }
        }, 500);
    }

    public void next(View v) {
        if (++index >= SVGModel.values().length) {
            index = 0;
        }
        setSvg(SVGModel.values()[index]);
    }

    private void setSvg(SVGModel svg) {
        mAnimatedSvgView.setGlyphStrings(svg.glyphs);
        mAnimatedSvgView.setFillColors(svg.colors);
        mAnimatedSvgView.setViewportSize(svg.width, svg.height);
        mAnimatedSvgView.setTraceResidueColor(0x32cd0000);
        mAnimatedSvgView.setTraceColors(svg.colors);
        mAnimatedSvgView.rebuildGlyphData();
        mAnimatedSvgView.start();
    }
}
