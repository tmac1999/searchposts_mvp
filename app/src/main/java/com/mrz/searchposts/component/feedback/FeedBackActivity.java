package com.mrz.searchposts.component.feedback;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.mrz.searchposts.R;
import com.mrz.searchposts.utils.ToastUtils;

import test.Injection;

public class FeedBackActivity extends Activity implements View.OnClickListener,FeedBackContract.View{
    // Content View Elements

    private EditText editText_about_app_user_input;
    private Button button_about_app_submit_user_input;
    private ListView listView_user_back;
    private FeedBackPresenter feedBackPresenter;
    // End Of Content View Elements

    private void bindViews() {

        editText_about_app_user_input = (EditText) findViewById(R.id.editText_about_app_user_input);
        button_about_app_submit_user_input = (Button) findViewById(R.id.button_about_app_submit_user_input);
        listView_user_back = (ListView) findViewById(R.id.listView_user_back);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);
        bindViews();
        button_about_app_submit_user_input.setOnClickListener(this);
        feedBackPresenter = new FeedBackPresenter(Injection.provideTasksRepository(getApplicationContext()), this);
    }

    @Override
    public void showFeedBackSuccessUI() {
        ToastUtils.longToast("提交成功！");
    }

    @Override
    public void showFeedBackFailedUI() {
        ToastUtils.longToast("提交失败！");
    }

    @Override
    public void setPresenter(FeedBackContract.Presenter presenter) {

    }

    @Override
    public void showLoadingBar() {

    }

    @Override
    public void showNetWorkTips() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_about_app_submit_user_input:
                String input =editText_about_app_user_input.getText().toString();
                feedBackPresenter.doFeedBack(input);

                break;
        }
    }
}
