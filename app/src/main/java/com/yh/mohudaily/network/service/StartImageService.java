package com.yh.mohudaily.network.service;

import com.yh.mohudaily.entity.MohuNewsBean;
import com.yh.mohudaily.entity.MohuStartImage;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by YH on 2016/11/9.
 */
public interface StartImageService {
    @GET("start-image/1080*1776")
    Observable<MohuStartImage> getStartImage();
}
