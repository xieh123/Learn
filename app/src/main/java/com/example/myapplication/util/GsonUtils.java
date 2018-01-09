package com.example.myapplication.util;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xieH on 2017/3/28 0028.
 */
public class GsonUtils {


    private static Gson gson = new Gson();

    /**
     * 将Json数据解析成相应的映射对象
     *
     * @param jsonData
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T fromJson(String jsonData, Class<T> type) {
        T result = null;
        if (jsonData != null) {
            result = gson.fromJson(jsonData, type);
        }
        return result;
    }


    /**
     * 将Json数组解析成相应的映射对象列表
     *
     * @param jsonData
     * @param type
     * @param <T>
     * @return
     */
    public static <T> List<T> fromJsonArray(String jsonData, Class<T> type) {
        List<T> list = new ArrayList<>();

        if (!TextUtils.isEmpty(jsonData)) {
            JsonArray array = new JsonParser().parse(jsonData).getAsJsonArray();
            for (final JsonElement elem : array) {
                list.add(gson.fromJson(elem, type));
            }
        }

        return list;
    }
}
