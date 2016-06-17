package com.mrz.searchposts.component.me;

import com.mrz.searchposts.component.BasePresenter;
import com.mrz.searchposts.component.BaseView;

/**
 * Created by zhengpeng on 2016/5/27.
 */
public interface MeContract {
    /**
     * 定义本模块的view需要有哪些行为
     */
    interface View extends BaseView<Presenter> {
        void showMe();

        void showRequestFailed();
    }

    interface Presenter extends BasePresenter {
        void getMeData();
    }
}
