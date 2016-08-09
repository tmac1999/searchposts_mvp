package com.mrz.searchenginefortieba.component.communicate.SubjectList;

import android.os.Bundle;

import com.mrz.searchenginefortieba.R;
import com.mrz.searchenginefortieba.component.BaseThemeActivity;
import com.mrz.searchenginefortieba.data.SPRepository;
import com.mrz.searchenginefortieba.utils.TimeUtils;
import com.mrz.searchenginefortieba.utils.ToastUtils;
import com.mrz.searchenginefortieba.view.XListView;
import com.mrz.searchenginefortieba.view.catloadingview.CatLoadingView;

public class SubjectListActivity extends BaseThemeActivity implements SubjectListContract.View {

    private CatLoadingView catLoadingView;

    private SubjectListContract.RequestType requestType ;
    @Override
    public void showList(SubjectListAdapter adapter) {
        xlv_postlist.setAdapter(adapter);
        if (requestType == SubjectListContract.RequestType.REFRESH) {

        } else {
            xlv_postlist.setSelection(adapter.getCount());
        }


    }


    @Override
    public void showRequestSuccessedUI() {

        catLoadingView.dismiss();
        if (requestType == SubjectListContract.RequestType.REFRESH) {//说明是刷新
            xlv_postlist.setRefreshTime(TimeUtils.getCurrentTime());
            xlv_postlist.stopRefresh();
        } else {//说明是请求更多
            xlv_postlist.stopLoadMore();
            xlv_postlist.setFootTextView("");
        }

    }

    @Override
    public void showRequestFailedUI() {
        ToastUtils.longToast("网络异常");
    }

    @Override
    public void setPresenter(SubjectListContract.Presenter presenter) {

    }

    @Override
    public void showLoadingBar() {
        catLoadingView = new CatLoadingView();
        catLoadingView.show(getSupportFragmentManager(), "");
    }

    @Override
    public void showNetWorkTips() {

    }
    // Content View Elements

    private com.mrz.searchenginefortieba.view.XListView xlv_postlist;

    // End Of Content View Elements

    private void bindViews() {

        xlv_postlist = (com.mrz.searchenginefortieba.view.XListView) findViewById(R.id.xlv_postlist);
    }


    SubjectListPresenter subjectListPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_list);
        bindViews();
        subjectListPresenter = new SubjectListPresenter(SPRepository.getInstance(null, null), this);
        xlv_postlist.setPullLoadEnable(true);
        requestType = SubjectListContract.RequestType.REFRESH;
        subjectListPresenter.getListFromNet(requestType);
        xlv_postlist.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                requestType = SubjectListContract.RequestType.REFRESH;
                subjectListPresenter.getListFromNet(requestType);
            }

            @Override
            public void onLoadMore() {
                requestType = SubjectListContract.RequestType.MORE;
                subjectListPresenter.getListFromNet(requestType);
            }
        });
    }
}
