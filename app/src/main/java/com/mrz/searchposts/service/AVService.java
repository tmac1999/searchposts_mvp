package com.mrz.searchposts.service;

import android.content.Context;
import android.util.Log;

import com.avos.avoscloud.AVCloud;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVInstallation;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.CountCallback;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.FunctionCallback;
import com.avos.avoscloud.PushService;
import com.avos.avoscloud.RequestPasswordResetCallback;
import com.avos.avoscloud.SaveCallback;
import com.avos.avoscloud.SignUpCallback;
import com.mrz.searchposts.component.login.LoginActivity;
import com.mrz.searchposts.data.bean.Post;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AVService {
    public static void countDoing(String doingObjectId, CountCallback countCallback) {
        AVQuery<AVObject> query = new AVQuery<AVObject>("DoingList");
        query.whereEqualTo("doingListChildObjectId", doingObjectId);
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MINUTE, -10);
        // query.whereNotEqualTo("userObjectId", userId);
        query.whereGreaterThan("createdAt", c.getTime());
        query.countInBackground(countCallback);
    }

    //Use callFunctionMethod
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static void getAchievement(String userObjectId) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("userObjectId", userObjectId);
        AVCloud.callFunctionInBackground("hello", parameters,
                new FunctionCallback() {
                    @Override
                    public void done(Object object, AVException e) {
                        if (e == null) {
                            Log.e("at", object.toString());// processResponse(object);
                        } else {
                            // handleError();
                        }
                    }
                });
    }

    public static void createDoing(String userId, String doingObjectId) {
        AVObject doing = new AVObject("DoingList");
        doing.put("userObjectId", userId);
        doing.put("doingListChildObjectId", doingObjectId);
        doing.saveInBackground();
    }

    public static void requestPasswordReset(String email, RequestPasswordResetCallback callback) {
        AVUser.requestPasswordResetInBackground(email, callback);
    }

    public static void findDoingListGroup(FindCallback<AVObject> findCallback) {
        AVQuery<AVObject> query = new AVQuery<AVObject>("DoingListGroup");
        query.orderByAscending("Index");
        query.findInBackground(findCallback);
    }

    public static void findChildrenList(String groupObjectId, FindCallback<AVObject> findCallback) {
        AVQuery<AVObject> query = new AVQuery<AVObject>("DoingListChild");
        query.orderByAscending("Index");
        query.whereEqualTo("parentObjectId", groupObjectId);
        query.findInBackground(findCallback);
    }

    public static void initPushService(Context ctx) {
        PushService.setDefaultPushCallback(ctx, LoginActivity.class);
        PushService.subscribe(ctx, "public", LoginActivity.class);
        AVInstallation.getCurrentInstallation().saveInBackground();
    }

    public static void signUp(String username, String password, String email, SignUpCallback signUpCallback) {
        AVUser user = new AVUser();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.signUpInBackground(signUpCallback);
    }

    public static void logout() {
        AVUser.logOut();
    }

    public static void createAdvice(String userId, String devicename, String advice, SaveCallback saveCallback) {
        AVObject doing = new AVObject("SuggestionByUser");
        doing.put("UserObjectId", userId);
        doing.put("UserSuggestion", advice);
        doing.put("devicename", devicename);
        doing.saveInBackground(saveCallback);
    }
    public static void updateUserSearchData(String userId, String userName, String tiebaName,String createTiebaTableTime,int tiebaPageSum,SaveCallback saveCallback) {
        AVObject doing = new AVObject("UserSearchData");
        doing.put("UserObjectId", userId);
        doing.put("userName", userName);
        doing.put("tiebaName",tiebaName);
        doing.put("createTiebaTableTime",createTiebaTableTime);
        doing.put("tiebaPageSum",tiebaPageSum);
        doing.saveInBackground(saveCallback);
    }

    public static void sendPost(Post post, SaveCallback saveCallback) {
        AVObject doing = new AVObject("Post");
        doing.put("title", post.title);
        doing.put("content", post.content);
        doing.put("userID", post.userID);
        doing.put("time", post.time);
        doing.put("userName", post.userName);
        if (post.imgURL != null) {
            doing.put("imgURL", post.imgURL);
        }
        doing.saveInBackground(saveCallback);
    }
    public static void queryAllPost(FindCallback findCallback) {
        AVQuery query = new AVQuery("Post");
        query.findInBackground(findCallback);
    }

}

