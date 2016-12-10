package com.yh.mohudaily.mvp.contract;

import com.yh.mohudaily.entity.ThemesBean;
import com.yh.mohudaily.mvp.base.BasePresenter;

import java.util.ArrayList;

/**
 * Created by YH on 2016/11/9.
 * 栏目总览 P V 管理类
 * p v 接口
 */

public interface ThemesContract {
    interface Presenter {
         void loadSections();
    }
    interface View {
        void sectionsLoading();
        void sectionsLoaded(ArrayList<ThemesBean.Theme> sections);
    }
}
