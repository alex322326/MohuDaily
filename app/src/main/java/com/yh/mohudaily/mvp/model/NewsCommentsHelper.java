package com.yh.mohudaily.mvp.model;

import com.yh.mohudaily.entity.CommentInfo;
import com.yh.mohudaily.network.RetrofitHelper;

import java.util.ArrayList;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by YH on 2016/11/15.
 * 评论Model 请求评论数据
 * 定义数据毁掉接口
 */

public class NewsCommentsHelper {

    /**
     * 请求长评论
     */
    public void getLongComments(String id){
        RetrofitHelper.getNewsLongCommentsApi().getNewsCommentsApi(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CommentInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.loadLongCommentsFailure(e);
                    }

                    @Override
                    public void onNext(CommentInfo comments) {
                        listener.loadLongCommentsSuccess(comments.getComments());
                    }
                });
    }
    /**
     * 请求短评论
     */
    public void getShortComments(String id){
        RetrofitHelper.getNewsShortCommentsApi().getNewsCommentsApi(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CommentInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.loadShortCommentsFailure(e);
                    }

                    @Override
                    public void onNext(CommentInfo comments) {
                        listener.loadShortCommentsSuccess(comments.getComments());
                    }
                });
    }

    private OnNewsCommentLoadListener listener;
    public interface OnNewsCommentLoadListener{
        void loadLongCommentsFailure(Throwable e);
        void loadShortCommentsFailure(Throwable e);
        void loadLongCommentsSuccess(ArrayList<CommentInfo.Comment> longComments);
        void loadShortCommentsSuccess(ArrayList<CommentInfo.Comment> shortComments);
    }

    public void setOnNewsCommentsLoadLisenter(OnNewsCommentLoadListener lisenter){
        this.listener = lisenter;
    }

}
