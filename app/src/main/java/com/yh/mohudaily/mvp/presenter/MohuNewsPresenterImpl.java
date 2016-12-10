package com.yh.mohudaily.mvp.presenter;

import com.yh.mohudaily.entity.StoryBean;
import com.yh.mohudaily.entity.TopStoryBean;
import com.yh.mohudaily.mvp.model.NewsHelper;
import com.yh.mohudaily.mvp.contract.NewsContract;

import java.util.ArrayList;

/**
 * Created by YH on 2016/11/9.
 */

public class MohuNewsPresenterImpl implements NewsContract.Presenter, NewsHelper.MohuNewsLoadListener {
    private NewsContract.View mView;
    private NewsHelper helper;
    public MohuNewsPresenterImpl(NewsContract.View view){
        mView = view;
        helper = new NewsHelper();
        helper.setNewsLoadListener(this);
    }

    /**
     * 请求网络数据
     * @param date
     */
    @Override
    public void loadNews(String date) {
        helper.getMohuNews(date);
    }

    @Override
    public void failure(Throwable e) {
        mView.loadFailure();
    }

    @Override
    public void newsLoadSuccess(ArrayList<StoryBean> stories, boolean isLoadMore,String date) {
        mView.loadNewsSuccess(stories,isLoadMore,date);
    }

    @Override
    public void topNewsLoadSuccess(ArrayList<TopStoryBean> top_stories) {
        mView.loadTopNewsSuccess(top_stories);
    }
}
