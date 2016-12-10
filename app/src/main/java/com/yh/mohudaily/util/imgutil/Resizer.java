package com.yh.mohudaily.util.imgutil;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.yh.mohudaily.util.LogUtil;

import java.io.FileDescriptor;
import java.io.InputStream;

/**
 * Created by YH on 2016/12/4.
 * 图片压缩类
 */

public class Resizer {
    private static final String TAG = "ImageResizer";

    /**
     * 解析资源图片
     * @param res
     * @param resid 资源ID
     * @param reqWidth  目标宽带
     * @param reqHeight 目标高度
     * @return
     */
    public Bitmap decodeBitMapFromResource(Resources res, int resid, int reqWidth, int reqHeight){
        BitmapFactory.Options options = new BitmapFactory.Options();
        //解析目标图片宽高
        options.inJustDecodeBounds = true;

        BitmapFactory.decodeResource(res,resid,options);

        //计算缩放比例  inSimpleSize
        options.inSampleSize = calculateInSimpleSize(options,reqWidth,reqHeight);

        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeResource(res, resid,options);
    }
    public Bitmap decodeBitMapFromInputStream(InputStream stream, int reqWidth, int reqHeight){
        BitmapFactory.Options options = new BitmapFactory.Options();
        //解析目标图片宽高
        options.inJustDecodeBounds = true;

        BitmapFactory.decodeStream(stream,null,options);

        //计算缩放比例  inSimpleSize
        options.inSampleSize = calculateInSimpleSize(options,reqWidth,reqHeight);

        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeStream(stream,null, options);
    }


    public Bitmap decodeBitMapFromFileDescriptor(FileDescriptor fd,int reqWidth,int reqHeight){
        BitmapFactory.Options options = new BitmapFactory.Options();
        //解析目标图片宽高
        options.inJustDecodeBounds = true;

        BitmapFactory.decodeFileDescriptor(fd,null,options);

        options.inSampleSize = calculateInSimpleSize(options, reqWidth, reqHeight);

        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFileDescriptor(fd,null,options);
    }
    /**
     * 计算采样率
     * @param options
     * @param reqWidth
     *  @param reqHeight
     */
    private int calculateInSimpleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        //初始采样率
        int inSimpleSize = 1;
        //判0
        if(reqWidth==0||reqHeight==0){
            return 1;
        }
        //获取图片解析宽高
        final int width = options.outWidth;
        final int height = options.outHeight;
        LogUtil.LogE("原始图片宽高，width："+width+",heght:"+height);
        if(height>reqHeight||width>reqWidth){
            //任意宽高大于需求宽高
            final int halfHeight = height/2;
            final int halfWidth = width/2;
            //定义循环 不断缩小halfheight 直到任意小于目标宽高跳出循环
            while((halfHeight/inSimpleSize)>=reqHeight&&(halfWidth/inSimpleSize)>=reqWidth){
                //官方建议取值为2的指数幂
                inSimpleSize*=2;
            }
        }
        return inSimpleSize;
    }
}
