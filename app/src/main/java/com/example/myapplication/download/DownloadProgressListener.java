package com.example.myapplication.download;

/**
 * Created by xieH on 2017/1/16 0016.
 */
public interface DownloadProgressListener {
    void update(long bytesRead, long contentLength, boolean done);
}
