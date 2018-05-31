package org.bcos.browser.base.utils;

import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

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
 * @file: ProppertiesUtil.java
 * @author: v_wbsqwu
 * @date: 2018
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
