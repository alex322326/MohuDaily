package com.yh.mohudaily.mvp.presenter;

import com.yh.mohudaily.entity.VideoBean;
import com.yh.mohudaily.mvp.contract.VideoContract;
import com.yh.mohudaily.mvp.model.VideoHelper;

import java.util.ArrayList;

/**
 * Created by YH on 2016/12/6.
 */

public class VideoPresenterImpl implements VideoContract.Presenter, VideoHelper.OnVideoInfoLoadListener {
    private VideoContract.View view;
    private VideoHelper helper;

    public VideoPresenterImpl(VideoContract.View view) {
        this.view = view;

        helper = new VideoHelper();

        helper.setOnVideoInfoLoadListener(this);
    }

    @Override
    public void getVideos(int page) {
        helper.getVideoInfo(page);
    }

    @Override
    public void onLoadSuccess(ArrayList<VideoBean> videos, int currentPage) {
        view.loadVideoSuccess(videos,currentPage);
    }

    @Override
    public void onLoadFailure(Throwable e) {
        view.loadVideoFailure(e);
    }
}
