package com.example.myapplication.api.base;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by xieH on 2016/12/2 0002.
 */
public class ApiThrowExcepitionFun1<t> implements Func1<ResponseInfo<t>, Observable<t>> {

    @Override
    public Observable<t> call(ResponseInfo<t> responseInfo) {
        if (responseInfo.getCode() != 200) {  //如果code返回的不是200,则抛出ApiException异常，否则返回data数据
            return Observable.error(new ApiException(responseInfo.getCode(), responseInfo.getMessage()));
        }
        return Observable.just(responseInfo.getData());
    }

}
