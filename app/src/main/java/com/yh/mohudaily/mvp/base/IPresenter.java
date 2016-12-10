package com.yh.mohudaily.mvp.base;

/**
 * Created by YH on 2016/12/8.
 */

public interface IPresenter <V extends IView>  {
    /**
     * @param view 绑定View
     */
    void attachView(V view);

    /**
     * 防止内存的泄漏,清楚presenter与activity之间的绑定
     */
    void detachView();

    /**
     *
     * @return 获取View
     */
    IView getIView();

}
