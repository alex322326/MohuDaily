package com.yh.mohudaily.network.service;

import com.yh.mohudaily.entity.BeautyImageInfo;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by YH on 2016/12/2.
 */

public interface BeautyImageService {
    @GET("1177-1")
    Observable<BeautyImageInfo> getBeautyImageInfo(@Query("showapi_appid")String app_id, @Query("showapi_sign")String sign, @Query("page") int page);

}
