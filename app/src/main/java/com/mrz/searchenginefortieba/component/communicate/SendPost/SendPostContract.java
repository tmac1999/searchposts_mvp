package com.mrz.searchenginefortieba.component.communicate.SendPost;

import com.mrz.searchenginefortieba.component.BasePresenter;
import com.mrz.searchenginefortieba.component.BaseView;
import com.mrz.searchenginefortieba.data.bean.Post;

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
