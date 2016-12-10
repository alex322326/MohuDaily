package com.yh.mohudaily.util;

/**
 * Created by HiAll_yan on 2016/5/18.
 */

import android.widget.Toast;
import com.yh.mohudaily.MohuDaily;

/**
 * 可以连续切换toast内容的toast
 * @author HiAll_yan
 *
 */
public class ToastUtil {
    private static Toast toast;
    public static void showToast(String text){
        if(toast==null){
            toast= Toast.makeText(MohuDaily.getInstance(), text, Toast.LENGTH_SHORT);
        }else{
            toast.setText(text);
        }
        toast.show();
    }
}
