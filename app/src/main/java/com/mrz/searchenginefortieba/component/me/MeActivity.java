package com.mrz.searchenginefortieba.component.me;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.avos.avoscloud.AVUser;
import com.mrz.searchenginefortieba.R;
import com.mrz.searchenginefortieba.component.BaseThemeActivity;

import test.Injection;

public class MeActivity extends BaseThemeActivity  implements MeContract.View,View.OnClickListener{
    // Content View Elements

    private TextView tv_username;
    private TextView tv_email;

    // End Of Content View Elements
    private MePresenter mePresenter;
    private AVUser currentUser;
    private Button userinfo_exit;

    private void bindViews() {

        tv_username = (TextView) findViewById(R.id.tv_username);
        tv_email = (TextView) findViewById(R.id.tv_email);
        userinfo_exit = (Button) findViewById(R.id.userinfo_exit);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me);
        bindViews();
        mePresenter = new MePresenter(Injection.provideTasksRepository(getApplicationContext()), this);
        userinfo_exit.setOnClickListener(this);
        refreshUI();
    }

    private void refreshUI() {
        currentUser = AVUser.getCurrentUser();
        if(currentUser !=null){
            tv_username.setText(currentUser.getUsername());
            tv_email.setText(currentUser.getEmail());
        }else {
            tv_username.setText("您尚未登录");
            tv_email.setVisibility(View.GONE);
            userinfo_exit.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshUI();
    }

    @Override
    public void showMe() {

    }

    @Override
    public void showRequestFailed() {

    }

    @Override
    public void setPresenter(MeContract.Presenter presenter) {

    }

    @Override
    public void showLoadingBar() {

    }

    @Override
    public void showNetWorkTips() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.userinfo_exit:
                AVUser.logOut();
                refreshUI();
                break;
        }
    }
}
