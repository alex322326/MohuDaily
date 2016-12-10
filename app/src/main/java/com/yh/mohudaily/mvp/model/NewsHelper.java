package com.yh.mohudaily.mvp.model;

import com.google.gson.Gson;
import com.yh.mohudaily.MohuDaily;
import com.yh.mohudaily.entity.BeforeNewsInfo;
import com.yh.mohudaily.entity.MohuNewsBean;
import com.yh.mohudaily.entity.StoryBean;
import com.yh.mohudaily.entity.TopStoryBean;
import com.yh.mohudaily.network.RetrofitHelper;
import com.yh.mohudaily.util.DbUtil;
import com.yh.mohudaily.util.HttpUtil;
import com.yh.mohudaily.util.LogUtil;


import java.util.ArrayList;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by YH on 2016/11/9.
 */

public class NewsHelper {
    private MohuNewsLoadListener listener;

    public void getMohuNews(String date){
        if(HttpUtil.isNetworkConnected(MohuDaily.getInstance())){
            //从网络获取数据
            if(date.equals("")){
                RetrofitHelper.getLatestApi().getLatestNews()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<MohuNewsBean>() {
                            @Override
                            public void onCompleted() {

                            }
                            @Override
                            public void onError(Throwable e) {
                                listener.failure(e);
                                LogUtil.LogE(e.toString());
                            }
                            @Override
                            public void onNext(MohuNewsBean mohuNewsBean) {
                                try {
                                    String json = new Gson().toJson(mohuNewsBean);
                                    DbUtil dbUtil = MohuDaily.getDbUtil();
                                    dbUtil.saveNewsCache(mohuNewsBean.getDate(),json);
                                }catch (Exception e){
                                    LogUtil.LogE(e.toString());
                                }
                                listener.newsLoadSuccess(mohuNewsBean.getStories(),false,mohuNewsBean.getDate());
                                listener.topNewsLoadSuccess(mohuNewsBean.getTop_stories());
                            }
                        });
            }else {
                RetrofitHelper. getBeforeNewsApi().getBeforeNewsApi(date)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<BeforeNewsInfo>() {
                            @Override
                            public void onCompleted() {
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(BeforeNewsInfo beforeNewsInfo) {
                                listener.newsLoadSuccess(beforeNewsInfo.getStories(),true,beforeNewsInfo.getDate());

                            }
                        });
            }

        }else {
         try{
             //从数据库获取
             DbUtil dbUtil = MohuDaily.getDbUtil();
             String info_str = dbUtil.getNewsCache();
             if(info_str!=null){
                 MohuNewsBean mohuNewsBean = new Gson().fromJson(info_str, MohuNewsBean.class);
                 listener.newsLoadSuccess(mohuNewsBean.getStories(),false,mohuNewsBean.getDate());
             }else {
                 listener.failure(new Throwable("数据不存在"));
             }
         }catch (Exception e){
             LogUtil.LogE(e.toString());
         }
        }

    }

    public interface MohuNewsLoadListener{
        void failure(Throwable e);
        void newsLoadSuccess(ArrayList<StoryBean> stories, boolean isLoadMore,String date);
        void topNewsLoadSuccess(ArrayList<TopStoryBean> top_stories);
    }

    public void setNewsLoadListener(MohuNewsLoadListener listener){
        this.listener = listener;
    }
}
