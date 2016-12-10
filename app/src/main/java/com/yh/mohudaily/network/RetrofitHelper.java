package com.yh.mohudaily.network;

import com.yh.mohudaily.MohuDaily;
import com.yh.mohudaily.network.service.BeautyImageService;
import com.yh.mohudaily.network.service.BeforeNewsService;
import com.yh.mohudaily.network.service.HotNewsService;
import com.yh.mohudaily.network.service.LatestNewsService;
import com.yh.mohudaily.network.service.NewsLongCommentService;
import com.yh.mohudaily.network.service.NewsContentService;
import com.yh.mohudaily.network.service.NewsExtraService;
import com.yh.mohudaily.network.service.NewsShortCommentService;
import com.yh.mohudaily.network.service.SectionsService;
import com.yh.mohudaily.network.service.ThemesService;
import com.yh.mohudaily.network.service.StartImageService;
import com.yh.mohudaily.network.service.ThemeContentService;
import com.yh.mohudaily.network.service.VideoService;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by YH on 2016/11/8.
 */

public class RetrofitHelper {
    private static OkHttpClient mOkhttpClient;

    private static final String ZHIHU_BASE_API= "http://news-at.zhihu.com/api/4/";

    private static final String ZHIHU_NEWS = "news/";
    public static final String SHOWAPI_BASEAPI="http://route.showapi.com/";
    public static final String SHOWAPI_APPID = "28188";
    public static final String SHOWAPI_SECRET = "6473f237f7f44fbd96d1b9da751b967f";

    public static final int SHOWAPI_TYPE =41;
    /*
    *
      http://route.showapi.com/255-1?showapi_appid=28188&type=41&page=0&showapi_sign=6473f237f7f44fbd96d1b9da751b967f
      http://route.showapi.com/255-1?showapi_appid=28188&type=10&page=0&showapi_sign=6473f237f7f44fbd96d1b9da751b967f

    http://route.showapi.com/1177-1?showapi_appid=28188&page=1&showapi_sign=6473f237f7f44fbd96d1b9da751b967f
    * */

    static {
        initOkhttpClient();
    }

    /**
     * 日报数据请求
     * @return
     */
    public static LatestNewsService getLatestApi(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ZHIHU_BASE_API)
                .client(mOkhttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        return retrofit.create(LatestNewsService.class);
    }

    /**
     * 图片集合列表
     *
     */
    public static BeautyImageService getBeautyImageApi(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SHOWAPI_BASEAPI)
                .client(mOkhttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        return retrofit.create(BeautyImageService.class);

    }
    /**
     * 视频集合列表
     *
     */
    public static VideoService getVideoApi(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SHOWAPI_BASEAPI)
                .client(mOkhttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        return retrofit.create(VideoService.class);

    }
    /**
     * 日报数据请求
     * 过去的日期
     * @return
     */
    public static BeforeNewsService getBeforeNewsApi(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ZHIHU_BASE_API)
                .client(mOkhttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        return retrofit.create(BeforeNewsService.class);
    }
    /**
     * 启动图片数据请求
     * @return
     */
    public static StartImageService getStartImageApi(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ZHIHU_BASE_API)
                .client(mOkhttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit.create(StartImageService.class);
    }

    /**
     * 主题数据请求
     * @return
     */
    public static ThemesService getThemesApi(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ZHIHU_BASE_API)
                .client(mOkhttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit.create(ThemesService.class);
    }

    /**
     * 专栏数据请求
     * @return
     */
    public static SectionsService getSectionsApi(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ZHIHU_BASE_API)
                .client(mOkhttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit.create(SectionsService.class);
    }

    /**
     * 热点数据请求
     * @return
     */
    public static HotNewsService getHotNewsApi(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ZHIHU_BASE_API)
                .client(mOkhttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit.create(HotNewsService.class);
    }

    /**
     * 获取主题内容
     * @return
     */
    public static ThemeContentService getThemeContent(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ZHIHU_BASE_API)
                .client(mOkhttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit.create(ThemeContentService.class);
    }

    /**
     * 获取新闻内容
     * @return
     */
    public static NewsContentService getNewsContentApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ZHIHU_BASE_API)
                .client(mOkhttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit.create(NewsContentService.class);
    }

    /**
     * 获取新闻评论信息
     * @return
     */
    public static NewsExtraService getNewsExtrasApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ZHIHU_BASE_API)
                .client(mOkhttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit.create(NewsExtraService.class);
    }

    /**
     * 长评论API
     * @return
     */
    public static NewsLongCommentService getNewsLongCommentsApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ZHIHU_BASE_API)
                .client(mOkhttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit.create(NewsLongCommentService.class);
    }
    /**
     * 短评论API
     * @return
     */
    public static NewsShortCommentService getNewsShortCommentsApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ZHIHU_BASE_API)
                .client(mOkhttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit.create(NewsShortCommentService.class);
    }
    /**
     * 初始化OkHttp
     */
    private static void initOkhttpClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        if (mOkhttpClient == null)
        {
            synchronized (RetrofitHelper.class)
            {
                if (mOkhttpClient == null)
                {
                    //设置Http缓存
                    Cache cache = new Cache(new File(MohuDaily.getInstance()
                            .getCacheDir(), "HttpCache"), 1024 * 1024 * 100);
                    mOkhttpClient = new OkHttpClient.Builder()
                            .cache(cache)
                            .addInterceptor(interceptor)
                            .addNetworkInterceptor(new CacheInterceptor())
                            .retryOnConnectionFailure(true)
                            .connectTimeout(30, TimeUnit.SECONDS)
                            .writeTimeout(20, TimeUnit.SECONDS)
                            .readTimeout(20, TimeUnit.SECONDS)
                            .build();
                }
            }
        }
    }


    /**
     * 为okhttp添加缓存，缓存时间为1小时，
     * 这里是考虑到服务器不支持缓存时，从而让okhttp支持缓存
     */
    private static class CacheInterceptor implements Interceptor
    {
        @Override
        public Response intercept(Chain chain) throws IOException
        {

            Request request = chain.request();
            return chain.proceed(request).newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", "max-age=" + 3600)
                    .build();
        }
    }


}
