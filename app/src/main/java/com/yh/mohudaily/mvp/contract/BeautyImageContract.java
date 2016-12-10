package com.yh.mohudaily.mvp.contract;

import com.yh.mohudaily.entity.BeautyImageBean;

import java.util.ArrayList;

/**
 * Created by YH on 2016/12/2.
 */

public interface BeautyImageContract {
    interface View{
        void loadBeautyImageSuccess(ArrayList<BeautyImageBean> images);
        void loadBeautyImageFailure(Throwable e);
    }

    interface Presenter{
        void getBeautyImages(int currentPage);
    }
}
