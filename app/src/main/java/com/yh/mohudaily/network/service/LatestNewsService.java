package com.yh.mohudaily.network.service;

import com.yh.mohudaily.entity.MohuNewsBean;


import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by YH on 2016/11/8.
 * 主页面新闻数据模块
 * URL: http://news-at.zhihu.com/api/4/news/latest
 *
 */
public interface LatestNewsService {
    @GET("news/latest")
    Observable<MohuNewsBean> getLatestNews();
}
