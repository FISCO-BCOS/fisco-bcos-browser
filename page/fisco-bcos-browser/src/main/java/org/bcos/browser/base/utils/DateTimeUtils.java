package org.bcos.browser.base.utils;

import org.bcos.browser.base.Constants;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by v_wbsqwu on 2017/9/29.
 * 日期转换工具类
 */
public class DateTimeUtils {
    private static Logger LOGGER =  LoggerFactory.getLogger(DateTimeUtils.class);

    /**
     * 日期转换成字符串
     */
    public static String date2String(Date date,String format){
        if(date==null){
            LOGGER.warn("date2String fail,date is null");
            return null;
        }
        if(StringUtils.isBlank(format)){
            //格式为空，用默认值
            LOGGER.warn("format is null,use default:{}", Constants.DEFAULT_DATA_TIME_FORMAT);
            format = Constants.DEFAULT_DATA_TIME_FORMAT;
        }

        DateFormat dateFormat = new SimpleDateFormat(format);
        String dateStr = null;
        try{
            dateStr = dateFormat.format(date);
        }catch (Exception ex){
            LOGGER.warn("date2String fail:{}",ex.getMessage());
        }

        return dateStr;
    }

    /**
     * 日期转换成字符串
     */
    public static String Timestamp2String(Timestamp time,String format){
        if(null == time){
            LOGGER.warn("Timestamp2String fail,time is null");
            return null;
        }
        if( StringUtils.isEmpty(format)){
            LOGGER.warn("format is null,use default:{}", Constants.DEFAULT_DATA_TIME_FORMAT);
            format = Constants.DEFAULT_DATA_TIME_FORMAT;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        String dateStr = null;
        try{
            dateStr = dateFormat.format(time);
        }catch (Exception ex){
            LOGGER.warn("Timestamp2String fail:{}",ex.getMessage());
        }

        return dateStr;
    }


    /**
     * 字符串转换成Date类型
     */
    public static  Date String2Date(String timeStr,String format)
    {
        if( StringUtils.isEmpty(timeStr)){
            LOGGER.warn("String2Date fail,timeStr is null");
            return null;
        }
        if( StringUtils.isEmpty(format)){
            LOGGER.warn("format is null,use default:{}", Constants.DEFAULT_DATA_TIME_FORMAT);
            format = Constants.DEFAULT_DATA_TIME_FORMAT;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = sdf.parse(timeStr);
        } catch (Exception ex) {
            LOGGER.warn("String2Date fail:{}",ex.getMessage());
        }

        return date;
    }

    /**
     * BigInteger转换成字符串
     */
    public static String BigInteger2String(BigInteger time,String format){
        if(null == time){
            LOGGER.warn("BigInteger2String fail,time is null");
            return null;
        }
        if( StringUtils.isEmpty(format)){
            LOGGER.warn("format is null,use default:{}", Constants.DEFAULT_DATA_TIME_FORMAT);
            format = Constants.DEFAULT_DATA_TIME_FORMAT;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String dateStr = null;
        try {
            dateStr = sdf.format(time);
        } catch (Exception ex) {
            LOGGER.warn("BigInteger2String fail:{}",ex.getMessage());
        }

        return dateStr;
    }

}
