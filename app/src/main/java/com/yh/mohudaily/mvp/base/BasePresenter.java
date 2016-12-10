package com.yh.mohudaily.mvp.base;

import java.lang.ref.WeakReference;
import java.util.HashMap;

/**
 * Created by YH on 2016/11/8.
 */

public abstract class BasePresenter<V extends IView> implements IPresenter{
    private WeakReference viewRef;

    protected V iView;

    public abstract HashMap<String, IModel> getiModelMap();

    @Override
    public void attachView(IView iView) {
        viewRef = new WeakReference(iView);
    }

    @Override
    public void detachView() {
        if (viewRef != null) {
            viewRef.clear();
            viewRef = null;
        }
    }

    @Override
    public V getIView() {
        return (V) viewRef.get();
    }

    /**
     * @param models
     * @return
     * 添加多个model,如有需要
     */
    public abstract HashMap<String, IModel> loadModelMap(IModel... models);
}
