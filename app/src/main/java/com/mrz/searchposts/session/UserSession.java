package com.mrz.searchposts.session;

import android.os.Build;

/**
 * Created by zhengpeng on 2016/5/31.
 */
public class UserSession {
    public static String getUserID() {
        String manufacturer = Build.MANUFACTURER;
        String serial = Build.SERIAL;
        String guestID = serial+"##"+manufacturer;
        String userID = getLoginUserID();
        if (userID==null){
            userID = guestID;
        }
        return userID;
    }

    public static String getLoginUserID() {
        return null;
    }
    public static String getLoginUserName() {
        return null;
    }
    public static String getUserName() {
        String userName = getLoginUserName();
        if (userName==null){
            userName = "guest";
        }
        return userName;
    }
}
