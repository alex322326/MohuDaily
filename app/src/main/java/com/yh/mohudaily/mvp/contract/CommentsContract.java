package com.yh.mohudaily.mvp.contract;

import com.yh.mohudaily.entity.CommentInfo;

import java.util.ArrayList;

/**
 * Created by YH on 2016/11/15.
 */

public interface CommentsContract {
    interface View{
        void loadLongCommentsSuccess(ArrayList<CommentInfo.Comment> longComments);
        void loadShortCommentsSuccess(ArrayList<CommentInfo.Comment> longComments);
        void loadLongCommnetsFailure(Throwable e);
        void loadShoryCommnetsFailure(Throwable e);
    }

    interface Presenter{
        void loadLongComments(String id);
        void loadShortComments(String id);
    }

}
