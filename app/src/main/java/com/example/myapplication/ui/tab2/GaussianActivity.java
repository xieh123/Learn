package com.example.myapplication.ui.tab2;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.myapplication.R;

/**
 * Created by xieH on 2017/6/15 0015.
 */
public class GaussianActivity extends AppCompatActivity {

    private ImageView imageView;
    private ImageView container;
    private LinearLayout layout;
    private TextView textViewProgress;
    private RenderScriptGaussianBlur blur;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gaussian);


        initView();
    }

    private void initView() {
        imageView = (ImageView) findViewById(R.id.imageView);
        container = (ImageView) findViewById(R.id.container);

        container.setVisibility(View.GONE);

        layout = (LinearLayout) findViewById(R.id.layout);
        layout.setVisibility(View.VISIBLE);

        SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);
        textViewProgress = (TextView) findViewById(R.id.textViewProgress);
        TextView textViewDialog = (TextView) findViewById(R.id.textViewDialog);
        blur = new RenderScriptGaussianBlur(this);

        seekBar.setMax(25);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textViewProgress.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int radius = seekBar.getProgress();
                if (radius < 1) {
                    radius = 1;
                }
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.welcome_thirdly);
                imageView.setImageBitmap(blur.gaussianBlur(radius, bitmap));
            }
        });

        textViewDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                container.setVisibility(View.VISIBLE);

                layout.setDrawingCacheEnabled(true);
                layout.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_LOW);

                Bitmap bitmap = layout.getDrawingCache();

                container.setImageBitmap(blur.gaussianBlur(25, bitmap));

                layout.setVisibility(View.INVISIBLE);

                AlertDialog dialog = new AlertDialog.Builder(GaussianActivity.this).create();
                dialog.setTitle("Title");
                dialog.setMessage("Message");
                dialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {

                    }
                });

                dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        container.setVisibility(View.GONE);
                        layout.setVisibility(View.VISIBLE);
                    }
                });

                dialog.show();
            }
        });

    }
}
