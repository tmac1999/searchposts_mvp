package com.mrz.searchposts.component.me;

import com.mrz.searchposts.data.SPRepository;

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

    }

    @Override
    public void start() {

    }
}
