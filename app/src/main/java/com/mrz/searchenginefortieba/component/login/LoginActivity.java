package com.mrz.searchenginefortieba.component.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVUser;
import com.mrz.searchenginefortieba.R;
import com.mrz.searchenginefortieba.component.BaseThemeActivity;
import com.mrz.searchenginefortieba.component.register.RegisterActivity;
import com.mrz.searchenginefortieba.utils.ToastUtils;

import test.Injection;

public class LoginActivity extends BaseThemeActivity implements LoginContract.View, View.OnClickListener {

    private LoginPresenter loginPresenter;

    @Override
    public void showWrongPwd() {

    }

    @Override
    public void showLoginResult(LoginContract.LoginResult loginResult, int resultCode) {
        switch (loginResult) {
            case ERROR:
                ToastUtils.longToast("服务器异常，请稍后再试:" + resultCode);
                break;
            case EMPTYPARAM:
                ToastUtils.longToast("用户名或者密码不能为空");
                break;
            case NONEEXISTENT_USER:
                ToastUtils.longToast("用户不存在");
                break;
            case WRONGPWD:
                ToastUtils.longToast("密码错误");
                break;


        }
    }

    @Override
    public void showRegisterUI() {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    @Override
    public void showLoginFinishedUI() {
        finish();
        ToastUtils.longToast("登录成功");
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {

    }

    @Override
    public void showLoadingBar() {

    }

    @Override
    public void showNetWorkTips() {

    }

    // Content View Elements

    private EditText editText_userName;
    private EditText editText_userPassword;
    private Button button_login;
    private Button button_register;
    private Button button_forget_password;
    private TextView tv_already_login;
    private RelativeLayout rl_login;
    // End Of Content View Elements

    private void bindViews() {

        editText_userName = (EditText) findViewById(R.id.editText_userName);
        editText_userPassword = (EditText) findViewById(R.id.editText_userPassword);
        button_login = (Button) findViewById(R.id.button_login);
        button_register = (Button) findViewById(R.id.button_register);
        button_forget_password = (Button) findViewById(R.id.button_forget_password);
        rl_login = (RelativeLayout) findViewById(R.id.rl_login);
        tv_already_login = (TextView) findViewById(R.id.tv_already_login);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bindViews();
        button_login.setOnClickListener(this);
        button_register.setOnClickListener(this);
        button_forget_password.setOnClickListener(this);
        loginPresenter = new LoginPresenter(Injection.provideTasksRepository(getApplicationContext()), this);
        refreshUI();
    }

    private void refreshUI() {
        AVUser currentUser = AVUser.getCurrentUser();

        if (currentUser == null) {
            rl_login.setVisibility(View.VISIBLE);
            tv_already_login.setVisibility(View.GONE);
        } else {
            rl_login.setVisibility(View.GONE);
            tv_already_login.setVisibility(View.VISIBLE);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshUI();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_login:
                String username = editText_userName.getText().toString();
                String psw = editText_userPassword.getText().toString();
                loginPresenter.login(username, psw);
                break;
            case R.id.button_register:
                loginPresenter.openRegisterUI();
                break;
            case R.id.button_forget_password:
                loginPresenter.openForgetPwdUI();
                break;
        }
    }
}
