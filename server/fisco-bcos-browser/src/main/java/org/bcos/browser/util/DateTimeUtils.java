package org.bcos.browser.util;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.bcos.browser.base.Constants;

@Slf4j
public class DateTimeUtils {

    /**
     * Date converted to a string.
     */
    public static String date2String(Date date, String format) {
        if (date == null) {
            log.warn("date2String fail,date is null");
            return null;
        }
        if (StringUtils.isBlank(format)) {
            log.warn("format is null,use default:{}", Constants.DEFAULT_DATA_TIME_FORMAT);
            format = Constants.DEFAULT_DATA_TIME_FORMAT;
        }

        DateFormat dateFormat = new SimpleDateFormat(format);
        String dateStr = null;
        try {
            dateStr = dateFormat.format(date);
        } catch (Exception ex) {
            log.warn("date2String fail:{}", ex.getMessage());
        }

        return dateStr;
    }

    /**
     * Timestamp converted to a string.
     */
    public static String timestamp2String(Timestamp time, String format) {
        if (null == time) {
            log.warn("Timestamp2String fail,time is null");
            return null;
        }
        if (StringUtils.isEmpty(format)) {
            log.warn("format is null,use default:{}", Constants.DEFAULT_DATA_TIME_FORMAT);
            format = Constants.DEFAULT_DATA_TIME_FORMAT;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        String dateStr = null;
        try {
            dateStr = dateFormat.format(time);
        } catch (Exception ex) {
            log.warn("Timestamp2String fail:{}", ex.getMessage());
        }

        return dateStr;
    }

    /**
     * String converted to Date.
     */
    public static Date string2Date(String timeStr, String format) {
        if (StringUtils.isEmpty(timeStr)) {
            log.warn("String2Date fail,timeStr is null");
            return null;
        }
        if (StringUtils.isEmpty(format)) {
            log.warn("format is null,use default:{}", Constants.DEFAULT_DATA_TIME_FORMAT);
            format = Constants.DEFAULT_DATA_TIME_FORMAT;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = sdf.parse(timeStr);
        } catch (Exception ex) {
            log.warn("String2Date fail:{}", ex.getMessage());
        }

        return date;
    }

    /**
     * BigInteger converted to a String.
     */
    public static String bigInteger2String(BigInteger time, String format) {
        if (null == time) {
            log.warn("BigInteger2String fail,time is null");
            return null;
        }
        if (StringUtils.isEmpty(format)) {
            log.warn("format is null,use default:{}", Constants.DEFAULT_DATA_TIME_FORMAT);
            format = Constants.DEFAULT_DATA_TIME_FORMAT;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String dateStr = null;
        try {
            dateStr = sdf.format(time);
        } catch (Exception ex) {
            log.warn("BigInteger2String fail:{}", ex.getMessage());
        }

        return dateStr;
    }
}
