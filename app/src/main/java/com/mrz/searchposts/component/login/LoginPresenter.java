package com.mrz.searchposts.component.login;

import android.text.TextUtils;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.mrz.searchposts.data.SPRepository;

/**
 * Created by zhengpeng on 2016/5/27.
 */
public class LoginPresenter implements LoginContract.Presenter{
    private LoginActivity loginActivity;
    public LoginPresenter(SPRepository spRepository,LoginActivity loginActivity){
        this.loginActivity = loginActivity;

    }


    @Override
    public void login(String username, String pwd) {
        if (TextUtils.isEmpty(username)||TextUtils.isEmpty(pwd)){
            loginActivity.showLoginResult(LoginContract.LoginResult.EMPTYPARAM);
            return;
        }
        AVUser.logInInBackground(username, pwd, new LogInCallback<AVUser>() {
            @Override
            public void done(AVUser avUser, AVException e) {
                saveAVUser(avUser);
                e.printStackTrace();
                loginActivity.showLoginFinishedUI();
            }
        });
    }

    /**
     * 不像浏览器可以保存session，也不像环信等及时通讯保持长tcp链接。
     * restful请求完后，服务端并不记录客户端状态？
     * 因此登录后本地保存登录状态
     * 登录成功，保存到sp
     * ###所有登录接口调用成功之后，云端会返回一个 SessionToken 给客户端，客户端在发送 HTTP 请求的时候，
     * ###Android SDK 会在 HTTP 请求的 Header 里面自动添加上当前用户的 SessionToken 作为这次请求的发起者 AVUser 的身份认证信息。
     * ###如果不调用 登出 方法，当前用户的缓存将永久保存在客户端
     * ###AVUser.logOut();// 清除缓存用户对象
     * ###AVUser currentUser = AVUser.getCurrentUser();// 现在的 currentUser 是 null 了
     * @param avUser
     */
    private void saveAVUser(AVUser avUser) {

    }

    @Override
    public void openForgetPwdUI() {

    }

    @Override
    public void openRegisterUI() {
        loginActivity.showRegisterUI();
    }

    @Override
    public void openLoginFinishedUI() {
        loginActivity.showLoginFinishedUI();
    }

    @Override
    public void start() {

    }


}
