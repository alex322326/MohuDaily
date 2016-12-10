package com.yh.mohudaily.mvp.presenter;

import com.yh.mohudaily.mvp.contract.ThemesContract;
import com.yh.mohudaily.entity.ThemesBean;
import com.yh.mohudaily.mvp.model.ThemesHelper;

import java.util.ArrayList;

/**
 * Created by YH on 2016/11/9.
 */
public class ThemesPresenterImpl implements ThemesContract.Presenter, ThemesHelper.SectionLoadListener {
    private ThemesContract.View view;

    private ThemesHelper helper;

    public ThemesPresenterImpl(ThemesContract.View view) {
        this.view = view;
        helper = new ThemesHelper();
        helper.setSectionLoadListener(this);
    }

    @Override
    public void loadSections() {
        helper.getSections();
    }

    @Override
    public void loadFailure(Throwable e) {

    }

    @Override
    public void loadSections(ArrayList<ThemesBean.Theme> sections) {
        view.sectionsLoaded(sections);
    }
}
