package com.example.myapplication.ui.tab2;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.transform.ChatTransformation;
import com.example.myapplication.ui.tab2.chat.EmojiAdapter;

/**
 * Created by xieH on 2017/8/1 0001.
 */
public class EmojiActivity extends AppCompatActivity implements EmojiAdapter.OnEmojiClickListener {

    private EditText mEditText;

    private RecyclerView mRecyclerView;

    private ImageView mImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emoji);

        initView();
    }

    private void initView() {
        mEditText = (EditText) findViewById(R.id.editText);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        EmojiAdapter adapter = new EmojiAdapter(this, this);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 6));
        mRecyclerView.setAdapter(adapter);

        mImageView = (ImageView) findViewById(R.id.imageView);

//        final Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_error);
//        mImageView.setImageBitmap(getChatBitmap(bitmap));

        Glide.with(this)
                .load(R.drawable.ic_error)
                .transform(new ChatTransformation(this, R.drawable.chat_img_to_bg_mask_press))
                .into(mImageView);

    }

    @Override
    public void onEmojiCLick(String emoji) {
        mEditText.setText(mEditText.getText().append(emoji));
        mEditText.setSelection(mEditText.getText().length());
    }

    public Bitmap getChatBitmap(Bitmap bitmap) {
        bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);

        ImageView imageView = new ImageView(this);
        imageView.setBackgroundResource(R.drawable.chat_img_to_bg_mask_press);
        imageView.setImageBitmap(bitmap);

        Bitmap backgroundBitmap = drawableToBitmap(imageView.getBackground(), bitmap.getWidth(), bitmap.getHeight());

        Paint mPaint = new Paint();
        Canvas canvas = new Canvas(backgroundBitmap);

        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, 0, 0, mPaint);

        return backgroundBitmap;
    }

    public Bitmap drawableToBitmap(Drawable drawable, int w, int h) {
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap;
    }
}
