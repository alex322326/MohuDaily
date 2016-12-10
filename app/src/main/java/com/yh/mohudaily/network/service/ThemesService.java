package com.yh.mohudaily.network.service;

import com.yh.mohudaily.entity.ThemesBean;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by YH on 2016/11/9.
 */
public interface ThemesService {
    @GET("themes")
    Observable<ThemesBean> getSections();
}
