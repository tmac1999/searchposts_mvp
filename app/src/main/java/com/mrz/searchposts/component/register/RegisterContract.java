package com.mrz.searchposts.component.register;

import com.mrz.searchposts.component.BasePresenter;
import com.mrz.searchposts.component.BaseView;

/**
 * Created by zhengpeng on 2016/5/30.
 */
public interface RegisterContract {
    /**
     * 定义本模块的view需要有哪些行为
     */
    interface View extends BaseView<Presenter> {
        void showRegisterTips(RegisterResult result);

        void showRegisterSuccessedUI();

        void showRegisterFailedUI();
    }

    interface Presenter extends BasePresenter {

        void register();

    }
     enum RegisterResult{
        EMPTY_PARAM,INCORRECT_PWD,INCORRECT_USERNAME,USERNAME_EXIST,ERROR,SUCCESS
    }
}
