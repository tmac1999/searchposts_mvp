package com.mrz.searchposts.component.me;

import android.os.Bundle;
import android.widget.TextView;

import com.mrz.searchposts.R;
import com.mrz.searchposts.component.BaseThemeActivity;

public class MeActivity extends BaseThemeActivity  implements MeContract.View{
    // Content View Elements

    private TextView tv_username;
    private TextView tv_email;

    // End Of Content View Elements

    private void bindViews() {

        tv_username = (TextView) findViewById(R.id.tv_username);
        tv_email = (TextView) findViewById(R.id.tv_email);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me);
        bindViews();
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
}
