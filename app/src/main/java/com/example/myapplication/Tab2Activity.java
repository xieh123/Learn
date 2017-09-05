package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.myapplication.ui.tab2.AdvertisingActivity;
import com.example.myapplication.ui.tab2.Behavior11Activity;
import com.example.myapplication.ui.tab2.BehaviorActivity;
import com.example.myapplication.ui.tab2.CacheActivity;
import com.example.myapplication.ui.tab2.ContactsActivity;
import com.example.myapplication.ui.tab2.DashboardActivity;
import com.example.myapplication.ui.tab2.EditMenuActivity;
import com.example.myapplication.ui.tab2.EmojiActivity;
import com.example.myapplication.ui.tab2.ExifInfoActivity;
import com.example.myapplication.ui.tab2.GaussianActivity;
import com.example.myapplication.ui.tab2.GuideActivity;
import com.example.myapplication.ui.tab2.LoadMoreActivity;
import com.example.myapplication.ui.tab2.LoaderViewActivity;
import com.example.myapplication.ui.tab2.MagnifierActivity;
import com.example.myapplication.ui.tab2.DrawableActivity;
import com.example.myapplication.ui.tab2.SceneTransitionAnimationActivity;
import com.example.myapplication.ui.tab2.TransitionActivity;
import com.example.myapplication.ui.tab2.View11Activity;
import com.example.myapplication.ui.tab2.ViewActivity;

/**
 * Created by xieH on 2017/6/15 0015.
 */
public class Tab2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab2);

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

    public void guide(View v) {
        Intent intent = new Intent(this, GuideActivity.class);
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
}
