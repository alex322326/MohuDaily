package com.yh.mohudaily.network.service;

import com.yh.mohudaily.entity.BeautyImageInfo;
import com.yh.mohudaily.entity.VideoInfo;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by YH on 2016/12/2.
 */

public interface VideoService {
    @GET("255-1")
    Observable<VideoInfo> getVideoInfo(@Query("showapi_appid") String app_id, @Query("showapi_sign") String sign, @Query("page") int page, @Query("type")int type);

}
