package cn.webank.bcos.browser.base.utils;

import org.apache.commons.lang.StringUtils;

import java.security.MessageDigest;

/**
 * MD5 encryption tool
 */
public class MD5Util {
    /**
     *Generate MD5
     * @param message
     * @return
     */
    public static String getMD5(String message) {
        if(StringUtils.isBlank(message)){
            return null;
        }
        String md5 = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageByte = message.getBytes("UTF-8");
            byte[] md5Byte = md.digest(messageByte);
            md5 = bytesToHex(md5Byte);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return md5;
    }


    /**
     *GeBinary to hexadecimal
     */
    private static String bytesToHex(byte[] bytes) {
        StringBuffer hexStr = new StringBuffer();
        int num;
        for (int i = 0; i < bytes.length; i++) {
            num = bytes[i];
            if(num < 0) {
                num += 256;
            }
            if(num < 16){
                hexStr.append("0");
            }
            hexStr.append(Integer.toHexString(num));
        }
        return hexStr.toString().toUpperCase();
    }
}
