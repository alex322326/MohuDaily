package com.yh.mohudaily.network.service;

import com.yh.mohudaily.entity.HotNewsInfo;
import com.yh.mohudaily.entity.MohuNewsBean;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by YH on 2016/11/10.
 */
public interface HotNewsService {
    @GET("news/hot")
    Observable<HotNewsInfo> getHotNewsInfo();

}
