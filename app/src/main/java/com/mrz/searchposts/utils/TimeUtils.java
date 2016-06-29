package com.mrz.searchposts.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils {
    /**
     *
     * @return "yy-MM-dd HH:mm:ss"
     */
	public static String getCurrentTime(){
		SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
		String time = format.format(new Date());
		return time;
	}
}
