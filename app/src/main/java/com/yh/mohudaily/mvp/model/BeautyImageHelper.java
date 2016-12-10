package com.yh.mohudaily.mvp.model;

import com.yh.mohudaily.entity.BeautyImageBean;
import com.yh.mohudaily.entity.BeautyImageInfo;
import com.yh.mohudaily.mvp.base.IModel;
import com.yh.mohudaily.network.RetrofitHelper;

import java.util.ArrayList;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by YH on 2016/12/2.
 * BeautyImage 数据加载
 * 接口回调
 */

public class BeautyImageHelper implements IModel{

    public void getBeautyImages(int currentPage){
        RetrofitHelper.getBeautyImageApi().getBeautyImageInfo(RetrofitHelper.SHOWAPI_APPID,RetrofitHelper.SHOWAPI_SECRET,currentPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BeautyImageInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.loadFailure(e);
                    }

                    @Override
                    public void onNext(BeautyImageInfo beautyImageInfo) {
                        BeautyImageInfo.ResponseBody body = beautyImageInfo.getShowapi_res_body();
                        listener.loadSuccess(body.getPagebean().getContentlist());
                    }
                });
    }

    public interface OnLoadBeautyImageListener{
        void loadSuccess(ArrayList<BeautyImageBean> images);
        void loadFailure(Throwable e);
    }
    private OnLoadBeautyImageListener listener;
    public void setOnLoadBeautyImageListener(OnLoadBeautyImageListener listener){
        this.listener = listener;
    }
}
