package com.yh.mohudaily.mvp.contract;

import com.yh.mohudaily.entity.HotNewsInfo;
import com.yh.mohudaily.mvp.base.BasePresenter;

import java.util.ArrayList;

/**
 * Created by YH on 2016/11/10.
 */

public interface HotNewsContract {
    interface Presenter {
        void loadHotNews();
    }

    interface View{
        void newsLoadFailure();
        void newLoadSuccess(ArrayList<HotNewsInfo.Hot> hots);
    }
}
