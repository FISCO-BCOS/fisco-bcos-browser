package org.bcos.browser.bcos.browser.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	
	public static String NowDateInfo() {
		
		Date date=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
		String dateNowstr=sdf.format(date);
		return dateNowstr;
	}
     
     

}
