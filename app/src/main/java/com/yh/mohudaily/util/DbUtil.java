package com.yh.mohudaily.util;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yh.mohudaily.MohuDaily;

/**
 * Created by YH on 2016/12/8.
 */

public class DbUtil {
    public static final int LATEST_COLUMN = Integer.MAX_VALUE;
    public static final int BASE_COLUMN = 100000000;
    private static DbUtil util=null;
    private DbCacheHelper dbHelper;
    private DbUtil(){
        dbHelper = new DbCacheHelper(MohuDaily.getInstance(), 1);
    }
    public static DbUtil getInstance(){
        if(util==null){
            synchronized (DbUtil.class){
                if(util==null){
                    util = new DbUtil();
                }
            }
        }
        return util;
    }
    public void saveNewsCache(String date,String news){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("replace into CacheList(date,json) values(" + LATEST_COLUMN + ",' " + news + "')");
        db.close();
    }

    public String getNewsCache(){
        String json=null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from CacheList where date = " + LATEST_COLUMN, null);
        if (cursor.moveToFirst()) {
             json =  cursor.getString(cursor.getColumnIndex("json"));
        }
        cursor.close();
        db.close();
        return json;
    }

    public void saveNewsContentCache(String id,String content){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("replace into CacheList(date,json) values(" + Integer.parseInt(id) + ",' " + content + "')");
        db.close();
    }

    public String getNewsContentCache(String id){
        String json=null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from CacheList where date = " +Integer.parseInt(id), null);
        if (cursor.moveToFirst()) {
            json =  cursor.getString(cursor.getColumnIndex("json"));
        }
        cursor.close();
        db.close();
        return json;
    }

}
