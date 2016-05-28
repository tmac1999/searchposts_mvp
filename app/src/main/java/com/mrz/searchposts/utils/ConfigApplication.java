package com.mrz.searchposts.utils;

import android.app.Application;

import com.avos.avoscloud.AVOSCloud;

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
    }
    public static Application getInstance(){
        return instance;
    }
}
