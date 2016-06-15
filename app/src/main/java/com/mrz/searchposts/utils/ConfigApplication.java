package com.mrz.searchposts.utils;

import android.app.Application;
import android.content.Context;

import com.avos.avoscloud.AVOSCloud;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

/**
 * Created by zhengpeng on 2016/5/24.
 */
public class ConfigApplication extends Application {
    private static Application instance ;
    @Override
    public void onCreate() {
        super.onCreate();
        AVOSCloud.useAVCloudCN();
        // U need your AVOS key and so on to run the code.
        AVOSCloud.initialize(this,
                "9EGsTuzmuzDJedjb9veYSkYY-gzGzoHsz",
                "tGy2SX18yfgbaQv7zHP3WMIu");
        instance = this;
        initImageLoader(getApplicationContext());



    }
    public static Application getInstance(){
        return instance;
    }
    public static void initImageLoader(Context context) {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .writeDebugLogs()
                .build();
        ImageLoader.getInstance().init(config);

    }
}
