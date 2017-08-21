package com.example.myapplication.api.interceptor;

import android.text.TextUtils;
import android.util.Log;

import com.example.myapplication.api.RtHttp;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;
import okio.BufferedSource;

/**
 * Created by xieH on 2016/12/2 0002.
 */
public class LogInterceptor implements Interceptor {

    private static final String TAG = "LogInterceptor";
    private static final Charset UTF8 = Charset.forName("UTF-8");

    @Override
    public Response intercept(Chain chain) throws IOException {
        Log.d(TAG, "before chain,request()");
        Request request = chain.request();
        Response response;
        try {
            long t1 = System.nanoTime();
            response = chain.proceed(request);
            long t2 = System.nanoTime();
            double time = (t2 - t1) / 1e6d;
            String acid = request.url().queryParameter("ACID");     //本项目log特定参数项目接口acid
            String userId = request.url().queryParameter("userId"); //本项目log特定参数用户id
            String type = "";
            if (request.method().equals("GET")) {
                type = "GET";
            } else if (request.method().equals("POST")) {
                type = "POST";
            } else if (request.method().equals("PUT")) {
                type = "PUT";
            } else if (request.method().equals("DELETE")) {
                type = "DELETE";
            }
            BufferedSource source = response.body().source();
            source.request(Long.MAX_VALUE); // Buffer the entire body.
            Buffer buffer = source.buffer();
            String logStr = "\n--------------------".concat(TextUtils.isEmpty(acid) ? "" : acid).concat("  begin--------------------\n")
                    .concat(type)
                    .concat("\nacid->").concat(TextUtils.isEmpty(acid) ? "" : acid)
                    .concat("\nuserId->").concat(TextUtils.isEmpty(userId) ? "" : userId)
                    .concat("\nnetwork code->").concat(response.code() + "")
                    .concat("\nurl->").concat(request.url() + "")
                    .concat("\ntime->").concat(time + "")
                    .concat("\nrequest headers->").concat(request.headers() + "")
                    .concat("request->").concat(bodyToString(request.body()))
                    .concat("\nbody->").concat(buffer.clone().readString(UTF8));
            Log.i(RtHttp.TAG, logStr);
        } catch (Exception e) {
            throw e;
        }
        return response;
    }

    private static String bodyToString(final RequestBody request) {

        try {
            final Buffer buffer = new Buffer();
            request.writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }

}
