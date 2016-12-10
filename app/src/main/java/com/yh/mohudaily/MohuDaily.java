package com.yh.mohudaily;

import android.app.Application;

import com.yh.mohudaily.util.DbCacheHelper;
import com.yh.mohudaily.util.DbUtil;
import com.yh.mohudaily.util.SharePrefUtil;

/**
 * Created by YH on 2016/11/8.
 */

public class MohuDaily extends Application {
    private static MohuDaily mInstance;
    private static DbUtil dbUtil;
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance =this;
        dbUtil = DbUtil.getInstance();
    }

    public static MohuDaily getInstance(){
        return mInstance;
    }
    public static DbUtil getDbUtil(){
        return dbUtil;
    }
}
