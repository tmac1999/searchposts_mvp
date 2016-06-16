package com.mrz.searchposts.component.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mrz.searchposts.R;
import com.mrz.searchposts.component.BaseThemeActivity;
import com.mrz.searchposts.component.register.RegisterActivity;
import com.mrz.searchposts.utils.ToastUtils;

import test.Injection;

public class LoginActivity extends BaseThemeActivity implements LoginContract.View, View.OnClickListener {

    private LoginPresenter loginPresenter;

    @Override
    public void showWrongPwd() {

    }

    @Override
    public void showLoginResult(LoginContract.LoginResult loginResult) {

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

    // End Of Content View Elements

    private void bindViews() {

        editText_userName = (EditText) findViewById(R.id.editText_userName);
        editText_userPassword = (EditText) findViewById(R.id.editText_userPassword);
        button_login = (Button) findViewById(R.id.button_login);
        button_register = (Button) findViewById(R.id.button_register);
        button_forget_password = (Button) findViewById(R.id.button_forget_password);
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
        }
    }
}
