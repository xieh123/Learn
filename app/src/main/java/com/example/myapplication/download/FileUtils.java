package com.example.myapplication.download;

import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by xieH on 2017/1/16 0016.
 */
public class FileUtils {


    // 默认存放文件下载的路径
    public static String getDefaultSaveFilePath() {

        return Environment
                .getExternalStorageDirectory()
                + File.separator
                + "QuanMinYigou"
                + File.separator + "download" + File.separator;
    }

    /**
     * 写入文件
     *
     * @param in
     * @param file
     */
    public static void writeFile(InputStream in, File file) throws IOException {
        if (!file.getParentFile().exists())
            file.getParentFile().mkdirs();

        if (file != null && file.exists())
            file.delete();

        FileOutputStream out = new FileOutputStream(file);
        byte[] buffer = new byte[1024 * 128];
        int len = -1;
        while ((len = in.read(buffer)) != -1) {
            out.write(buffer, 0, len);
        }
        out.flush();
        out.close();
        in.close();

    }

}
