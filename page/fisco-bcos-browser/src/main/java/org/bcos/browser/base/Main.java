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

package org.bcos.browser.base;

import java.util.List;

import org.bcos.browser.dto.TbAddWarrantEventDto;
import org.bcos.browser.service.TbAddWarrantEventService;
import org.bcos.browser.service.TbNodesInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.fastjson.JSONObject;



/**
 * start the service
 *
 */
public class Main {
	
	private static Logger logger = LoggerFactory.getLogger(Main.class);
	
	public static void main(String[] args) throws Throwable {
		logger.debug("initialize GovernService");
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/applicationContext.xml");
		
		TbAddWarrantEventService service = context.getBean(TbAddWarrantEventService.class);
		//查询所有的仓单。TODO 分页查询？
		List<TbAddWarrantEventDto> results = service.getAllAddWarrantEvent();
		if(results == null) return;
		for(TbAddWarrantEventDto result:results)
		{
		    System.out.println(result);
		}
		//按照仓单ID，查询单个仓单
		TbAddWarrantEventDto result = service.getAddWarrantEventByID("10");
		System.out.println(result);
		
	}
}
