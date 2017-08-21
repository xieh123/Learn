package com.example.myapplication.ui.tab2.chat;

/**
 * Created by xieH on 2017/8/1 0001.
 */
public class EmojiUtils {

    public static String getEmojiStringByUnicode(int unicode) {
        return new String(Character.toChars(unicode));
    }
}
