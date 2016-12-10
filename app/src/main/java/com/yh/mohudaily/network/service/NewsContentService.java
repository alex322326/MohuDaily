package com.yh.mohudaily.network.service;

import com.yh.mohudaily.entity.NewsContentBean;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by YH on 2016/11/11.
 */
public interface NewsContentService {
    @GET("news/{id}")
    Observable<NewsContentBean> getNewsContent(@Path("id")String id);
}
