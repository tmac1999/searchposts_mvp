package com.mrz.searchenginefortieba.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class TimeUtils {
    /**
     * @return "yy-MM-dd HH:mm:ss"
     */
    public static String getCurrentTime() {
        SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
        String time = format.format(new Date());
        return time;
    }

    /**
     * @param time 毫秒值
     * @return HH:mm:ss (排除时区因素之后的)
     */
    public static String formatTimeMillis(long time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        int rawOffset = TimeZone.getDefault().getRawOffset();
        long l = time - rawOffset;
        return simpleDateFormat.format(new Date(l));
    }
}
