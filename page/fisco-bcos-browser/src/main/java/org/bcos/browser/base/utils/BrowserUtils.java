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
 * @file: BrowserUtils.java
 * @author: v_wbsqwu
 * @date: 2018
 */

package org.bcos.browser.base.utils;

import org.apache.commons.lang.StringUtils;

import java.util.regex.Pattern;


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
        if(IP != null && IP.matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}")){
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
