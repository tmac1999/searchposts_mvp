package com.mrz.searchposts.component.communicate.SendPost;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.mrz.searchposts.R;

public class SendPostActivity extends Activity implements View.OnClickListener,CommunicateContract.View{
    // Content View Elements

    private EditText et_title;
    private EditText et_content;
    private Button btn_updateimg;
    private ImageView iv_updateimg;

    // End Of Content View Elements

    private void bindViews() {

        et_title = (EditText) findViewById(R.id.et_title);
        et_content = (EditText) findViewById(R.id.et_content);
        btn_updateimg = (Button) findViewById(R.id.btn_updateimg);
        iv_updateimg = (ImageView) findViewById(R.id.iv_updateimg);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_communicate);
        bindViews();
        btn_updateimg.setOnClickListener(this);
    }

    @Override
    public void showFeedBackSuccessUI() {

    }

    @Override
    public void showFeedBackFailedUI() {

    }

    @Override
    public void setPresenter(CommunicateContract.Presenter presenter) {

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
            case R.id.btn_updateimg:

                break;
        }
    }
//    private void toChoosePhoto() {
//        Intent intent = new Intent(context, SelectAllPhotoActivity.class);
//        intent.putExtra("size", size);
//        startActivityForResult(intent, PARAM.PHOTO_PICKED_WITH_DATA);
//    }
}
