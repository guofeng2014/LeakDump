package com.leaklibrary.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by guofeng
 * on 2017/10/23.
 */

public class DateUtil {

    public static String dateFormatter(long timeMillion) {
        DateFormat format = SimpleDateFormat.getInstance();
        return format.format(new Date(timeMillion));
    }

}
