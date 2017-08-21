package com.example.myapplication.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by xieH on 2017/4/27 0027.
 */
public class HttpUrlFetcher {

    private static final String TAG = "HttpUrlFetcher";

    private HttpURLConnection urlConnection;
    private int size;

    public int getSize() {
        return size;
    }

    public InputStream loadData(URL url) throws IOException {
        urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setConnectTimeout(2500);
        urlConnection.setReadTimeout(2500);
        urlConnection.setUseCaches(false);
        urlConnection.setDoInput(true);
        size = urlConnection.getContentLength();
        final int statusCode = urlConnection.getResponseCode();
        if (statusCode / 100 == 2) {
            return urlConnection.getInputStream();
        }
        return null;
    }
}
