package com.example.myapplication.download;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by xieH on 2016/10/24.
 */
public interface ApiService {

    @GET
    Observable<ResponseBody> download(@Url String url);

}
