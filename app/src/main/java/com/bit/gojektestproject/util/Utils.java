package com.bit.gojektestproject.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Utils {
    public static Date convertStringToDate(String pattern, String date) {
        try {
            TimeZone timeZone = TimeZone.getTimeZone("UTC");
            SimpleDateFormat fromPatternFormat = new SimpleDateFormat(pattern, Locale.US);
            fromPatternFormat.setTimeZone(timeZone);
            return fromPatternFormat.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
