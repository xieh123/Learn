package com.example.myapplication.ui.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

/**
 * Created by xieH on 2017/1/5 0005.
 */
public class RxJavaActivity extends AppCompatActivity {

    private static final String TAG = "RxJavaActivity";


    private PublishSubject<String> mSubject = PublishSubject.create();




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        initView();
    }


    public void initView() {


    }

    public void test() {

        // 使用timer做定时操作  // 例如：2秒后输出日志“hello world”，然后结束
        Observable.timer(2, TimeUnit.SECONDS)
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Long number) {
                        Log.i(TAG, "hello world");
                    }
                });


        // 使用interval做周期性操作  // 每隔2秒输出日志“hello world”
        Observable.interval(2, TimeUnit.SECONDS)
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Long number) {
                        Log.i(TAG, "hello world");
                    }
                });
    }




    public void RxJava() {

        // 使用 RxJava 去抖500ms
        mSubject.debounce(500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        //从网络或者本地搜索
                        //  queryMatch(s);
                    }
                }).subscribe();

        //////
        SearchView mSearchView = new SearchView(this);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!TextUtils.isEmpty(newText)) {
                    mSubject.onNext(newText);
                }

                return true;
            }
        });
    }


    public void test11(){

        /**
         *  但有时候可能一开始我们并不知道cityId，我们只知道cityName。
         *  所以就需要我们先访问服务器，拿到对应城市名的cityId，然后通过这个cityId再去获取天气数据
         */
//        ApiClient.weatherService.getCityIdByName("上海")
//                .flatMap(new Func1<String, Observable<Weather>>() {
//                    @Override
//                    public Observable<Weather> call(String cityId) {
//                        return ApiClient.weatherService.getWeather(cityId);
//                    }
//                }).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Action1<Weather>() {
//                    @Override
//                    public void call(Weather weather) {
//                        weatherView.displayWeatherInformation(weather);
//                    }
//                });

    }

}
