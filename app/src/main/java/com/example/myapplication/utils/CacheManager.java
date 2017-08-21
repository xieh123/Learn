package com.example.myapplication.utils;

import android.content.Context;

import com.google.gson.Gson;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

/**
 * Created by xieH on 2017/6/27 0027.
 */
public class CacheManager {

    /**
     * 保存 json 到本地
     *
     * @param context
     * @param fileName
     * @param json
     */
    public static void saveJsonToFile(Context context, String fileName, String json) {
        String path = context.getCacheDir() + File.separator + fileName + ".";
        File file = new File(path);
        FileOutputStream os = null;
        try {
            if (!file.exists()) {
                file.createNewFile();
            }

            os = new FileOutputStream(file);
            os.write(json.getBytes("utf-8"));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(os);
        }
    }

    /**
     * 从本地获取 json
     *
     * @param context
     * @param fileName
     * @param cla
     * @param <T>
     * @return
     */
    public static <T> T readJsonFromFile(Context context, String fileName, Class<T> cla) {
        if (cla == null) {
            return null;
        }

        String path = context.getCacheDir() + File.separator + fileName + ".";
        File file = new File(path);
        if (!file.exists()) {
            return null;
        }

        Reader reader = null;
        try {
            reader = new FileReader(file);
            return new Gson().fromJson(reader, cla);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(reader);
        }

        return null;
    }

    /**
     * 关闭流
     *
     * @param closeables Closeable
     */
    @SuppressWarnings("WeakerAccess")
    public static void close(Closeable... closeables) {
        if (closeables == null || closeables.length == 0)
            return;
        for (Closeable closeable : closeables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
