package com.yh.mohudaily.util;

import android.util.Log;

/**
 * Created by HiAll_yan on 2016/5/17.
 */
public class LogUtil {
    public static final boolean TOGGLE = true;
    public static String TAG = "hiall";
    public static void LogE(String content){
        if(TOGGLE){
            Log.e(TAG,content);
        }
    }

}
