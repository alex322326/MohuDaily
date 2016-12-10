package com.yh.mohudaily.mvp.contract;

import com.yh.mohudaily.entity.StoryBean;
import com.yh.mohudaily.entity.TopStoryBean;
import com.yh.mohudaily.mvp.base.BasePresenter;
import com.yh.mohudaily.view.BaseView;

import java.util.ArrayList;

/**
 * Created by YH on 2016/11/9.
 */

public interface NewsContract {
    interface Presenter {
        void loadNews(String date);
    }

    interface View  {
        void loadFailure();
        void loadNewsSuccess(ArrayList<StoryBean> stories, boolean isLoadMore,String date);
        void loadTopNewsSuccess(ArrayList<TopStoryBean> top_stories);
    }
}
