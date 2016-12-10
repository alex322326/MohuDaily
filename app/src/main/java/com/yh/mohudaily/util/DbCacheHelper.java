package com.yh.mohudaily.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by YH on 2016/12/8.
 */

public class DbCacheHelper extends SQLiteOpenHelper {
    public DbCacheHelper(Context context,  int version) {
        super(context,"cache.db",null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists CacheList (id INTEGER primary key autoincrement,date INTEGER unique,json text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
