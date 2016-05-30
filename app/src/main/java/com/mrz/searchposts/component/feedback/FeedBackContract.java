package com.mrz.searchposts.component.feedback;

import com.mrz.searchposts.component.BasePresenter;
import com.mrz.searchposts.component.BaseView;

/**
 * Created by zhengpeng on 2016/5/30.
 */
public interface FeedBackContract {
    /**
     * 定义本模块的view需要有哪些行为
     */
    interface View extends BaseView<Presenter> {
        void showFeedBackSuccessUI();

        void showFeedBackFailedUI();

    }

    interface Presenter extends BasePresenter {

        void openFeedBackSuccessUI();

        void openFeedBackFailedUI();

        void doFeedBack(String feedBackContent);
    }
}
