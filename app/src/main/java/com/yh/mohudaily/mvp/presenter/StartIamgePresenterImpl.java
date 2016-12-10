package com.yh.mohudaily.mvp.presenter;

import com.yh.mohudaily.mvp.contract.StartIamgeContract;
import com.yh.mohudaily.mvp.model.StartImageHelper;
import com.yh.mohudaily.util.LogUtil;

/**
 * Created by YH on 2016/11/9.
 * 启动图片实现类
 * 从model获取数据
 * 获取view对象
 */
public class StartIamgePresenterImpl implements StartIamgeContract.Presenter, StartImageHelper.StartImageLoadListener {
    private StartImageHelper helper;

    private StartIamgeContract.StartView view;

    public StartIamgePresenterImpl(StartIamgeContract.StartView view) {
        this.view = view;
        this.view.setPresenter(this);
        helper = new StartImageHelper();
        helper.setStartImageLoadListener(this);
    }

    @Override
    public void loadStartIamge() {
        helper.getStartImage();
    }

    @Override
    public void imageLoadFailure(Throwable e) {
        view.imageLoadFailure(e);
    }

    @Override
    public void imageLoadSuccess(String imageUrl) {
        view.imageLoaded(imageUrl);
    }
}
