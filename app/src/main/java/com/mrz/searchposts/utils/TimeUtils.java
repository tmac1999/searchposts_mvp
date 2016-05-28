package com.mrz.searchposts.utils;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.text.format.Formatter;

public class TimeUtils {
	public static String getCurrentTime(){
		SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
		String time = format.format(new Date());
		return time;
	}
}
