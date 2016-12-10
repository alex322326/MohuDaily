package com.yh.mohudaily.network.service;

import com.yh.mohudaily.entity.CommentInfo;


import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by YH on 2016/11/15.
 */
public interface NewsLongCommentService {
    @GET("story/{id}/long-comments")
    Observable<CommentInfo> getNewsCommentsApi(@Path("id")String id);
}
