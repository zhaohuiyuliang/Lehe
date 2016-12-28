package com.xiangsheng;

import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.xiangsheng.dao.impl.DaoManager;
import com.xiangsheng.net.Controller;

import java.io.File;


/**
 * Created by wangliang on 6/13/16.
 */
public class ControlApplication extends Application {
    private static ControlApplication application;
    private Controller controller;
    private DaoManager daoManager;

    public static ControlApplication getApplication() {
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        initImageLoader(getApplicationContext());// ImageLoader初始化
    }


    private void initImageLoader(Context context) {
        // 缓存文件的目录
        File cacheDir = StorageUtils.getOwnCacheDirectory(context, "imageloader/xiangsheng");
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .memoryCacheExtraOptions(480, 800)
                // max width, max height，即保存的每个缓存文件的最大长宽
                .threadPoolSize(3)
                // 线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator()) // 将保存的时候的URI名称用MD5
                // 加密
                .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024)) // You
                // can pass your own memory cache
                // implementation/你可以通过自己的内存缓存实现
                .memoryCacheSize(2 * 1024 * 1024) // 内存缓存的最大值
                .diskCacheSize(50 * 1024 * 1024) // 50 MB SD卡(本地)缓存的最大值
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                // 由原先的discCache -> diskCache
                .diskCache(new UnlimitedDiscCache(cacheDir))// 自定义缓存路径
                .imageDownloader(new BaseImageDownloader(context, 5 * 1000, 30 * 1000)) // connectTimeout
                // (5s),readTimeout(30s)超时时间
                .writeDebugLogs() // Remove for release app
                .build();
        // 全局初始化此配置
        ImageLoader.getInstance().init(config);
    }

    public Controller getController() {
        if (controller == null) {
            controller = new Controller();
        }
        return controller;
    }

    public DaoManager getDaoManager() {
        if (daoManager == null) {
            daoManager = new DaoManager(this);
        }
        return daoManager;
    }

}
