package com.example.myapplication.api.interceptor;

import android.content.Context;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by xieH on 2016/12/2 0002.
 */
public class HeaderInterceptor implements Interceptor {
    private Context context;

    public HeaderInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        Request.Builder requestBuilder = original.newBuilder()
                .header("sessionId", "sessionId"); //添加sessionId
        //     .header("sessionId", CommonData.getUserInfo(context).sessionId); //添加sessionId
        Request request = requestBuilder.build();
        return chain.proceed(request);
    }
}
