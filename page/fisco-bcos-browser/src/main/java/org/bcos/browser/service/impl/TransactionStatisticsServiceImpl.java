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
 * @file: TransactionStatisticsServiceImpl.java
 * @author: v_wbsqwu
 * @date: 2018
 */

package org.bcos.browser.service.impl;

import org.bcos.browser.base.Constants;
import org.bcos.browser.base.TablePrefixEnum;
import org.bcos.browser.base.utils.DateTimeUtils;
import org.bcos.browser.base.utils.ProppertiesUtil;
import org.bcos.browser.dto.TbStatTransactionDto;
import org.bcos.browser.dto.TbTablesDto;
import org.bcos.browser.mapper.TbStatTransactionMapper;
import org.bcos.browser.mapper.TbTablesMapper;
import org.bcos.browser.service.TransactionStatisticsService;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;


@Service
public class TransactionStatisticsServiceImpl implements TransactionStatisticsService {
    private static Logger LOGGER =  LoggerFactory.getLogger(TransactionStatisticsServiceImpl.class);

    @Autowired
    TbStatTransactionMapper tbStatTransactionMapper;
    @Autowired
    TbTablesMapper tbTablesMapper;

    @Override
    public int countTbStatTransaction(Map<String,Object> map) {
        LOGGER.info("countTbStatTransaction start. map:{}",JSON.toJSONString(map));

        //根据日期动态获取表格名称
        String tableName = getTableName(map.get("dateStr"));

        TbTablesDto tbTablesDto = tbTablesMapper.getTableByName(ProppertiesUtil.getDbName(),tableName);
        LOGGER.info("getTableByName result:{}",JSON.toJSONString(tbTablesDto));
        if(tbTablesDto==null){
            return 0;
        }

        //获取交易流程总记录数
        map.put("tableName",tableName);
        int count = tbStatTransactionMapper.countTbStatTransaction(map);

        LOGGER.info("countTbStatTransaction end. count:{}",count);
        return count;
    }

    /**
     *@Description:分页查询交易流程统计信息列表
     */
    @Override
    public List<TbStatTransactionDto> listTbStatTransaction(Map<String,Object> map) {
        LOGGER.info("listTbStatTransaction start. map:{}",JSON.toJSONString(map));

        //根据日期动态获取表格名称
        String tableName = getTableName(map.get("dateStr"));

        TbTablesDto tbTablesDto = tbTablesMapper.getTableByName(ProppertiesUtil.getDbName(),tableName);
        LOGGER.info("getTableByName result:{}",JSON.toJSONString(tbTablesDto));
        if(tbTablesDto==null){
            return null;
        }

        //分页查询交易流程统计信息列表
        map.put("tableName",tableName);
        List<TbStatTransactionDto> list = tbStatTransactionMapper.listTbStatTransaction(map);

        LOGGER.info("listTbStatTransaction end. list:{}", JSON.toJSONString(list));
        return list;
    }


    /**
     *@Description: 获取动态表格名称
     */
    private final String getTableName(Object dateTime){
        String tableName;
        if(dateTime != null && dateTime.toString().replace("-","").trim().length()>0){
            String dateTimeStr = dateTime.toString().replace("-","").trim();
            tableName = TablePrefixEnum.TB_STAT_TRANSACTION.getValue() + dateTimeStr;
        }else {
            Date date = new Date();
            String dateStr = DateTimeUtils.date2String(date, Constants.DATE_FORMAT_YYMMDD);
            tableName = TablePrefixEnum.TB_STAT_TRANSACTION.getValue() + dateStr;
        }
        LOGGER.debug("tableName:{}",tableName);
        return  tableName;
    }
}
