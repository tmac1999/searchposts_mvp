package com.mrz.searchposts.component.communicate.SubjectList;

import android.os.Bundle;

import com.mrz.searchposts.R;
import com.mrz.searchposts.component.BaseThemeActivity;
import com.mrz.searchposts.data.SPRepository;
import com.mrz.searchposts.utils.ToastUtils;
import com.mrz.searchposts.view.catloadingview.CatLoadingView;

public class SubjectListActivity extends BaseThemeActivity implements SubjectListContract.View{

    private CatLoadingView catLoadingView;

    @Override
    public void showList(SubjectListAdapter adapter) {
        xlv_postlist.setAdapter(adapter);
    }


    @Override
    public void showRequestSuccessedUI() {
        catLoadingView.dismiss();
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
        catLoadingView.show(getSupportFragmentManager(),"");
    }

    @Override
    public void showNetWorkTips() {

    }
    // Content View Elements

    private com.mrz.searchposts.view.XListView xlv_postlist;

    // End Of Content View Elements

    private void bindViews() {

        xlv_postlist = (com.mrz.searchposts.view.XListView) findViewById(R.id.xlv_postlist);
    }

    SubjectListPresenter subjectListPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_list);
        bindViews();
        subjectListPresenter = new SubjectListPresenter(SPRepository.getInstance(null, null), this);
        subjectListPresenter.getListFromNet();
    }
}
