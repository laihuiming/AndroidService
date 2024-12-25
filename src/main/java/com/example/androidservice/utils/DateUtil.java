
package com.example.androidservice.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 @author: laihuiming
 @date: 2023/4/4
 @version: 1.0
 @description: 时间工具类
 **/
public class DateUtil {
    public static ThreadLocal<SimpleDateFormat> datetimeFormat = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };
    /** 数据库存储的时间格式串，如yyyyMMddHHMiSS */
    public static final String DB_STORE_DATE_FULL = "yyyyMMddHHmmss";

    public static String getCurrentTime() {
        Date date = new Date();
        return getDateStr(date, DB_STORE_DATE_FULL);
    }

    private static String getDateStr(Date date, String formatStr) {
        SimpleDateFormat fomate = new SimpleDateFormat(formatStr);
        return fomate.format(date);
    }

    /**
     * 日期转换为字符串
     *
     * @param date     日期
     * @param date_sdf 日期格式
     * @return 字符串
     */
    public static String date2Str(Date date, SimpleDateFormat date_sdf) {
        if (null == date) {
            return null;
        }
        return date_sdf.format(date);
    }

    public static Date str2Date(String dateTime, String formatStr) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        Date date = format.parse(dateTime);
        return date;
    }

    public static String getAgeByBirth(String birth){
        int age = 0;
        try {
            //获取当前时间
            Calendar cal = Calendar.getInstance();
            //取出当前年
            int nowYear = cal.get(Calendar.YEAR);
            //获取当前月份,1月份为0,所以需要+1
            int nowMonth = cal.get(Calendar.MONTH) + 1;
            int birthYear = Integer.parseInt(birth.substring(0,4));
            int birthMonth = Integer.parseInt(birth.substring(4));
            //计算年份
            age = nowYear - birthYear;
            //计算月份和日，看看是否大于当前月日，如果小于则减去一岁
//        if(nowMonth < birthMonth || (nowMonth == birthMonth && nowDay < birthDay)){
//            age--;
//        }
            if(nowMonth < birthMonth){
                age--;
            }
            if (age < 0){
                age = 0;
            }
        } catch (Exception e) {
            age = 0;
        }
        return age+"";
    }
}
