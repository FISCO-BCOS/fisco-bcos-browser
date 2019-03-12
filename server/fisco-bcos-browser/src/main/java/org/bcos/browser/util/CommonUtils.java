package org.bcos.browser.util;

import com.alibaba.fastjson.JSON;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@Slf4j
public class CommonUtils {
    /**
     * Remove all Spaces before and after the string.
     */
    public static String trimSpaces(String str) {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        while (str.startsWith(" ")) {
            str = str.substring(1, str.length()).trim();
        }
        while (str.endsWith(" ")) {
            str = str.substring(0, str.length() - 1).trim();
        }
        return str;
    }

    /**
     * Determine if the string is ip.
     */
    public static boolean isIp(String ip) {
        boolean isIpFlag = false;
        if (StringUtils.isBlank(ip)) {
            return isIpFlag;
        }
        ip = trimSpaces(ip);
        if (ip != null && ip.matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}")) {
            String[] s = ip.split("\\.");
            if (Integer.parseInt(s[0]) < 255 && Integer.parseInt(s[1]) < 255 
                    && Integer.parseInt(s[2]) < 255 && Integer.parseInt(s[3]) < 255) {
                isIpFlag = true;
            }
        }
        return isIpFlag;
    }


    /**
     * Determine if the string is a number.
     * @param str Incoming string
     * @return Is an integer that returns true, otherwise it returns false
     */
    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    /**
     * buildHeaders.
     * 
     * @return
     */
    public static HttpHeaders buildHeaders() {
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        return headers;
    }

    /**
     * Object to JavaBean.
     * 
     * @param obj obj
     * @param clazz clazz
     * @return
     */
    public static <T> T object2JavaBean(Object obj, Class<T> clazz) {
        if (obj == null || clazz == null) {
            log.warn("Object2JavaBean. obj or clazz null");
            return null;
        }
        String jsonStr = JSON.toJSONString(obj);
        return JSON.parseObject(jsonStr, clazz);
    }

    /**
     * parseHexStr2Int.
     * 
     * @param str str
     * @return
     */
    public static int parseHexStr2Int(String str) {
        if (StringUtils.isBlank(str)) {
            return 0;
        }
        return Integer.parseInt(str.substring(2), 16);
    }

    /**
     * parseStr2HexStr.
     * 
     * @param value int
     * @return
     */
    public static String parseInt2HexStr(int value) {
        String result = "0x" + Integer.toHexString(value);
        return result;
    }
    
    /**
     * getDateDescStr.
     * 
     * @return
     */
    public static String getDateDescStr() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateDescStr = sdf.format(date);
        return dateDescStr;
    }
}
