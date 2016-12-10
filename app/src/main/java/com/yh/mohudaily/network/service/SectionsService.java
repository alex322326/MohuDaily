package com.yh.mohudaily.network.service;

import com.yh.mohudaily.entity.SectionsInfo;
import com.yh.mohudaily.entity.ThemesBean;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by YH on 2016/11/10.
 */

public interface SectionsService {
    @GET("sections")
    Observable<SectionsInfo> getSections();
}
