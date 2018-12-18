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
 * @file: Main.java
 * @author: v_sfqiliu
 * @date: 2018
 */

package cn.bcos.browser.service;

import java.util.List;

import org.bcos.web3j.abi.datatypes.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.fastjson.JSONObject;

import cn.bcos.browser.contract.Contract;


/**
 * start the service
 *
 */
public class Main {
	
	private static Logger logger = LoggerFactory.getLogger(Main.class);
	
	public static void main(String[] args) throws Throwable {
		logger.debug("initialize GovernService");
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		
	}
}
