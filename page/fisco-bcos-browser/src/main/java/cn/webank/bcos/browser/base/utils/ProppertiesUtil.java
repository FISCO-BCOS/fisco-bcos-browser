package cn.webank.bcos.browser.base.utils;

import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

/***
 * Properties configuration file operation class
 *
 */
public class ProppertiesUtil {
	private static Map<String,String> map= new HashMap<String,String>();
	private static final String PROPERTIES_JDBC_URL_NAME = "jdbc_url";

	static{
		Properties props = SpringContextUtil.getApplicationContext().getBean("jdbc", Properties.class);
		Iterator<Entry<Object, Object>> it=props.entrySet().iterator();
		while(it.hasNext()){
			Entry<Object, Object> entry = it.next();
			String key = (String) entry.getKey();
			String value = (String) entry.getValue();
			map.put(key, value);
		}
	}

	/***
	 * Get a string based on the key
	 * @throws Exception
	 */
	public static String getString(String key) {
		return map.get(key);
	}


	/**
	 *@Description: Get the database name
	 */
	public static String getDbName(){
		String jdbcUrl = getString(PROPERTIES_JDBC_URL_NAME);//Get jdbc connection path
		if(StringUtils.isBlank(jdbcUrl)){
			return null;
		}
		String dbName = jdbcUrl.substring(jdbcUrl.lastIndexOf("/")+1);
		return dbName;
	}
}
