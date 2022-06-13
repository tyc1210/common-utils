package com.tyc.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 类描述
 *
 * @author tyc
 * @version 1.0
 * @date 2022-06-09 17:28:30
 */
public class DateUtil {

    public static String dataToString(LocalDateTime date){
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public static String dataToString(Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(date);
    }

    public static Date stringToDate(String date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * LocalDateTime 转时间戳
     */
    public static Long getTimeStamp(LocalDateTime localDate){
        return localDate.toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
    }

    /**
     * LocalDate 转时间戳
     */
    public static Long getTimeStamp(LocalDate localDate){
        return localDate.atStartOfDay(ZoneOffset.ofHours(8)).toInstant().toEpochMilli();
    }

    /**
     * LocalDate转Date
     */
    public static Date LocalDateToDate(LocalDate localDate){
        return Date.from(localDate.atStartOfDay(ZoneOffset.ofHours(8)).toInstant());
    }

    /**
     * LocalDateTime转Date
     */
    public static Date LocalDateToDate(LocalDateTime localDateTime){
        return Date.from(localDateTime.atZone(ZoneOffset.ofHours(8)).toInstant());
    }

    /**
     * 获取到今天结束还剩多少秒
     */
    public static Long getLeftSecondOfToday(){
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime end = LocalDateTime.of(now.toLocalDate(), LocalTime.MAX);
        return (end.toEpochSecond(ZoneOffset.of("+8")) - now.toEpochSecond(ZoneOffset.of("+8")));
    }

}
