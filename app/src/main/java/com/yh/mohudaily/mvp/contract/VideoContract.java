package com.yh.mohudaily.mvp.contract;

import com.yh.mohudaily.entity.VideoBean;

import java.util.ArrayList;

/**
 * Created by YH on 2016/12/6.
 */

public interface VideoContract {

    interface View{
        void loadVideoSuccess(ArrayList<VideoBean> videos,int currentpage);
        void loadVideoFailure(Throwable e);
    }

    interface Presenter{
        void getVideos(int page);

    }
}
