package com.yh.mohudaily.network.service;

import com.yh.mohudaily.entity.CommentInfo;

import java.util.ArrayList;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by YH on 2016/11/15.
 */
public interface NewsShortCommentService {
    @GET("story/{id}/short-comments")
    Observable<CommentInfo> getNewsCommentsApi(@Path("id") String id);
}
