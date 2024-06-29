package com.example.multitool.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
public class DateUtils {
    public static String getCurrentDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return sdf.format(new Date());
    }
}
