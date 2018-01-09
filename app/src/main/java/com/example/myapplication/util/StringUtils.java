package com.example.myapplication.util;

import android.support.annotation.ColorInt;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * Created by xieH on 2017/5/22 0022.
 */
public class StringUtils {

    /**
     * 关键字高亮变色
     *
     * @param text    文字
     * @param keyword 文字中的关键字
     * @param color   变化的色值
     * @return
     */
    public static SpannableStringBuilder matcherTextStyle(String text, String keyword, @ColorInt int color) {
        SpannableStringBuilder s = new SpannableStringBuilder(text);
        Pattern p = Pattern.compile(keyword);
        Matcher m = p.matcher(s);
        while (m.find()) {
            int start = m.start();
            int end = m.end();
            s.setSpan(new ForegroundColorSpan(color), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return s;
    }

    /**
     * 多个关键字高亮变色
     *
     * @param text     文字
     * @param keywords 文字中的关键字数组
     * @param color    变化的色值
     * @return
     */
    public static SpannableStringBuilder matcherTextStyle(String text, String[] keywords, @ColorInt int color) {
        SpannableStringBuilder s = new SpannableStringBuilder(text);
        for (int i = 0; i < keywords.length; i++) {
            Pattern p = Pattern.compile(keywords[i]);
            Matcher m = p.matcher(s);
            while (m.find()) {
                int start = m.start();
                int end = m.end();
                s.setSpan(new ForegroundColorSpan(color), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        return s;
    }
}
