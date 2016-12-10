package com.yh.mohudaily.util.imgutil;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.StatFs;
import android.util.LruCache;
import android.widget.ImageView;

import com.yh.mohudaily.R;
import com.yh.mohudaily.util.LogUtil;
import com.yh.mohudaily.util.ToastUtil;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.LogRecord;


/**
 * Created by YH on 2016/12/4.
 * 1、切换请求框架，默认HttpUrlConnection（还是未优化过的，示例项目提供volley和okhttp3的下载器的实现）
 * 2、二级缓存，一级内存LruCache，二级本地DiskLruCache
 * 3、可以决定缓存裁剪后的图片还是原图
 */

public class MohuImageLoader {
    private static final int MESSAGE_POST_RESULT = 1;
    //存活时间
    private static final long KEEP_ALIVE = 10L;
    // key tag 必须是资源id
    private static final int TAG_KEY_URI = R.id.image_tag;

    private static final int IO_BUFFUER_SIZE = 8 * 1024;//io缓冲区大小
    private static final long MAX_DISKCACHE_SIZE = 100 * 1024 * 1024;//最大本地缓存100M
    private static final int DISK_CACHE_INDEX = 0;

    //线程池配置 核心数 最大线程数量
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int CORE_POOL_SIZE = CPU_COUNT + 1;
    private static final int MAX_POOL_SIZE = CPU_COUNT * 2 + 1;
    private static final ThreadFactory sThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable runnable) {
            //第二个参数自定义线程名字
            return new Thread(runnable, "ImageLoader#" + mCount.getAndIncrement());
        }
    };
    private static final Executor THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(CORE_POOL_SIZE,
            MAX_POOL_SIZE,
            KEEP_ALIVE,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(), sThreadFactory);
    private Handler mMainHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //UI线程设置图片
            LoaderResult result = (LoaderResult) msg.obj;
            ImageView imageView = result.imageView;
            String uri = (String) imageView.getTag(TAG_KEY_URI);
            if(uri.equals(result.uri)){
                imageView.setImageBitmap(result.bitmap);
            }
        }
    };
    //内存LruCache
    private LruCache<String, Bitmap> mMemoryCache;
    //本地DiskLruCache
    private DiskLruCache mDiskLruCache;
    private Context mContext;

    //图片裁剪对象
    private Resizer mResizer = new Resizer();
    //磁盘缓存标志位
    private boolean mIsDiskLruCacheCreated = false;

    private boolean isSaveDiskLrucache = true;

    private MohuImageLoader(Context context) {
        try {
            //防止传入的是activity的上下文 引发内存泄漏
            Activity activity = (Activity) context;
            mContext = context.getApplicationContext();
        } catch (Exception e) {
            e.printStackTrace();
            mContext = context;
        }
        //最大缓存 kb
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        //进程可用大小的1/8
        int cacheSize = maxMemory / 8;
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            //重写内部方法计算缓存图片大小

            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                //行大小X高度
                return bitmap.getRowBytes() * bitmap.getHeight() / 1024;
            }
        };

        //磁盘缓存路径
        File diskCacheDir = getDiskCacheDir(mContext, "bitmap");
        if (!diskCacheDir.exists()) {
            diskCacheDir.mkdirs();
        }
        //验证缓存空间
        if (getUsableSpace(diskCacheDir) > MAX_DISKCACHE_SIZE) {

            try {
                mDiskLruCache = DiskLruCache.open(diskCacheDir, 1, 1, MAX_DISKCACHE_SIZE);
                mIsDiskLruCacheCreated = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            ToastUtil.showToast("内存卡容量不足！");
        }

    }

    /**
     * 静态获取方法
     * todo 单列
     *
     * @param context
     * @return
     */
    public static MohuImageLoader build(Context context) {
        return new MohuImageLoader(context);
    }



    /**
     * 绑定ImageView和BitMap
     * 从内存获取  没有就从本地获取
     *
     * @param uri
     * @param imageView
     */
    public void bindBitmap(String uri, final ImageView imageView) {
        bindBitmap(uri, imageView, 0, 0,isSaveDiskLrucache);
    }

    public void bindBitmap(final String uri, final ImageView imageView, final int reqWidth, final int reqHeight,boolean isSaveDisk) {
        imageView.setTag(TAG_KEY_URI, uri);
        Bitmap bitmap = loadBitMapFromMemCache(uri);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
            return;
        }
        isSaveDiskLrucache = isSaveDisk;
        //异步子线程加载
        Runnable loadBitMapTask = new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = loadBitMap(uri, reqWidth, reqHeight);
                if (bitmap != null) {
                    LoaderResult result = new LoaderResult(imageView, uri, bitmap);
                    Message msg = mMainHandler.obtainMessage(MESSAGE_POST_RESULT, result);
                    msg.sendToTarget();

                }
            }
        };
        THREAD_POOL_EXECUTOR.execute(loadBitMapTask);
    }

    private Bitmap loadBitMap(String uri, int reqWidth, int reqHeight) {
        Bitmap bitmap = loadBitMapFromMemCache(uri);
        if (bitmap != null) {
            LogUtil.LogE("从内存缓存获取图片uri：" + uri);
            return bitmap;
        }

        try {
            bitmap = loadBitMapFromDiskCache(uri, reqWidth, reqHeight);
            if (bitmap != null) {
                LogUtil.LogE("从本地缓存获取图片uri：" + uri);
                return bitmap;
            }
            if(isSaveDiskLrucache){
                bitmap = loadBitmapFromHttp(uri,reqWidth,reqHeight);
            }else {
                bitmap = downloadBitmapFromUrl(uri);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        //内存和本地都没有 且本地缓存没有建立
        if (bitmap == null && !mIsDiskLruCacheCreated) {
            LogUtil.LogE("没有创建本地缓存！");
            bitmap = downloadBitmapFromUrl(uri);
        }
        return bitmap;
    }

    /**
     * 内存缓存加载bitmap
     *
     * @param uri
     * @return
     */
    private Bitmap loadBitMapFromMemCache(String uri) {
        String key = hashKeyFromUrl(uri);
        Bitmap bitmap = getBitMapFromMemoryCache(key);
        return bitmap;
    }
    /**
     * 内存缓存添加图片
     *
     * @param key
     * @param bitmap
     */
    private void addBitMapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitMapFromMemoryCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }
    /**
     * 本地缓存加载图片
     * @param url
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    private Bitmap loadBitMapFromDiskCache(String url, int reqWidth, int reqHeight) throws IOException {
        //线程验证
        if (Looper.myLooper() == Looper.getMainLooper()) {
            LogUtil.LogE("不建议从UI线程加载图片");
        }
        if (mDiskLruCache == null) {
            return null;
        }

        Bitmap bitmap = null;
        //图片url的哈希值作为key
//        String key = url;
        String key = hashKeyFromUrl(url);
        //存储快照
        DiskLruCache.Snapshot snapshot = mDiskLruCache.get(key);
        if (snapshot != null) {
            FileInputStream fileInputStream = (FileInputStream) snapshot.getInputStream(DISK_CACHE_INDEX);
            FileDescriptor fd = fileInputStream.getFD();
            //resize图片
            bitmap = mResizer.decodeBitMapFromFileDescriptor(fd, reqWidth, reqHeight);
            if (bitmap != null) {
                addBitMapToMemoryCache(key, bitmap);
            }
        }
        return bitmap;
    }

    /**
     * 网络加载bitmap到本地缓存
     * 先将图片流写入本地
     * 再从本地获取图片
     *
     * @return
     */
    private Bitmap loadBitmapFromHttp(String url, int reqWidth, int reqHeight) throws IOException {
        //UI线程验证
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw new RuntimeException("UI线程禁止访问网络！");
        }
        if (mDiskLruCache == null) {
            return null;
        }
        String key = hashKeyFromUrl(url);
        DiskLruCache.Editor editor = mDiskLruCache.edit(key);
        if (editor != null) {
            //网络流写入本地缓存
            OutputStream outputStream = editor.newOutputStream(DISK_CACHE_INDEX);
            if (downloadUrlToStream(url, outputStream)) {
                editor.commit();
            } else {
                editor.abort();
            }
            mDiskLruCache.flush();
        }
        return loadBitMapFromDiskCache(url, reqWidth, reqHeight);

    }

    /**
     * 判断流写入是否成功
     *
     * @param urlString
     * @param outputStream
     * @return
     */
    private boolean downloadUrlToStream(String urlString, OutputStream outputStream) throws IOException {
        HttpURLConnection urlConnection = null;
        BufferedOutputStream out = null;
        BufferedInputStream in = null;
        try {
            URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            in = new BufferedInputStream(urlConnection.getInputStream(), IO_BUFFUER_SIZE);

            out = new BufferedOutputStream(outputStream, IO_BUFFUER_SIZE);
            int b;
            while ((b = in.read()) != -1) {
                out.write(b);
            }
            return true;

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            try {
                out.close();
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return false;
    }

    /**
     * 下载bitmap
     * @param urlString
     * @return
     */
    private Bitmap downloadBitmapFromUrl(String urlString) {
        Bitmap bitmap = null;
        HttpURLConnection urlConnection = null;
        BufferedInputStream in = null;
        URL url = null;
        try {
            url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            in = new BufferedInputStream(urlConnection.getInputStream(), IO_BUFFUER_SIZE);
            bitmap = mResizer.decodeBitMapFromInputStream(in, 200, 200);
//            bitmap = BitmapFactory.decodeStream(in);
        } catch (IOException e) {
            LogUtil.LogE("网络加载失败！");
            e.printStackTrace();
        }finally {
            if(urlConnection!=null){
                urlConnection.disconnect();
            }
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return bitmap;
    }

    /**
     * 内存缓存查重
     *
     * @param key
     * @return
     */
    private Bitmap getBitMapFromMemoryCache(String key) {
        return mMemoryCache.get(key);
    }

    /**
     * 获取可用储存
     *
     * @param path
     * @return
     */
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    private long getUsableSpace(File path) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD){
            return path.getUsableSpace();
        }
        final StatFs stats = new StatFs(path.getPath());
        return (long)stats.getBlockSize()*(long)stats.getAvailableBlocks();
    }

    /**
     * 获取磁盘路径
     *
     * @param context
     * @param uniqueName
     * @return
     */
    private File getDiskCacheDir(Context context, String uniqueName) {
        //存取验证
        boolean diskAvailable =  Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        final String cachePath;
        if(diskAvailable){
            cachePath = context.getExternalCacheDir().getPath();
            LogUtil.LogE("path:"+cachePath.toString());
        }else {
            cachePath = context.getCacheDir().getPath();

        }
        return new File(cachePath+File.separator+uniqueName);

    }

    /**
     * 获取字符串哈希值
     *
     * @param url
     * @return
     */
    private String hashKeyFromUrl(String url) {
        String cacheKey;
        try {
            MessageDigest mDigest   = MessageDigest.getInstance("MD5");
            mDigest.update(url.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey  =String.valueOf(url.hashCode());
        }

        return cacheKey;
    }

    /**
     * 字符类型转换
     * @param bytes
     * @return
     */
    private String bytesToHexString(byte[] bytes) {
        StringBuilder sb    = new StringBuilder();
        for (int i = 0;i<bytes.length;i++){
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if(hex.length()==1){
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();

    }

    private static class LoaderResult{
        public ImageView imageView;
        public String uri;
        public Bitmap bitmap;

        public LoaderResult(ImageView imageView,String uri,Bitmap bitmap){
            this.imageView = imageView;
            this.uri =uri;
            this.bitmap = bitmap;
        }
    }
}
