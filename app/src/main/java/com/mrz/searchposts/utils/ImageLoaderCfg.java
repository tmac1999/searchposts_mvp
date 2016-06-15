package com.mrz.searchposts.utils;

import com.mrz.searchposts.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;

/**
 * Created by zhengpeng on 2016/6/12.
 */
public class ImageLoaderCfg {
    public static DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showImageOnFail(R.drawable.ic_launcher).cacheInMemory(true)
            .cacheOnDisc(true)
            .displayer(new SimpleBitmapDisplayer()).build();
}
