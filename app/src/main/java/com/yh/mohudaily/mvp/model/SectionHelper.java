package com.yh.mohudaily.mvp.model;

import com.yh.mohudaily.entity.SectionsInfo;
import com.yh.mohudaily.network.RetrofitHelper;

import java.util.ArrayList;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by YH on 2016/11/10.
 * 专栏 数据model
 * 数据加载实现
 * 提供接口回调
 */
public class SectionHelper {

    public void getSections(){
        //load data
        RetrofitHelper.getSectionsApi().getSections()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SectionsInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.loadFailure(e);
                    }

                    @Override
                    public void onNext(SectionsInfo sectionsInfo) {
                        listener.loadSuccess(sectionsInfo.getData());
                    }
                });
    }

    public interface OnSectionsLoadListener{
        void loadFailure(Throwable e);
        void loadSuccess(ArrayList<SectionsInfo.Section>  sections);
    }
    private OnSectionsLoadListener listener;
    public void setOnSectionsLoadListener(OnSectionsLoadListener listener){
        this.listener = listener;
    }
}
