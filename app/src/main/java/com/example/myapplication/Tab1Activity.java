package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.myapplication.ui.tab1.ActivityAnimationActivity;
import com.example.myapplication.ui.tab1.BottomSheetActivity;
import com.example.myapplication.ui.tab1.CountdownActivity;
import com.example.myapplication.ui.tab1.DialogActivity;
import com.example.myapplication.ui.tab1.EditLayoutActivity;
import com.example.myapplication.ui.tab1.EraserActivity;
import com.example.myapplication.ui.tab1.ExpandableTextViewActivity;
import com.example.myapplication.ui.tab1.FlexboxLayoutActivity;
import com.example.myapplication.ui.tab1.FlipViewActivity;
import com.example.myapplication.ui.tab1.FoldRecyclerViewActivity;
import com.example.myapplication.ui.tab1.FragmentActivity;
import com.example.myapplication.ui.tab1.Gif11Activity;
import com.example.myapplication.ui.tab1.GifActivity;
import com.example.myapplication.ui.tab1.GotoQQActivity;
import com.example.myapplication.ui.tab1.GraffitiActivity;
import com.example.myapplication.ui.tab1.ImageDescriptionActivity;
import com.example.myapplication.ui.tab1.ListActivity;
import com.example.myapplication.ui.tab1.ProgressiveImageActivity;
import com.example.myapplication.ui.tab1.RecyclerDecorationActivity;
import com.example.myapplication.ui.tab1.SwipeLayoutActivity;
import com.example.myapplication.ui.tab1.VideoSeekBarActivity;
import com.example.myapplication.ui.tab1.View11Activity;
import com.example.myapplication.ui.tab1.View22Activity;
import com.example.myapplication.ui.tab1.View33Activity;
import com.example.myapplication.ui.tab1.ViewActivity;
import com.example.myapplication.ui.tab1.ViewDragHelperActivity;
import com.example.myapplication.ui.tab1.ViewPager11Activity;
import com.example.myapplication.ui.tab1.ViewPagerActivity;
import com.example.myapplication.ui.timeline.TimeLineActivity;

/**
 * Created by xieH on 2017/2/13 0013.
 */
public class Tab1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab1);

    }

    public void gotoQQ(View v) {
        Intent intent = new Intent(this, GotoQQActivity.class);
        startActivity(intent);
    }

    public void countdown(View v) {
        Intent intent = new Intent(this, CountdownActivity.class);
        startActivity(intent);
    }

    public void gif(View v) {
        Intent intent = new Intent(this, GifActivity.class);
        startActivity(intent);
    }

    public void imageDescription(View v) {
        Intent intent = new Intent(this, ImageDescriptionActivity.class);
        startActivity(intent);
    }

    public void bottomSheets(View v) {
        Intent intent = new Intent(this, BottomSheetActivity.class);
        startActivity(intent);
    }

    public void foldRecyclerView(View v) {
        Intent intent = new Intent(this, FoldRecyclerViewActivity.class);
        startActivity(intent);
    }

    public void swipeLayout(View v) {
        Intent intent = new Intent(this, SwipeLayoutActivity.class);
        startActivity(intent);
    }

    public void dragHelper(View v) {
        Intent intent = new Intent(this, ViewDragHelperActivity.class);
        startActivity(intent);
    }

    public void activityAnimation(View v) {
        Intent intent = new Intent(this, ActivityAnimationActivity.class);
        startActivity(intent);
    }

    public void dividerItemDecoration(View v) {
        Intent intent = new Intent(this, RecyclerDecorationActivity.class);
        startActivity(intent);
    }


    public void view(View v) {
        Intent intent = new Intent(this, ViewActivity.class);
        startActivity(intent);
    }

    public void view11(View v) {
        Intent intent = new Intent(this, View11Activity.class);
        startActivity(intent);
    }


    public void view22(View v) {
        Intent intent = new Intent(this, View22Activity.class);
        startActivity(intent);
    }

    public void viewPager(View v) {
        Intent intent = new Intent(this, ViewPagerActivity.class);
        startActivity(intent);
    }

    public void viewPager11(View v) {
        Intent intent = new Intent(this, ViewPager11Activity.class);
        startActivity(intent);
    }

    public void flexboxLayout(View v) {
        Intent intent = new Intent(this, FlexboxLayoutActivity.class);
        startActivity(intent);
    }

    public void fragment(View v) {
        Intent intent = new Intent(this, FragmentActivity.class);
        startActivity(intent);
    }

    public void editGif(View v) {
        Intent intent = new Intent(this, Gif11Activity.class);
        startActivity(intent);
    }

    public void graffiti(View v) {
        Intent intent = new Intent(this, GraffitiActivity.class);
        startActivity(intent);
    }

    public void dialogFragment(View v) {
        Intent intent = new Intent(this, DialogActivity.class);
        startActivity(intent);
    }

    public void progressiveImage(View view) {
        Intent intent = new Intent(this, ProgressiveImageActivity.class);
        startActivity(intent);
    }

    public void expandableTextView(View v) {
        Intent intent = new Intent(this, ExpandableTextViewActivity.class);
        startActivity(intent);
    }

    public void editLayout(View v) {
        Intent intent = new Intent(this, EditLayoutActivity.class);
        startActivity(intent);
    }

    public void view33(View v) {
        Intent intent = new Intent(this, View33Activity.class);
        startActivity(intent);
    }


    public void videoSeekbar(View v) {
        Intent intent = new Intent(this, VideoSeekBarActivity.class);
        startActivity(intent);
    }

    public void eraser(View v) {
        Intent intent = new Intent(this, EraserActivity.class);
        startActivity(intent);
    }

    public void flipView(View v) {
        Intent intent = new Intent(this, FlipViewActivity.class);
        startActivity(intent);
    }

    public void timeline(View v) {
        Intent intent = new Intent(this, TimeLineActivity.class);
        startActivity(intent);
    }

    public void list(View v) {
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }
}
