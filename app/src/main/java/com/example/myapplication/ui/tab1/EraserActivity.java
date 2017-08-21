package com.example.myapplication.ui.tab1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.example.myapplication.R;

/**
 * Created by xieH on 2017/6/1 0001.
 */
public class EraserActivity extends AppCompatActivity implements View.OnTouchListener {

    private ImageView mImageView;

    private Bitmap blankBitmap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eraser);

        initView();
    }

    public void initView() {
        mImageView = (ImageView) findViewById(R.id.eraser_iv);

        BitmapFactory.Options opts = new BitmapFactory.Options(); //图片加载器，用于配置一些缩放比例，和像素单位
        opts.inSampleSize = 2; //制定加载器把原图片的宽高缩放到2/1的效果加载
        //获得外层图片,decodeResource方法默认获得的像素单位是RGB(red,green,blue),ARGB(alpha,red,green,blue)
        Bitmap topImage = BitmapFactory.decodeResource(getResources(), R.drawable.welcome_thirdly, opts);

        //创建一张空白图片，并且把图片想读单位指定为:ARGB
        blankBitmap = Bitmap.createBitmap(topImage.getWidth(), topImage.getHeight(), Bitmap.Config.ARGB_4444);

        //把上边的topImage画到空白图片上
        Canvas canvas = new Canvas(blankBitmap);
        //把topImage画到空白图片上但是像素单位变成ARGB()
        canvas.drawBitmap(topImage, 0, 0, null);
        mImageView.setImageBitmap(blankBitmap);

        mImageView.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
            //获得按下坐标
            int x = (int) motionEvent.getX();
            int y = (int) motionEvent.getY();

            for (int i = x - 10; i < x + 10; i++) {
                for (int j = y - 10; j < y + 10; j++) {
                    //防止超出边界
                    if (j >= 0 && j < blankBitmap.getHeight() && i >= 0 && i < blankBitmap.getWidth()) {
                        blankBitmap.setPixel(i, j, Color.TRANSPARENT);
                    }
                }
            }
            //修改后的图片设置给ImageView
            mImageView.setImageBitmap(blankBitmap);
        }

        return true;
    }
}
