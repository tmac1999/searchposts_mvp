package com.mrz.searchenginefortieba.component.communicate.SendPost;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.mrz.searchenginefortieba.R;
import com.mrz.searchenginefortieba.component.BaseThemeActivity;
import com.mrz.searchenginefortieba.data.SPRepository;
import com.mrz.searchenginefortieba.data.bean.Post;
import com.mrz.searchenginefortieba.session.UserSession;
import com.mrz.searchenginefortieba.utils.FileUtils;
import com.mrz.searchenginefortieba.utils.TimeUtils;
import com.mrz.searchenginefortieba.utils.ToastUtils;

import java.io.FileNotFoundException;

public class SendPostActivity extends BaseThemeActivity implements View.OnClickListener, SendPostContract.View {


    private SendPostPresenter sendPostPresenter;
    // Content View Elements

    private EditText et_title;
    private EditText et_content;
    private Button btn_updateimg;
    private ImageView iv_updateimg;
    private Button btn_submitpost;
    private String realFilePath;


    // End Of Content View Elements

    private void bindViews() {

        et_title = (EditText) findViewById(R.id.et_title);
        et_content = (EditText) findViewById(R.id.et_content);
        btn_updateimg = (Button) findViewById(R.id.btn_updateimg);
        iv_updateimg = (ImageView) findViewById(R.id.iv_updateimg);
        btn_submitpost = (Button) findViewById(R.id.btn_submitpost);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_communicate);
        bindViews();
        btn_updateimg.setOnClickListener(this);
        btn_submitpost.setOnClickListener(this);
        sendPostPresenter = new SendPostPresenter(SPRepository.getInstance(null, null), this);
    }

    @Override
    public void showProgress(int progress) {
        ToastUtils.showSingletonToast("进度:" + progress + "%");
    }

    @Override
    public void setPresenter(SendPostContract.Presenter presenter) {

    }

    @Override
    public void showLoadingBar() {

    }

    @Override
    public void showNetWorkTips() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_updateimg:
                sendPostPresenter.choosePhoto();
                break;
            case R.id.btn_submitpost:
                Post post = new Post();
                post.content = et_content.getText().toString();
                post.title = et_title.getText().toString();
                post.time = TimeUtils.getCurrentTime();
                post.userID = UserSession.getUserID();
                post.userName = UserSession.getUserName();
                post.imgAbsoluteLocalPath = realFilePath;
                sendPostPresenter.sendPost(post);
                break;
        }
    }

    //    private void toChoosePhoto() {
//        Intent intent = new Intent(context, SelectAllPhotoActivity.class);
//        intent.putExtra("size", size);
//        startActivityForResult(intent, PARAM.PHOTO_PICKED_WITH_DATA);
//    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Uri uri = data.getData();
           // realFilePath = CommonUtils.getRealFilePath(SendPostActivity.this, uri);
            realFilePath = FileUtils.getPath(SendPostActivity.this, uri);
            Log.e("uri", uri.toString());
            ContentResolver cr = this.getContentResolver();
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                /* 将Bitmap设定到ImageView */
                iv_updateimg.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                Log.e("Exception", e.getMessage(), e);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void showPostFailedUI() {
        ToastUtils.longToast("发帖失败！");
    }

    @Override
    public void showPostSuccessedUI() {
        ToastUtils.longToast("发帖成功！请到交流版查看");
        finish();
    }
}
