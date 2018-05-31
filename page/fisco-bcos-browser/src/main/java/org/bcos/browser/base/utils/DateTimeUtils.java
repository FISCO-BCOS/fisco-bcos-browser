/*
 * This file is part of FISCO BCOS Browser.
 *
 * FISCO BCOS Browser is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * FISCO BCOS Browser is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with FISCO BCOS Browser.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 * @file: DateTimeUtils.java
 * @author: v_wbsqwu
 * @date: 2018
 */

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


public class DateTimeUtils {
    private static Logger LOGGER =  LoggerFactory.getLogger(DateTimeUtils.class);

    /**
     * Date converted to a string
     */
    public static String date2String(Date date,String format){
        if(date==null){
            LOGGER.warn("date2String fail,date is null");
            return null;
        }
        if(StringUtils.isBlank(format)){
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
     * Timestamp converted to a string
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
     * String converted to Date
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
     * BigInteger converted to a String
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