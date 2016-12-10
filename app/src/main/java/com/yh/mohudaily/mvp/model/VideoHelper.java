package com.yh.mohudaily.mvp.model;

import com.yh.mohudaily.entity.VideoBean;
import com.yh.mohudaily.entity.VideoInfo;
import com.yh.mohudaily.network.RetrofitHelper;

import java.util.ArrayList;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by YH on 2016/12/6.
 */
public class VideoHelper {
    public void getVideoInfo(final int page){
        RetrofitHelper.getVideoApi().getVideoInfo(RetrofitHelper.SHOWAPI_APPID,RetrofitHelper.SHOWAPI_SECRET,page,RetrofitHelper.SHOWAPI_TYPE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<VideoInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onLoadFailure(e);
                    }

                    @Override
                    public void onNext(VideoInfo videoInfo) {
                        VideoInfo.VideoResponse pagebean = videoInfo.getShowapi_res_body().getPagebean();
                        listener.onLoadSuccess(pagebean.getContentlist(),pagebean.getCurrentPage());
                    }
                });
    }

    public interface OnVideoInfoLoadListener{
        void onLoadSuccess(ArrayList<VideoBean> videos,int currentPage);
        void onLoadFailure(Throwable e);
    }

    private OnVideoInfoLoadListener listener;
    public void setOnVideoInfoLoadListener(OnVideoInfoLoadListener listener){
        this.listener = listener;
    }
}
