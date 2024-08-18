package com.example.multitool.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DataUtil {
    private static final String TIME_ZONE = "Asia/Tokyo";
    public static String getCurrentDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        sdf.setTimeZone(TimeZone.getTimeZone(TIME_ZONE));
        return sdf.format(new Date());
    }
}
