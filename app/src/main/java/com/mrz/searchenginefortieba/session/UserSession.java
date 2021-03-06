package com.mrz.searchenginefortieba.session;

import android.os.Build;

import com.avos.avoscloud.AVUser;

/**
 * Created by zhengpeng on 2016/5/31.
 */
public class UserSession {
    public static String getUserID() {
        String manufacturer = Build.MANUFACTURER;
        String serial = Build.SERIAL;
        String guestID = serial + "##" + manufacturer;
        String userID = getLoginUserID();
        if (userID == null) {
            userID = guestID;
        }
        return userID;
    }

    /**
     * 将user表 ObjectId作为userid
     *
     * @return
     */
    private static String getLoginUserID() {
        if (AVUser.getCurrentUser()==null){
            return null;
        }
        return AVUser.getCurrentUser().getObjectId();
    }

    public static String getLoginUserName() {
        if (AVUser.getCurrentUser()==null){
            return null;
        }
        return AVUser.getCurrentUser().getUsername();
    }

    public static String getUserName() {
        String userName = getLoginUserName();
        if (userName == null) {
            userName = "guest";
        }
        return userName;
    }
}
