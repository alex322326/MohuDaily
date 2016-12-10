package com.yh.mohudaily.mvp.presenter;

import com.yh.mohudaily.mvp.contract.CommentsContract;
import com.yh.mohudaily.entity.CommentInfo;
import com.yh.mohudaily.mvp.model.NewsCommentsHelper;

import java.util.ArrayList;

/**
 * Created by YH on 2016/11/15.
 * 长评论 P m获取数据 v数据回调
 */

public class ShortCommentsPresenterImpl implements CommentsContract.Presenter, NewsCommentsHelper.OnNewsCommentLoadListener {
    private CommentsContract.View mView;
    private NewsCommentsHelper helper;
    public ShortCommentsPresenterImpl(CommentsContract.View view) {
        mView = view;
        helper = new NewsCommentsHelper();
        helper.setOnNewsCommentsLoadLisenter(this);
    }

    @Override
    public void loadLongComments(String id) {

    }

    @Override
    public void loadShortComments(String id) {
        helper.getShortComments(id);
    }

    @Override
    public void loadLongCommentsFailure(Throwable e) {

    }

    @Override
    public void loadShortCommentsFailure(Throwable e) {

    }

    @Override
    public void loadLongCommentsSuccess(ArrayList<CommentInfo.Comment> longComments) {
        mView.loadShortCommentsSuccess(longComments);
    }

    @Override
    public void loadShortCommentsSuccess(ArrayList<CommentInfo.Comment> shortComments) {
        mView.loadShortCommentsSuccess(shortComments);
    }
}
