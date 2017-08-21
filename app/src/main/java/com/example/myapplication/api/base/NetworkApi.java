package com.example.myapplication.api.base;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by xieH on 2016/12/2 0002.
 */
public class NetworkApi {

    @POST("open/open.do")
    Observable<Object> post(@Query("ACID") int acid, @Body RequestBody entery) {
        return null;
    }

    @POST("open/open.do")
    Observable<ResponseInfo<Object>> response(@Query("ACID") int acid, @Body RequestBody entery) {
        return null;
    }
}
