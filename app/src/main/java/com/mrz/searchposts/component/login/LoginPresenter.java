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
