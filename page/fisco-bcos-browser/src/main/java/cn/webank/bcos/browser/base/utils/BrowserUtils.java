package cn.webank.bcos.browser.base.utils;

import org.apache.commons.lang.StringUtils;

import java.util.regex.Pattern;

/**
 * @Description:common utils
 * @Date: 2018/2/5 17:27
 */
public class BrowserUtils {

    /**
     *@Description: Remove all Spaces before and after the string.
     */
    public static String trimSpaces(String str){
        if(StringUtils.isBlank(str)){
            return null;
        }
        while(str.startsWith(" ")){
            str= str.substring(1,str.length()).trim();
        }
        while(str.endsWith(" ")){
            str= str.substring(0,str.length()-1).trim();
        }
        return str;
    }

    /**
     *@Description: Determine if the string is ip
     */
    public static boolean isIp(String IP){
        boolean isIpFlag = false;
        if(StringUtils.isBlank(IP)){
            return isIpFlag;
        }
        IP = trimSpaces(IP);
        if(IP.matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}")){
            String s[] = IP.split("\\.");
            if(Integer.parseInt(s[0])<255)
                if(Integer.parseInt(s[1])<255)
                    if(Integer.parseInt(s[2])<255)
                        if(Integer.parseInt(s[3])<255)
                            isIpFlag = true;
        }
        return isIpFlag;
    }


    /**
     *@Description: Determine if the string is a number
     * @param str Incoming string
     * @return Is an integer that returns true, otherwise it returns false
     */
    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }
}
