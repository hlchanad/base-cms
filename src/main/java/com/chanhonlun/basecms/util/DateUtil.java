package com.chanhonlun.basecms.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static final String DATE_FORMAT_1 = "yyyy-MM-dd";
    public static final String DATE_FORMAT_2 = "yyyy-MM-dd HH:mm:ss";

    public static String formatDate(Date date, String format) {
        return new SimpleDateFormat(format).format(date);
    }
}
