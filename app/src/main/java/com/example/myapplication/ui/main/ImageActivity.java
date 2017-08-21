package com.example.myapplication.ui.main;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.myapplication.R;
import com.example.myapplication.utils.FileUtils;
import com.example.myapplication.utils.ScreenUtils;
import com.example.myapplication.widget.sticker.Sticker;
import com.example.myapplication.widget.sticker.StickerView;

import java.io.File;

/**
 * Created by Administrator on 2016/11/24 0024.
 */
public class ImageActivity extends AppCompatActivity {

    private StickerView mStickerView;

    private LinearLayout imageLl;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        initView();

    }

    public void initView() {

        mStickerView = (StickerView) findViewById(R.id.image_sticker_view);

        //   mStickerView.setLooked(false);

        mStickerView.setOnStickerClickListener(new StickerView.OnStickerClickListener() {

            @Override
            public void onStickerClick(Sticker sticker) {
                //  mStickerView.replace(new BitmapDrawable(getResources(), bitmapReplace));
            }
        });

        imageLl = (LinearLayout) findViewById(R.id.images_ll);

        for (int i = 0; i < imageLl.getChildCount(); i++) {
            final ImageView mImageView = (ImageView) imageLl.getChildAt(i);

            mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //  Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.haizewang_150);

                    Drawable mDrawable = mImageView.getDrawable();

                    mStickerView.addSticker(mDrawable);
                }
            });
        }
    }

    public void add(View v) {


        EditDialogFragment editDialogFragment = new EditDialogFragment();

        editDialogFragment.setOnAddClickListener(new EditDialogFragment.OnAddClickListener() {
            @Override
            public void add(String inputText, int textSize, int color) {

                //  textBitmap = ImageUtils.drawTextToCenter(ImageActivity.this, waterBitmap, inputText, textSize, color);

                int with = ScreenUtils.getScreenWidth(ImageActivity.this);

                Bitmap bitmap = Bitmap.createBitmap(with, 200, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                canvas.drawColor(ContextCompat.getColor(ImageActivity.this, R.color.transparent));


//                Paint paint = new Paint();
//                paint.setTextAlign(Paint.Align.LEFT);
//                paint.setAntiAlias(true);
//                paint.setTextSize(20);

//                canvas.drawText(inputText, 20, 30, paint);
//                canvas.save(Canvas.ALL_SAVE_FLAG);
//                canvas.restore();

                TextPaint mCurrentPaint = new TextPaint();

                mCurrentPaint.setTextAlign(Paint.Align.LEFT); // 若设置为center，则文本左半部分显示不全 paint.setColor(Color.RED);
                mCurrentPaint.setColor(color);
                mCurrentPaint.setTextSize(50);
                mCurrentPaint.setAntiAlias(true); // 消除锯齿

                StaticLayout layout = new StaticLayout(inputText, mCurrentPaint, with - 20, Layout.Alignment.ALIGN_NORMAL, 1.0F, 0.0F, true);
                canvas.translate(20, 20);
                layout.draw(canvas);

                mStickerView.addSticker(bitmap);
            }
        });

        editDialogFragment.show(getSupportFragmentManager(), null);


    }


    public void save(View v) {

        String filePath = FileUtils.getNewFilePath(ImageActivity.this, "Sticker");

        File file = new File(filePath);

        if (file != null) {
            mStickerView.save(file);
        }


        Intent intent = new Intent(ImageActivity.this, ImageDetailActivity.class);
        intent.putExtra("imagePath", filePath);
        startActivity(intent);
    }
}
