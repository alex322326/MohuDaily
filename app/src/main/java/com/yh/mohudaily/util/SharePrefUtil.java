package com.yh.mohudaily.util;

import android.content.Context;
import android.content.SharedPreferences;
import com.yh.mohudaily.MohuDaily;

/**
 * Created by HiAll_yan on 2016/5/6.
 */
public class SharePrefUtil {
    private static final String SHARE_PREFS_NAME = "YH";
    private static final Context ctx= MohuDaily.getInstance();
    public static void putBoolean(String key, boolean value) {
        SharedPreferences pref = ctx.getSharedPreferences(SHARE_PREFS_NAME,
                Context.MODE_PRIVATE);
        pref.edit().putBoolean(key, value).commit();
    }

    public static boolean getBoolean(String key,
                                     boolean defaultValue) {
        SharedPreferences pref = ctx.getSharedPreferences(SHARE_PREFS_NAME,
                Context.MODE_PRIVATE);

        return pref.getBoolean(key, defaultValue);
    }

    public static void putString(String key, String value) {
        SharedPreferences pref = ctx.getSharedPreferences(SHARE_PREFS_NAME,
                Context.MODE_PRIVATE);

        pref.edit().putString(key, value).commit();
    }

    public static String getString(String key, String defaultValue) {
        SharedPreferences pref = ctx.getSharedPreferences(SHARE_PREFS_NAME,
                Context.MODE_PRIVATE);

        return pref.getString(key, defaultValue);
    }

    public static void putInt(String key, int value) {
        SharedPreferences pref = ctx.getSharedPreferences(SHARE_PREFS_NAME,
                Context.MODE_PRIVATE);

        pref.edit().putInt(key, value).commit();
    }

    public static int getInt(String key, int defaultValue) {
        SharedPreferences pref = ctx.getSharedPreferences(SHARE_PREFS_NAME,
                Context.MODE_PRIVATE);

        return pref.getInt(key, defaultValue);
    }


}
