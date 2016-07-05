package com.mrz.searchposts.utils;

import android.widget.Toast;

/**
 * Created by zhengpeng on 2016/5/27.
 */
public class ToastUtils {

    volatile static Toast toast;
    public  static void showSingletonToast(String msg){

        if (toast==null){
            toast = Toast.makeText(ConfigApplication.getInstance(),msg,Toast.LENGTH_SHORT);
        }
        toast.setText(msg);
        toast.show();

    }
    public static void longToast(String msg){
        Toast.makeText(ConfigApplication.getInstance(),msg,Toast.LENGTH_LONG).show();
    }
}
