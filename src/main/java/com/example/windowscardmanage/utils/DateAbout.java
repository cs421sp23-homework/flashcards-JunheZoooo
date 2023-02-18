package com.example.windowscardmanage.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 常见的时间相关
 */
public class DateAbout {
    /**
     * 返回一定时间后的日期
     * @param date 开始计时的时间
     * @param year 增加的年
     * @param month 增加的月
     * @param day 增加的日
     * @param hour 增加的小时
     * @param minute 增加的分钟
     * @param second 增加的秒
     * @return 返回时间状态
     */
    public static Date getAfterDate(Date date, int year, int month, int day, int hour, int minute, int second) {
        if (date == null) {
            date = new Date();
        }

        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        if (year != 0) {
            cal.add(Calendar.YEAR,year);
        }
        if (month != 0) {
            cal.add(Calendar.MONTH,month);
        }
        if (day != 0) {
            cal.add(Calendar.DATE,day);
        }
        if (hour != 0) {
            cal.add(Calendar.HOUR,hour);
        }
        if (minute != 0) {
            cal.add(Calendar.MINUTE,minute);
        }
        if (second != 0) {
            cal.add(Calendar.SECOND,second);
        }
        return cal.getTime();
    }
}
