package com.example.myapplication.ui.tab1;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import com.example.myapplication.R;
import com.example.myapplication.widget.AnimatedDoorLayout;

/**
 * Created by xieH on 2017/3/8 0008.
 */
public class ActivityAnimationActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private RadioButton mHorizontalBtn;

    private RadioButton mVerticalBtn;

    private int mDoorType = AnimatedDoorLayout.HORIZONTAL_DOOR;


    private Button mArgbBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_animation);

        initView();

        startAnimator();
    }


    public void initView() {
        mHorizontalBtn = (RadioButton) findViewById(R.id.activity_animation_horizontal_bt);
        mVerticalBtn = (RadioButton) findViewById(R.id.activity_animation_vertical_bt);

        mHorizontalBtn.setOnCheckedChangeListener(this);
        mVerticalBtn.setOnCheckedChangeListener(this);

        mArgbBtn = (Button) findViewById(R.id.activity_animation_argb_bt);

    }

    public void open(View v) {
        Intent intent = new Intent(this, DoorActivity.class);

        intent.putExtra("door_type", mDoorType);
        startActivity(intent);

        overridePendingTransition(0, 0);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

        switch (compoundButton.getId()) {
            case R.id.activity_animation_horizontal_bt:
                if (isChecked) {
                    mDoorType = AnimatedDoorLayout.HORIZONTAL_DOOR;
                }
                break;
            case R.id.activity_animation_vertical_bt:
                if (isChecked) {
                    mDoorType = AnimatedDoorLayout.VERTICAL_DOOR;
                }
                break;
            default:
                break;
        }
    }


    public void startAnimator() {
        int colorStart = ContextCompat.getColor(this, R.color.black);
        int colorEnd = ContextCompat.getColor(this, R.color.red);

        ValueAnimator valueAnimator = ValueAnimator.ofObject(new ArgbEvaluator(), colorStart, colorEnd);
        valueAnimator.setDuration(20000);

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mArgbBtn.setBackgroundColor((Integer) animation.getAnimatedValue());
            }
        });

        valueAnimator.start();
    }
}
