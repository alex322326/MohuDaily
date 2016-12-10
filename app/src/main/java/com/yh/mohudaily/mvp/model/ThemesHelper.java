package com.yh.mohudaily.mvp.model;

import com.yh.mohudaily.entity.ThemesBean;
import com.yh.mohudaily.network.RetrofitHelper;

import java.util.ArrayList;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by YH on 2016/11/9.
 * sections 数据model
 * 请求数据
 * 提供接口回调
 */
public class ThemesHelper {

    public void getSections(){
        //loading
        RetrofitHelper.getThemesApi().getSections()

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ThemesBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.loadFailure(e);
                    }

                    @Override
                    public void onNext(ThemesBean themesBean) {
                        listener.loadSections(themesBean.getOthers());
                    }
                });
    }

    public interface SectionLoadListener{
        void loadFailure(Throwable e);
        void loadSections(ArrayList<ThemesBean.Theme> sections);
    }

    private SectionLoadListener listener;
    public void setSectionLoadListener(SectionLoadListener listener){
        this.listener = listener;
    }
}
