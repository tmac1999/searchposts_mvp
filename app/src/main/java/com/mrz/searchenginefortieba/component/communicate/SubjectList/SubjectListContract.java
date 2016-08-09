package com.mrz.searchenginefortieba.component.communicate.SubjectList;

import com.mrz.searchenginefortieba.component.BasePresenter;
import com.mrz.searchenginefortieba.component.BaseView;

/**
 * Created by zhengpeng on 2016/5/30.
 */
public interface SubjectListContract {
    /**
     * 定义本模块的view需要有哪些行为
     */
    interface View extends BaseView<Presenter> {
        void showList(SubjectListAdapter adapter);

        void showRequestSuccessedUI();

        void showRequestFailedUI();

    }

    enum RequestType {
        MORE, REFRESH
    }

    interface Presenter extends BasePresenter {

        void getListFromNet(RequestType type);

        void getListFromLocal();

    }
}
