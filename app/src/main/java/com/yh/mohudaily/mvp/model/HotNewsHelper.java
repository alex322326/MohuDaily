package com.yh.mohudaily.mvp.model;

import com.yh.mohudaily.entity.HotNewsInfo;
import com.yh.mohudaily.network.RetrofitHelper;
import com.yh.mohudaily.util.LogUtil;

import java.util.ArrayList;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by YH on 2016/11/10.
 * Hot 数据加载
 * 接口回调
 */

public class HotNewsHelper {
    public interface OnHotNewsLoadListener{
        void hotNewsLoadFailure();
        void hotNewsLoadSuccess(ArrayList<HotNewsInfo.Hot> hots);
    }
    private OnHotNewsLoadListener listener;
    public void setOnHotNewsLoadListener(OnHotNewsLoadListener listener) {
        this.listener = listener;
    }

    /**
     * model的数据加载方法
     */

    public void getHotNews(){
        RetrofitHelper.getHotNewsApi().getHotNewsInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HotNewsInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.hotNewsLoadFailure();
                    }

                    @Override
                    public void onNext(HotNewsInfo hotNewsInfo) {
                        LogUtil.LogE("size:"+hotNewsInfo.getRecent().size());
                        listener.hotNewsLoadSuccess(hotNewsInfo.getRecent());
                    }
                });

    }
}
