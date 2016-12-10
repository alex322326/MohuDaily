package com.yh.mohudaily.mvp.presenter;

import com.yh.mohudaily.mvp.base.BasePresenter;
import com.yh.mohudaily.mvp.contract.BeautyImageContract;
import com.yh.mohudaily.entity.BeautyImageBean;
import com.yh.mohudaily.mvp.model.BeautyImageHelper;

import java.util.ArrayList;

/**
 * Created by YH on 2016/12/2.
 */

public class BeautyImagePresenterImpl implements BeautyImageContract.Presenter, BeautyImageHelper.OnLoadBeautyImageListener {

    private BeautyImageContract.View view;
    private BeautyImageHelper helper;
    public BeautyImagePresenterImpl(BeautyImageContract.View view) {
        this.view = view;
        helper =new BeautyImageHelper();
        helper.setOnLoadBeautyImageListener(this);
    }

    @Override
    public void getBeautyImages(int currentPage) {
        helper.getBeautyImages(currentPage);
    }

    @Override
    public void loadSuccess(ArrayList<BeautyImageBean> images) {
        view.loadBeautyImageSuccess(images);
    }

    @Override
    public void loadFailure(Throwable e) {
        view.loadBeautyImageFailure(e);
    }
}
