package com.yh.mohudaily.network.service;

import com.yh.mohudaily.entity.MohuNewsBean;
import com.yh.mohudaily.entity.ThemeBean;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by YH on 2016/11/9.
 */
public interface ThemeContentService {
    @GET("theme/{id}")
    Observable<ThemeBean> getThemeContentApi(@Path("id") String id);
}
