package com.example.multitool.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
public class DateUtil {
    public static String getCurrentDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return sdf.format(new Date());
    }
}
