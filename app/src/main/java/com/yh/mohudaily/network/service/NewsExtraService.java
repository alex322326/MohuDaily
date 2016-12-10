package com.yh.mohudaily.network.service;

import com.yh.mohudaily.entity.NewsExtra;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by YH on 2016/11/15.
 */
public interface NewsExtraService {
    @GET("story-extra/{id}")
    Observable<NewsExtra> getNewsExtras(@Path("id")String id);
}
