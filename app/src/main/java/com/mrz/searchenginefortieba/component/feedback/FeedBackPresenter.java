package com.mrz.searchenginefortieba.component.feedback;

import android.os.Build;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.SaveCallback;
import com.mrz.searchenginefortieba.data.SPRepository;
import com.mrz.searchenginefortieba.service.AVService;
import com.mrz.searchenginefortieba.session.UserSession;

/**
 * Created by zhengpeng on 2016/5/30.
 */
public class FeedBackPresenter implements FeedBackContract.Presenter {
    private FeedBackActivity feedBackActivity;

    public FeedBackPresenter(SPRepository spRepository, FeedBackActivity feedBackActivity) {
        this.feedBackActivity = feedBackActivity;

    }

    @Override
    public void openFeedBackSuccessUI() {

    }

    @Override
    public void openFeedBackFailedUI() {

    }

    @Override
    public void doFeedBack(String feedBackContent) {
        String brand = Build.BRAND;
        String device = Build.DEVICE;
        String model = Build.MODEL;
        String product = Build.PRODUCT;
        int sdk = Build.VERSION.SDK_INT;
        String cpuAbi = Build.CPU_ABI;
        String board = Build.BOARD;
        String cpuAbi2 = Build.CPU_ABI2;

        String devicename = "brand="+brand+";device="+device+";model="+model+";product="+product+";sdk="+sdk+";cpuAbi="+cpuAbi+";board="+board+";cpuAbi2="+cpuAbi2;
        SaveCallback saveCallback = new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    feedBackActivity.showFeedBackSuccessUI();
                } else {
                    feedBackActivity.showFeedBackFailedUI();
                    e.printStackTrace();
                }
            }
        };
        String userID = UserSession.getUserID();

        AVService.createAdvice(userID, devicename,feedBackContent, saveCallback);
    }

    @Override
    public void start() {

    }


}
