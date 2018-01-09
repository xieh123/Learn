package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.myapplication.base.BaseActivity;
import com.example.myapplication.ui.tab2.AdvertisingActivity;
import com.example.myapplication.ui.tab2.Behavior11Activity;
import com.example.myapplication.ui.tab2.BehaviorActivity;
import com.example.myapplication.ui.tab2.BroadcastActivity;
import com.example.myapplication.ui.tab2.CacheActivity;
import com.example.myapplication.ui.tab2.ChronometerActivity;
import com.example.myapplication.ui.tab2.ContactsActivity;
import com.example.myapplication.ui.tab2.DashboardActivity;
import com.example.myapplication.ui.tab2.DeviceInfoActivity;
import com.example.myapplication.ui.tab2.DrawableActivity;
import com.example.myapplication.ui.tab2.EditMenuActivity;
import com.example.myapplication.ui.tab2.EmojiActivity;
import com.example.myapplication.ui.tab2.ExifInfoActivity;
import com.example.myapplication.ui.tab2.GaussianActivity;
import com.example.myapplication.ui.tab2.ItemSlideActivity;
import com.example.myapplication.ui.tab2.LoadMoreActivity;
import com.example.myapplication.ui.tab2.LoaderViewActivity;
import com.example.myapplication.ui.tab2.MagnifierActivity;
import com.example.myapplication.ui.tab2.VideoPlayActivity;
import com.example.myapplication.ui.tab2.SceneTransitionAnimationActivity;
import com.example.myapplication.ui.tab2.TransitionActivity;
import com.example.myapplication.ui.tab2.VegaScrollActivity;
import com.example.myapplication.ui.tab2.View11Activity;
import com.example.myapplication.ui.tab2.ViewActivity;
import com.example.myapplication.ui.tab2.VoiceBroadcastActivity;
import com.example.myapplication.ui.tab2.VoicePlayActivity;

/**
 * Created by xieH on 2017/6/15 0015.
 */
public class Tab2Activity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_tab2;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initView() {

    }

    public void gaussian(View v) {
        Intent intent = new Intent(this, GaussianActivity.class);
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

    public void transition(View v) {
        Intent intent = new Intent(this, TransitionActivity.class);
        startActivity(intent);
    }

    public void dashboard(View v) {
        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
    }

    public void behavior(View v) {
        Intent intent = new Intent(this, BehaviorActivity.class);
        startActivity(intent);
    }

    public void behavior11(View v) {
        Intent intent = new Intent(this, Behavior11Activity.class);
        startActivity(intent);
    }

    public void loaderView(View v) {
        Intent intent = new Intent(this, LoaderViewActivity.class);
        startActivity(intent);
    }

    public void contacts(View v) {
        Intent intent = new Intent(this, ContactsActivity.class);
        startActivity(intent);
    }

    public void magnifier(View v) {
        Intent intent = new Intent(this, MagnifierActivity.class);
        startActivity(intent);
    }

    public void activityAnimation(View v) {
        Intent intent = new Intent(this, SceneTransitionAnimationActivity.class);
        startActivity(intent);
    }

    public void cache(View v) {
        Intent intent = new Intent(this, CacheActivity.class);
        startActivity(intent);
    }

    public void editMenu(View v) {
        Intent intent = new Intent(this, EditMenuActivity.class);
        startActivity(intent);
    }

    public void playVideo(View v) {
        Intent intent = new Intent(this, VideoPlayActivity.class);
        startActivity(intent);
    }

    public void emoji(View v) {
        Intent intent = new Intent(this, EmojiActivity.class);
        startActivity(intent);
    }

    public void drawable(View v) {
        Intent intent = new Intent(this, DrawableActivity.class);
        startActivity(intent);
    }

    public void loadMore(View v) {
        Intent intent = new Intent(this, LoadMoreActivity.class);
        startActivity(intent);
    }

    public void exif(View v) {
        Intent intent = new Intent(this, ExifInfoActivity.class);
        startActivity(intent);
    }

    public void advertising(View v) {
        Intent intent = new Intent(this, AdvertisingActivity.class);
        startActivity(intent);
    }

    public void chronometer(View v) {
        Intent intent = new Intent(this, ChronometerActivity.class);
        startActivity(intent);
    }

    public void vegaScroll(View v) {
        Intent intent = new Intent(this, VegaScrollActivity.class);
        startActivity(intent);
    }

    public void itemSlide(View v) {
        Intent intent = new Intent(this, ItemSlideActivity.class);
        startActivity(intent);
    }

    public void deviceInfo(View v) {
        Intent intent = new Intent(this, DeviceInfoActivity.class);
        startActivity(intent);
    }

    public void broadcast(View v) {
        Intent intent = new Intent(this, BroadcastActivity.class);
        startActivity(intent);
    }

    public void voiceBroadcast(View v) {
        Intent intent = new Intent(this, VoiceBroadcastActivity.class);
        startActivity(intent);
    }

    public void voicePlay(View v) {
        Intent intent = new Intent(this, VoicePlayActivity.class);
        startActivity(intent);
    }
}
