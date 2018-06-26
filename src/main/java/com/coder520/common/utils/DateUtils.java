package com.coder520.common.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Create By Zhang on 2017/11/11
 */
public class DateUtils {
    private  static Calendar calendar=Calendar.getInstance();

    /**
     * @Author Zhang
     * @Date 2017/11/11 13:15
     * @Description  得到今天是周几 时间差
     */
    public static int getTodayWeek(){

//        获取最新时间
        calendar.setTime(new Date());
//        获取今天是周几(一周的第一天是周日)
        int week=calendar.get(Calendar.DAY_OF_WEEK)-1;
        if(week<0) week=7;
        return week;
    }

    public static int getMinite(Date startDate,Date endDate){
//        获取开始时间和结束时间的毫秒值
        long start=startDate.getTime();
        long end=endDate.getTime();

        int minite= (int)(end-start)/(1000*60);
        return minite;
    }

    /**
     * @Author Zhang
     * @Date 2017/11/12 23:18
     * @Description     获取当天时间
     */
    public  static Date getDate(int hour,int minite){
        calendar.set(Calendar.HOUR_OF_DAY,hour);
        calendar.set(Calendar.MINUTE,minite);
        return calendar.getTime();
    }
}
