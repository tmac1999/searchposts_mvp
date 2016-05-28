package com.mrz.searchposts.data;

/**
 * Created by zhengpeng on 2016/5/27.
 */
public class SPRepository {
    private <T,E>SPRepository(T mock,E remote){

    }
    public static SPRepository getInstance(Object mock,Object remote){
        return new SPRepository(mock,remote);
    }
}
