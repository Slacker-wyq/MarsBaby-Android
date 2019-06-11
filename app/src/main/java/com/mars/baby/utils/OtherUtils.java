package com.mars.baby.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class OtherUtils {
    public static String getDate(Calendar join) {
        Calendar curr = Calendar.getInstance();
        int day = curr.get(Calendar.DAY_OF_MONTH) - join.get(Calendar.DAY_OF_MONTH);

        int month = curr.get(Calendar.MONTH) - join.get(Calendar.MONTH);

        int year = curr.get(Calendar.YEAR) - join.get(Calendar.YEAR);

        if (day < 0) {

            month -= 1;
            curr.add(Calendar.MONTH, -1);
            day = day + curr.getActualMaximum(Calendar.DAY_OF_MONTH);
        }
        if (month < 0) {
            month = (month + 12) % 12;
            year--;
        }
        String dateStr = year + "岁" + month + "月" + day + "日";

        return dateStr;
    }

    public static String getTime(String join) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            Date date = sdf.parse(join);
            return sdf.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return "null";
        }
    }
}
