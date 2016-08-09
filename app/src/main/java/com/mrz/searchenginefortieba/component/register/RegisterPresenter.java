package com.mrz.searchenginefortieba.component.register;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.SignUpCallback;
import com.mrz.searchenginefortieba.data.SPRepository;
import com.mrz.searchenginefortieba.service.AVService;

/**
 * Created by zhengpeng on 2016/5/30.
 */
public class RegisterPresenter implements RegisterContract.Presenter {
    private RegisterActivity registerActivity;

    public RegisterPresenter(SPRepository spRepository, RegisterActivity registerActivity) {
        this.registerActivity = registerActivity;

    }

    @Override
    public void register(String username,String password,String email) {
        SignUpCallback signUpCallback = new SignUpCallback() {
            public void done(AVException e) {
                registerActivity.progressDialogDismiss();
                if (e == null) {
                    registerActivity.showRegisterSuccessedUI();
                    registerActivity.finish();
                } else {
                    switch (e.getCode()) {
                        case AVException.USERNAME_TAKEN:
                            registerActivity.showRegisterTips(RegisterContract.RegisterResult.USERNAME_EXIST);
                            break;
                        case AVException.EMAIL_TAKEN:
                            registerActivity.showRegisterTips(RegisterContract.RegisterResult.EMAIL_TAKEN);
                            break;
                        default:
                            registerActivity.showRegisterTips(RegisterContract.RegisterResult.ERROR);
                            break;
                    }
                }
            }
        };
        AVService.signUp(username, password, email, signUpCallback);
    }

    @Override
    public void start() {

    }
}
