package com.yh.mohudaily.mvp.contract;

/*
 * Created by YH on 2016/11/10.
 * 专栏 P V 接口
 */

import com.yh.mohudaily.entity.SectionsInfo;
import com.yh.mohudaily.mvp.base.BasePresenter;

import java.util.ArrayList;

public interface SectionContract {

    interface Presenter {
        void loadSectionData();
    }

    interface View{
        void loadFailure();
        void loadSuccess(ArrayList<SectionsInfo.Section> sections);
    }
}
