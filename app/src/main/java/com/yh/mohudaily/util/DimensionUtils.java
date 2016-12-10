package com.yh.mohudaily.util;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

public class DimensionUtils {
	
	/** 
     *根据手机的分辨率从 dp 的单位 转成为 px(像素) 
     */  
    public static int dip2px(Context context, float dpValue) {  
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (dpValue * scale + 0.5f);  
    }  
  
    /** 
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp 
     */  
    public static int px2dip(Context context, float pxValue) {  
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (pxValue / scale + 0.5f);  
    } 

	/**
	*获取屏幕的宽度
	*
	*/
	public static int getScreenWidth(Context context){
		DisplayMetrics dm = new DisplayMetrics();
		((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.widthPixels ;
	}

	public static int getScreenRealHeight(Context context){
		return 1920;
	}
	/**
	*获取屏幕的高度
	*
	*/
	public static int getScreenHeight(Context context){
		DisplayMetrics dm = new DisplayMetrics();
		((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.heightPixels ;
	}
	/**
	 * 获取dimens资源
	 */
	public static int getDimens(Context context,int id){
		float dimen = context.getResources().getDimension(id);
		return (int) dimen;
	}
}
