package com.yh.mohudaily.mvp.contract;

import com.yh.mohudaily.mvp.base.BasePresenter;
import com.yh.mohudaily.view.BaseView;

/**
 * Created by YH on 2016/11/9.
 *  启动图片 P V管理
 *  presenter接口
 *  view接口
 * */

 public interface StartIamgeContract {
    interface Presenter {
        void loadStartIamge();
    }

    interface StartView extends BaseView<Presenter>{
        void imageLoadFailure(Throwable e);
        void iamgeLoading();
        void imageLoaded(String imgUrl);
    }

}
