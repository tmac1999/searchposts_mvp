package com.mrz.searchposts.component.register;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mrz.searchposts.R;
import com.mrz.searchposts.component.BaseThemeActivity;
import com.mrz.searchposts.utils.ToastUtils;
import com.mrz.searchposts.view.catloadingview.CatLoadingView;

import test.Injection;

public class RegisterActivity extends BaseThemeActivity implements RegisterContract.View, View.OnClickListener {
    // Content View Elements

    private EditText editText_register_userName;
    private EditText editText_register_email;
    private EditText editText_register_userPassword;
    private EditText editText_register_userPassword_again;
    private Button button_i_need_register;

    // End Of Content View Elements

    private void bindViews() {

        editText_register_userName = (EditText) findViewById(R.id.editText_register_userName);
        editText_register_email = (EditText) findViewById(R.id.editText_register_email);
        editText_register_userPassword = (EditText) findViewById(R.id.editText_register_userPassword);
        editText_register_userPassword_again = (EditText) findViewById(R.id.editText_register_userPassword_again);
        button_i_need_register = (Button) findViewById(R.id.button_i_need_register);
    }

    RegisterPresenter registerPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        bindViews();
        button_i_need_register.setOnClickListener(this);
        registerPresenter = new RegisterPresenter(Injection.provideTasksRepository(getApplicationContext()), this);
    }

    @Override
    public void showRegisterTips(RegisterContract.RegisterResult result) {
        switch (result){
            case USERNAME_EXIST:
                ToastUtils.longToast(getString(R.string.error_register_user_name_repeat));
                break;
            case EMAIL_TAKEN:
                ToastUtils.longToast(getString(R.string.error_register_email_repeat));
                break;
            case ERROR:
                ToastUtils.longToast(getString(R.string.network_error));
                break;
        }
    }

    @Override
    public void showRegisterSuccessedUI() {
        new AlertDialog.Builder(this)
                .setTitle(
                        this.getResources().getString(
                                R.string.dialog_message_title))
                .setMessage(
                        this.getResources().getString(
                                R.string.success_register_success))
                .setNegativeButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                dialog.dismiss();
                            }
                        }).show();
        finish();
    }


    @Override
    public void showRegisterFailedUI() {

    }

    @Override
    public void setPresenter(RegisterContract.Presenter presenter) {

    }

    @Override
    public void showLoadingBar() {

    }

    @Override
    public void showNetWorkTips() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_i_need_register:
                if (editText_register_userPassword.getText().toString()
                        .equals(editText_register_userPassword_again.getText().toString())) {
                    if (!editText_register_userPassword.getText().toString().isEmpty()) {
                        if (!editText_register_userName.getText().toString().isEmpty()) {
                            if (!editText_register_email.getText().toString().isEmpty()) {
                                progressDialogShow();
                                String username = editText_register_userName.getText().toString();
                                String password = editText_register_userPassword.getText().toString();
                                String email = editText_register_email.getText().toString();
                                registerPresenter.register(username, password, email);
                            } else {
                                ToastUtils.showSingletonToast(
                                        getString(R.string.error_register_email_address_null));
                            }
                        } else {
                            ToastUtils.showSingletonToast(getString(R.string.error_register_user_name_null));
                        }
                    } else {
                        ToastUtils.showSingletonToast(getString(R.string.error_register_password_null));
                    }
                } else {
                    ToastUtils.showSingletonToast(getString(R.string.error_register_password_not_equals));
                }
                break;
        }
    }

    CatLoadingView catLoadingView;

    private void progressDialogShow() {
        catLoadingView = new CatLoadingView();
        catLoadingView.show(getSupportFragmentManager(), "");
    }

    public void progressDialogDismiss() {
        catLoadingView.dismiss();
    }
}
