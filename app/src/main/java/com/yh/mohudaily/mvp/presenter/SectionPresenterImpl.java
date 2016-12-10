package com.yh.mohudaily.mvp.presenter;

import com.yh.mohudaily.mvp.contract.SectionContract;
import com.yh.mohudaily.entity.SectionsInfo;
import com.yh.mohudaily.mvp.model.SectionHelper;

import java.util.ArrayList;

/**
 * Created by YH on 2016/11/10.
 * 专栏 数据Presenter实现类
 * 控制model数据加载
 * 为view传递数据
 */
public class SectionPresenterImpl implements SectionContract.Presenter,SectionHelper.OnSectionsLoadListener {
    private SectionContract.View mView;
    private SectionHelper helper;
    public SectionPresenterImpl(SectionContract.View view) {
        mView = view;
        helper = new SectionHelper();
        helper.setOnSectionsLoadListener(this);
    }
    @Override
    public void loadSectionData() {
        helper.getSections();
    }
    @Override
    public void loadFailure(Throwable e) {
        mView.loadFailure();
    }

    @Override
    public void loadSuccess(ArrayList<SectionsInfo.Section> sections) {
        mView.loadSuccess(sections);
    }


}
