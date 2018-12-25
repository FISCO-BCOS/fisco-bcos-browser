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
import org.bcos.browser.dto.TbCnsContractDto;
import org.bcos.browser.dto.TbMarketAuctionSuccessEventDto;
import org.bcos.browser.service.TbAddWarrantEventService;
import org.bcos.browser.service.TbCnsContractService;
import org.bcos.browser.service.TbMarketAuctionSuccessService;
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
		
		{//测试获取AddWarrantEvent表的数据
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
		
		{//测试获取MarketAuctionSuccessEvent表的数据
		    TbMarketAuctionSuccessService service = context.getBean(TbMarketAuctionSuccessService.class);
	        //查询所有的交易记录。TODO 分页查询？
	        List<TbMarketAuctionSuccessEventDto> results = service.getAllMarketAuctionSuccessEvent();
	        if(results == null) return;
	        for(TbMarketAuctionSuccessEventDto result:results)
	        {
	            System.out.println(result);
	        }
	        //按照仓单ID，查询单个仓单的所有交易记录
	        results = service.getMarketAuctionSuccessEventByID("10");
	        for(TbMarketAuctionSuccessEventDto result:results)
            {
                System.out.println(result);
            }
		}
		
		{//测试获取CNS数据
		    TbCnsContractService service = context.getBean(TbCnsContractService.class);
            //查询所有的交易记录。TODO 分页查询？
            TbCnsContractDto result = service.getContractAInfoByAddress("0x0c8706a240099d2971553b8d4c0da8171e56e57f");
            if(result == null) return;
            System.out.println(result);
		}
		
		
		
	}
}
