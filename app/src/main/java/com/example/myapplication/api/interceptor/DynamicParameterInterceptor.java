package com.example.myapplication.api.interceptor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by xieH on 2016/12/2 0002.
 */
public class DynamicParameterInterceptor implements Interceptor {

    private HashMap<String, String> map;

    public DynamicParameterInterceptor(HashMap<String, String> map) {
        this.map = map;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        //get请求后面追加共同的参数
        HttpUrl.Builder builder = request.url().newBuilder();
        Iterator iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            builder.addQueryParameter((String) entry.getKey(), (String) entry.getValue());
        }
        request = request.newBuilder().url(builder.build()).build();
        return chain.proceed(request);
    }
}
