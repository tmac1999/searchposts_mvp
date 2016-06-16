package com.mrz.searchposts.component.register;

import com.mrz.searchposts.data.SPRepository;

/**
 * Created by zhengpeng on 2016/5/30.
 */
public class RegisterPresenter implements RegisterContract.Presenter {
    private RegisterActivity registerActivity;

    public RegisterPresenter(SPRepository spRepository, RegisterActivity registerActivity) {
        this.registerActivity = registerActivity;

    }

    @Override
    public void register() {

    }

    @Override
    public void start() {

    }
}
