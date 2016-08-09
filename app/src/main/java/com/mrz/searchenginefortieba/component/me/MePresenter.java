package com.mrz.searchenginefortieba.component.me;

import com.avos.avoscloud.AVUser;
import com.mrz.searchenginefortieba.data.SPRepository;

/**
 * Created by zhengpeng on 2016/5/27.
 */
public class MePresenter implements MeContract.Presenter{
    private MeActivity meActivity;
    public MePresenter(SPRepository spRepository, MeActivity meActivity){
        this.meActivity = meActivity;

    }

    @Override
    public void getMeData() {
        String username = AVUser.getCurrentUser().getUsername();

    }

    @Override
    public void start() {

    }
}
