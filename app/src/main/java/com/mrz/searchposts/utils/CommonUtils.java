package com.mrz.searchposts.utils;

import android.content.Context;

public class CommonUtils {
	public static int dp2px(Context context,int dpValue){
		float density = context.getResources().getDisplayMetrics().density;
		int px = (int) (dpValue * density + 0.5f);
		return px;
	}
}
