package com.mrz.searchenginefortieba.component.communicate.SendPost;

import android.content.Intent;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.ProgressCallback;
import com.avos.avoscloud.SaveCallback;
import com.mrz.searchenginefortieba.data.SPRepository;
import com.mrz.searchenginefortieba.data.bean.Post;
import com.mrz.searchenginefortieba.service.AVService;

import java.io.IOException;

/**
 * Created by zhengpeng on 2016/5/30.
 */
public class SendPostPresenter implements SendPostContract.Presenter {
    private SendPostActivity sendPostActivity;

    public SendPostPresenter(SPRepository spRepository, SendPostActivity sendPostActivity) {
        this.sendPostActivity = sendPostActivity;

    }

    @Override
    public void choosePhoto() {
        Intent intent = new Intent();
                /* 开启Pictures画面Type设定为image */
        intent.setType("image/*");
                /* 使用Intent.ACTION_GET_CONTENT这个Action */
        intent.setAction(Intent.ACTION_GET_CONTENT);
                /* 取得相片后返回本画面 */
        sendPostActivity.startActivityForResult(intent, 1);
    }

    @Override
    public void sendPost(final Post post) {
        final SaveCallback sendPostCallback = new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    // 成功或失败处理...
                    sendPostActivity.showPostSuccessedUI();
                } else {
                    sendPostActivity.showPostFailedUI();
                    e.printStackTrace();
                }
            }
        };
        SaveCallback uploadIMGCallback = new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    // 成功或失败处理...
                  //  sendPostActivity.showPostSuccessedUI();
                    post.imgURL = avFile.getUrl();
                    AVService.sendPost(post, sendPostCallback);
                } else {
                    sendPostActivity.showPostFailedUI();
                    e.printStackTrace();
                }

            }
        };
        ProgressCallback uploadIMGProgressCallback = new ProgressCallback() {
            @Override
            public void done(Integer integer) {
                // 上传进度数据，integer 介于 0 和 100。
                sendPostActivity.showProgress(integer);
            }
        };

        if (post.imgAbsoluteLocalPath!=null){//有图片,图片上传完成后拿到url再发帖
            String imgName = post.imgAbsoluteLocalPath.substring(post.imgAbsoluteLocalPath.lastIndexOf("/")+1);
            uploadIMG(imgName, post.imgAbsoluteLocalPath,uploadIMGCallback,uploadIMGProgressCallback);
        }else{
            //没图片直接发
            AVService.sendPost(post, sendPostCallback);
        }



    }
    AVFile avFile = null;
    public void uploadIMG(String name, String imgAbsoluteLocalPath,SaveCallback uploadIMGCallback,ProgressCallback uploadIMGProgressCallback) {

        try {
            avFile = AVFile.withAbsoluteLocalPath(name, imgAbsoluteLocalPath);

        } catch (IOException e) {
            e.printStackTrace();
        }
        if (avFile != null) {
            avFile.saveInBackground(uploadIMGCallback,uploadIMGProgressCallback);
        }

    }
    @Override
    public void openPostFailedUI() {

    }

    @Override
    public void openPostSuccessedUI() {

    }

    @Override
    public void start() {

    }

}
