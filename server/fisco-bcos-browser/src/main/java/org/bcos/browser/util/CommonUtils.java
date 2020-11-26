package org.bcos.browser.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.bcos.browser.base.ConstantCode;
import org.bcos.browser.base.exception.BaseException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@Slf4j
public class CommonUtils {
    
    static final String STR_0X = "0x";
    
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
        String jsonStr = JsonTools.toJSONString(obj);
        return JsonTools.toJavaObject(jsonStr, clazz);
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
        // adapt fisco 2.7.0
        if (str.startsWith(STR_0X)) {
            return Integer.parseInt(str.substring(2), 16);
        }
        return Integer.parseInt(str);
    }

    /**
     * parseStr2HexStr.
     * 
     * @param value int
     * @return
     */
    public static String parseInt2HexStr(int value) {
        String result = STR_0X + Integer.toHexString(value);
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

    /**
     *
     * @param zipFile
     * @throws BaseException
     */
    public static void preTreatmentFile(ZipFile zipFile) throws BaseException {
        for (Enumeration entries = zipFile.entries(); entries.hasMoreElements(); ) {
            ZipEntry zipEntry = (ZipEntry) entries.nextElement();
            String zipEntryName = zipEntry.getName();
            if (zipEntryName.startsWith("__MACOSX")){
                continue;
            }
            if (zipEntryName.endsWith(".zip")) {
                throw new BaseException(ConstantCode.DO_NOT_ALL_ZIP_FILE);
            }
            if (zipEntryName.contains(File.separator)) {
                String[] strings = zipEntryName.split(File.separator);
                if (strings.length > 2 ) {
                    throw new BaseException(ConstantCode.FOLDERS_ARE_NOT_ALLOWED);
                }
            }
        }
    }

    /**
     * readZipFile.
     *
     * @return
     */
    public static String readZipFile(ZipEntry zipEntry, ZipFile zipFile) throws IOException {
        long size = zipEntry.getSize();
        StringBuilder strBuilder = new StringBuilder();
        if (size > 0) {
            InputStream inputStream = zipFile.getInputStream(zipEntry);
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = br.readLine()) != null) {
                strBuilder.append(line).append("\n");
                }
            br.close();
        }
        return strBuilder.toString();
    }

}
