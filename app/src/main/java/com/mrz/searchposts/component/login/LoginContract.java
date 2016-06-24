package com.mrz.searchposts.component.login;

import com.mrz.searchposts.component.BasePresenter;
import com.mrz.searchposts.component.BaseView;

/**
 * Created by zhengpeng on 2016/5/27.
 */
public interface LoginContract {
    /**
     * 定义本模块的view需要有哪些行为
     */
    interface View extends BaseView<Presenter> {
        void showWrongPwd();

        void showLoginResult(LoginResult loginResult,int resultcode);

        void showRegisterUI();

        void showLoginFinishedUI();
    }

    interface Presenter extends BasePresenter {
        void login(String username,String pwd);

        void openRegisterUI();

        void openLoginFinishedUI();

        void openForgetPwdUI();
    }
    enum LoginResult{
        EMPTYPARAM,WRONGPWD,NONEEXISTENT_USER,ERROR,SUCCESS
    }
}
