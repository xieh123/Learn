package com.example.myapplication.ui.main;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.util.switchthmelib.Colorful;

/**
 * Created by xieH on 2016/12/23 0023.
 */
public class ChangeThemeActivity extends AppCompatActivity {


    Colorful mColorful;

    boolean isNight = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initTheme();

        setContentView(R.layout.activity_change_theme);

        initView();

        setupColorful();
    }


    public void initView() {


    }

    /**
     * 设置本 Activity 的视图View与颜色属性之间的关联
     */
    private void setupColorful() {

        // 设置列表Item的属性关联
//        ViewGroupSetter listViewSetter = new ViewGroupSetter(mNewsListView);
//        // 绑定ListView的Item View中的news_title视图，在换肤时修改它的text_color属性
//        listViewSetter.childViewTextColor(R.id.news_title, R.attr.text_color);


        // 构建Colorful对象来绑定View与属性的对象关系
        mColorful = new Colorful.Builder(this)
//                .backgroundDrawable(R.id.change_theme_bt, R.attr.backgroundColor)  // 设置图标
                .backgroundColor(R.id.change_theme_ll, R.attr.backgroundColor)   // 设置背景色
                .backgroundColor(R.id.change_theme_bt, R.attr.buttonColor) // 设置背景色
                .textColor(R.id.change_theme_tv, R.attr.textColor)  // 设置 TextView 字体颜色
//                .setter(listViewSetter) // 手动设置setter
                .create();
    }

    public void change(View v) {
        showAnimation();

        String theme = "";

        if (!isNight) {
            mColorful.setTheme(R.style.NightTheme);

            theme = "NightTheme";
        } else {
            mColorful.setTheme(R.style.DayTheme);

            theme = "DayTheme";
        }

        isNight = !isNight;

        updateTheme(theme);
    }

    /**
     * 更新主题
     *
     * @param theme
     */
    public void updateTheme(String theme) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("theme", theme);
        editor.commit();
    }


    /**
     * 初始化主题
     * 在 setContentView()之前调用
     */
    public void initTheme() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        String theme = sp.getString("theme", "");

        if (theme.equals("DayTheme")) {
            this.setTheme(R.style.DayTheme);
        } else if (theme.equals("NightTheme")) {
            this.setTheme(R.style.NightTheme);
        } else {
            this.setTheme(R.style.DayTheme);
        }
    }

    /**
     * 展示一个切换动画
     */
    private void showAnimation() {
        final View decorView = getWindow().getDecorView();
        Bitmap cacheBitmap = getCacheBitmapFromView(decorView);

        if (decorView instanceof ViewGroup && cacheBitmap != null) {
            final View view = new View(this);
            view.setBackground(new BitmapDrawable(getResources(), cacheBitmap));
            ViewGroup.LayoutParams layoutParam = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            ((ViewGroup) decorView).addView(view, layoutParam);

            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "alpha", 1f, 0f);
            objectAnimator.setDuration(500);
            objectAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    ((ViewGroup) decorView).removeView(view);
                }
            });

            objectAnimator.start();
        }
    }

    /**
     * 获取一个 View 的缓存视图
     *
     * @param view
     * @return
     */
    private Bitmap getCacheBitmapFromView(View view) {
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache(true);
        Bitmap drawingCache = view.getDrawingCache();
        Bitmap bitmap = null;

        if (drawingCache != null) {
            bitmap = Bitmap.createBitmap(drawingCache);
            view.setDrawingCacheEnabled(false);
        }

        return bitmap;
    }

}
