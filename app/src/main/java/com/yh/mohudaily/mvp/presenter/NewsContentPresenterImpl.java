package com.yh.mohudaily.mvp.presenter;

import com.yh.mohudaily.mvp.contract.NewsContentContract;
import com.yh.mohudaily.entity.NewsContentBean;
import com.yh.mohudaily.entity.NewsExtra;
import com.yh.mohudaily.mvp.model.NewsContentHelper;

/**
 * Created by YH on 2016/11/11.
 */
public class NewsContentPresenterImpl implements NewsContentContract.Presenter, NewsContentHelper.OnNewsContentLoadListener, NewsContentHelper.OnNewsExtrasLoadListener {
    private NewsContentContract.View mView;
    private NewsContentHelper helper;
    public NewsContentPresenterImpl(NewsContentContract.View view) {
        mView = view;
        helper = new NewsContentHelper();
        helper.setOnNewsContentLoadListener(this);
        helper.setOnNewsExtraLoadListener(this);
    }

    @Override
    public void getNewsContent(String id) {
        helper.getNewsContent(id);
    }

    @Override
    public void getNewsExtras(String id) {
        helper.getNewsExtras(id);
    }

    @Override
    public void loadNewsContentFailure() {
        mView.getNewsContentFailure();
    }

    @Override
    public void loadNewsContentSuccess(NewsContentBean content) {
        mView.getNewsContentSuccess(content);
    }

    @Override
    public void loadNewsExtraFailure() {
        mView.getNewsExtrasFailure();
    }

    @Override
    public void loadNewsExtraSuccess(NewsExtra extra) {
        mView.getNewsExtrasSuccess(extra);
    }
}
