package com.mrz.searchposts.component;

/**
 * Created by zhengpeng on 2016/5/27.
 */
public interface BaseView<T> {
    void setPresenter(T presenter);

    void showLoadingBar();

    void showNetWorkTips();
}
