package com.mrz.searchposts.utils;

import android.widget.Toast;

/**
 * Created by zhengpeng on 2016/5/27.
 */
public class ToastUtils {


    public static void showSingletonToast(){

    }
    public static void longToast(String msg){
        Toast.makeText(ConfigApplication.getInstance(),msg,Toast.LENGTH_LONG).show();
    }
}
