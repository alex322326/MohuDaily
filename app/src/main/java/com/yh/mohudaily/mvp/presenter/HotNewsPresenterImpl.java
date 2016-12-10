package com.yh.mohudaily.mvp.presenter;

import com.yh.mohudaily.entity.HotNewsInfo;
import com.yh.mohudaily.mvp.contract.HotNewsContract;
import com.yh.mohudaily.mvp.model.HotNewsHelper;

import java.util.ArrayList;

/**
 * Created by YH on 2016/11/10.
 * 处理 热点的model和view
 * 持有hotfragment对象
 *
 */
public class HotNewsPresenterImpl implements HotNewsContract.Presenter,HotNewsHelper.OnHotNewsLoadListener {
    private HotNewsContract.View mView;
    private HotNewsHelper helper;

    public HotNewsPresenterImpl(HotNewsContract.View view) {
        mView = view;
        helper = new HotNewsHelper();
        helper.setOnHotNewsLoadListener(this);
    }

    /**
     * 加载HotNews数据
     */
    @Override
    public void loadHotNews() {
        helper.getHotNews();
    }

    @Override
    public void hotNewsLoadFailure() {
        mView.newsLoadFailure();
    }

    @Override
    public void hotNewsLoadSuccess(ArrayList<HotNewsInfo.Hot> hots) {
        mView.newLoadSuccess(hots);
    }

}
