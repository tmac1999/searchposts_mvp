package com.mrz.searchenginefortieba.utils;

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

    public static String KEY_LAST_BROWSE_TIEBANAME = "KEY_LAST_BROWSE_TIEBANAME";

    public String getLastBrowseTiebaName() {
        SharedPreferences sp = context.getSharedPreferences("USERINFO", Context.MODE_PRIVATE);
        return sp.getString(KEY_LAST_BROWSE_TIEBANAME, null);

    }
    public void setLastBrowseTiebaName(String name) {
        SharedPreferences sp = context.getSharedPreferences("USERINFO", Context.MODE_PRIVATE);
        sp.edit().putString(KEY_LAST_BROWSE_TIEBANAME,name).apply();
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
