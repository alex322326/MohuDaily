package com.yh.mohudaily.mvp.model;

import com.google.gson.Gson;
import com.yh.mohudaily.MohuDaily;
import com.yh.mohudaily.entity.NewsContentBean;
import com.yh.mohudaily.entity.NewsExtra;
import com.yh.mohudaily.network.RetrofitHelper;
import com.yh.mohudaily.util.DbUtil;
import com.yh.mohudaily.util.HttpUtil;
import com.yh.mohudaily.util.LogUtil;
import com.yh.mohudaily.util.ToastUtil;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by YH on 2016/11/11.
 * 日报内容model
 * 获取数据 提供接口
 *
 */
public class NewsContentHelper {
    private OnNewsContentLoadListener listener;
    private OnNewsExtrasLoadListener listener2;
    public void getNewsContent(final String id){
        if(HttpUtil.isNetworkConnected(MohuDaily.getInstance())){
            RetrofitHelper.getNewsContentApi().getNewsContent(id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<NewsContentBean>() {
                        @Override
                        public void onCompleted() {

                        }
                        @Override
                        public void onError(Throwable e) {
                            LogUtil.LogE("failure"+e.toString());
                            listener.loadNewsContentFailure();
                        }
                        @Override
                        public void onNext(NewsContentBean newsContentBean) {
                            String json = new Gson().toJson(newsContentBean);
                            DbUtil dbUtil = MohuDaily.getDbUtil();
                            dbUtil.saveNewsContentCache(id,json);
                            listener.loadNewsContentSuccess(newsContentBean);
                        }
                    });
        }else {
            DbUtil dbUtil = MohuDaily.getDbUtil();
            String info_str = dbUtil.getNewsContentCache(id);
            if(info_str!=null){
                NewsContentBean newsContentBean = new Gson().fromJson(info_str, NewsContentBean.class);
                listener.loadNewsContentSuccess(newsContentBean);
            }else {
               listener.loadNewsContentFailure();
                ToastUtil.showToast("数据不存在！");
            }
        }

    }

    public void getNewsExtras(String id) {
        RetrofitHelper.getNewsExtrasApi().getNewsExtras(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NewsExtra>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.LogE("load extra failure:"+e.toString());
                        listener2.loadNewsExtraFailure();
                    }

                    @Override
                    public void onNext(NewsExtra newsExtra) {
                        listener2.loadNewsExtraSuccess(newsExtra);
                    }
                });
    }

    public interface OnNewsContentLoadListener{
        void loadNewsContentFailure();
        void loadNewsContentSuccess(NewsContentBean content);
    }
    public interface OnNewsExtrasLoadListener{
        void loadNewsExtraFailure();
        void loadNewsExtraSuccess(NewsExtra extra);
    }

    public void setOnNewsContentLoadListener(OnNewsContentLoadListener listener) {
        this.listener = listener;
    }

    public void setOnNewsExtraLoadListener(OnNewsExtrasLoadListener listener) {
        listener2 = listener;
    }
}
