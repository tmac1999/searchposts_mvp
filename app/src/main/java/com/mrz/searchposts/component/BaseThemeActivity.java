package com.mrz.searchposts.component;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.mrz.searchposts.R;

/**
 * Created by zhengpeng on 2016/6/16.
 */
public class BaseThemeActivity extends FragmentActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.in_lefttoright,
                R.anim.out_lefttoright);
    }
    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.in_righttoleft, R.anim.out_lefttoright);
    }
}
