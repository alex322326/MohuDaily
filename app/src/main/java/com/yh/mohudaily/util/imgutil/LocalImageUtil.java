package com.yh.mohudaily.util.imgutil;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Looper;

import com.yh.mohudaily.util.Constant;
import com.yh.mohudaily.util.LogUtil;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by YH on 2016/12/8.
 */

public class LocalImageUtil {

    public static Bitmap getDiskBitmap(String pathString)
    {
        Bitmap bitmap = null;
        try
        {
            File file = new File(pathString);
            if(file.exists())
            {
                bitmap = BitmapFactory.decodeFile(pathString);
            }
        } catch (Exception e)
        {
            // TODO: handle exception
        }


        return bitmap;
    }

    /**
     * 图片写入本地
     *
     * @param bitmap
     * @throws IOException
     */
    public static void saveBitmapToFile(Bitmap bitmap)
            throws IOException {
        String path = Environment.getExternalStorageDirectory().getPath();
        BufferedOutputStream os = null;
        try {
            File file = new File(path + Constant.IMAGE_FILE_NAME);  //新建图片
            if (!file.exists()) {  //如果文件夹不存在，创建文件夹
                file.mkdirs();
            }
            file.createNewFile();  //创建图片文件
            os = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);  //图片存成png格式。
        } finally {
            if (os != null) {
                try {
                    os.close();  //关闭流
                } catch (IOException e) {
                }
            }
        }
    }
}
