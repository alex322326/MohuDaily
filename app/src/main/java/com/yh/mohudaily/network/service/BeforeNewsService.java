package com.yh.mohudaily.network.service;

import com.yh.mohudaily.entity.BeforeNewsInfo;


import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by YH on 2016/11/10.
 */
public interface BeforeNewsService {
    @GET("news/before/{id}")
    Observable<BeforeNewsInfo> getBeforeNewsApi(@Path("id") String id);
}
