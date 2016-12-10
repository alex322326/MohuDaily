package com.yh.mohudaily.mvp.contract;

import com.yh.mohudaily.entity.NewsContentBean;
import com.yh.mohudaily.entity.NewsExtra;

/**
 * Created by YH on 2016/11/11.
 */

public interface NewsContentContract {
    interface Presenter{
        void getNewsContent(String id);
        void getNewsExtras(String id);
    }


    interface View{
        void getNewsContentFailure();
        void getNewsContentSuccess(NewsContentBean content);
        void getNewsExtrasFailure();
        void getNewsExtrasSuccess(NewsExtra extra);
    }

}
