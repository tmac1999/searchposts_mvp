package com.mrz.searchposts.component.communicate.SendPost;

import com.mrz.searchposts.component.BasePresenter;
import com.mrz.searchposts.component.BaseView;
import com.mrz.searchposts.data.bean.Post;

/**
 * Created by zhengpeng on 2016/5/30.
 */
public interface SendPostContract {
    /**
     * 定义本模块的view需要有哪些行为
     */
    interface View extends BaseView<Presenter> {
        void showPostFailedUI();

        void showPostSuccessedUI();

        void showProgress(int progress);
    }

    interface Presenter extends BasePresenter {

        void choosePhoto();

        void sendPost(Post post);

        void openPostFailedUI();

        void openPostSuccessedUI();
    }
}
