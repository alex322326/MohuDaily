package com.yh.mohudaily.mvp.model;

import com.yh.mohudaily.entity.MohuStartImage;
import com.yh.mohudaily.network.RetrofitHelper;
import com.yh.mohudaily.util.LogUtil;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by YH on 2016/11/9.
 * 启动图片数据处理model
 * 请求图片数据
 * 提供数据加载的接口回调
 */
public class StartImageHelper {

    public void getStartImage() {
        LogUtil.LogE("getStartImage");
        //数据请求操作
        RetrofitHelper.getStartImageApi().getStartImage()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MohuStartImage>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.LogE("error:"+e.toString());
                        listener.imageLoadFailure(e);
                    }
                    @Override
                    public void onNext(MohuStartImage mohuStartImage) {
                        LogUtil.LogE("load success:"+mohuStartImage.toString());
                        listener.imageLoadSuccess(mohuStartImage.getImg());
                    }
                });
    }

    public interface StartImageLoadListener {
        void imageLoadFailure(Throwable e);

        void imageLoadSuccess(String imageUrl);
    }

    private StartImageLoadListener listener;

    public void setStartImageLoadListener(StartImageLoadListener listener) {
        this.listener = listener;
    }
}
