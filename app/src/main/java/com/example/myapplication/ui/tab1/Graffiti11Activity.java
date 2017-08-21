package com.example.myapplication.ui.tab1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.myapplication.R;
import com.example.myapplication.widget.graffiti.AnnotationConfig;
import com.example.myapplication.widget.graffiti.ScrawlBoardView;

/**
 * Created by xieH on 2017/4/14 0014.
 */
public class Graffiti11Activity extends AppCompatActivity implements View.OnClickListener {

    private ScrawlBoardView mScrawlBoardView;

    private RadioGroup mColorRadioGroup;

    private ImageView mCancelIv, mCleanIv, mEraserIv;

    private int[] colors = new int[]{R.color.red, R.color.orange, R.color.yellow,
            R.color.green, R.color.blue, R.color.purple};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graffiti11);

        initView();
    }

    public void initView() {
        mScrawlBoardView = (ScrawlBoardView) findViewById(R.id.graffiti_scrawlBoardView);
        mColorRadioGroup = (RadioGroup) findViewById(R.id.graffiti_color_group);

        mCancelIv = (ImageView) findViewById(R.id.graffiti_cancel_iv);
        mCleanIv = (ImageView) findViewById(R.id.graffiti_clean_iv);
        mEraserIv = (ImageView) findViewById(R.id.graffiti_eraser_iv);

        mCancelIv.setOnClickListener(this);
        mCleanIv.setOnClickListener(this);
        mEraserIv.setOnClickListener(this);


        String path = getIntent().getStringExtra("path");
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        mScrawlBoardView.setBackgroud(bitmap);


        for (int i = 0; i < colors.length; i++) {
            Drawable drawable = ContextCompat.getDrawable(this, R.drawable.ring);
            drawable.setColorFilter(ContextCompat.getColor(this, colors[i]), PorterDuff.Mode.SRC_ATOP);

            RadioButton radioButton = new RadioButton(this);
            radioButton.setBackground(drawable);

//            radioButton.setBackgroundResource(R.drawable.ic_error);
//            radioButton.setButtonDrawable(android.R.color.transparent);

            mColorRadioGroup.addView(radioButton);
        }

        mColorRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case 1:
                        mScrawlBoardView.setPaintType(AnnotationConfig.PaintType.Paint_Red);
                        break;
                    case 2:
                        mScrawlBoardView.setPaintType(AnnotationConfig.PaintType.Paint_Orange);
                        break;
                    case 3:
                        mScrawlBoardView.setPaintType(AnnotationConfig.PaintType.Paint_Yellow);
                        break;
                    case 4:
                        mScrawlBoardView.setPaintType(AnnotationConfig.PaintType.Paint_Green);
                        break;
                    case 5:
                        mScrawlBoardView.setPaintType(AnnotationConfig.PaintType.Paint_Blue);
                        break;
                    case 6:
                        mScrawlBoardView.setPaintType(AnnotationConfig.PaintType.Paint_Purple);
                        break;
                    default:
                        break;
                }
            }
        });


        //   bitmap = mScrawlBoardView.getSrawBoardBitmap();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.graffiti_cancel_iv:
                mScrawlBoardView.cancelPath();
                break;
            case R.id.graffiti_clean_iv:
                mScrawlBoardView.clearScrawlBoard();
                break;
            case R.id.graffiti_eraser_iv:
                mScrawlBoardView.setPaintType(AnnotationConfig.PaintType.Paint_Eraser);
                break;
            default:
                break;
        }
    }
}
