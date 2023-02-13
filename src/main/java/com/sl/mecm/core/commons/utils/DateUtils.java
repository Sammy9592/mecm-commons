package com.sl.mecm.core.commons.utils;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    private DateUtils(){}

    public static Date getNow(){
        return new Date();
    }

    public static Date getDateAfterSeconds(int seconds){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, seconds);
        return calendar.getTime();
    }
}
