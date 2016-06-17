package com.mrz.searchposts.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.avos.avoscloud.AVUser;

/**
 * Created by zhengpeng on 2016/6/17.
 */
public class SharePreferUtil {
    private Context context;
    private SharedPreferences sp;

    public SharePreferUtil(Context context) {
        this.context = context;
    }


    /**
     * 保存用户信息
     */
    public void saveUserAfterLogin(AVUser avUser) {
        sp = context.getSharedPreferences("USERINFO", Context.MODE_PRIVATE);
    }


    /**
     * 获取用户信息
     */
    public AVUser getUserInfo() {

        return null;
    }
}