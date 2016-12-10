package com.yh.mohudaily.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by YH on 2016/12/8.
 */

public class HttpUtil {
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            try {
                ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                        .getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
                if (mNetworkInfo != null) {
                    return mNetworkInfo.isAvailable();
                }
            }catch (Exception e){
                LogUtil.LogE(e.toString());
            }

        }
        return false;
    }
}
