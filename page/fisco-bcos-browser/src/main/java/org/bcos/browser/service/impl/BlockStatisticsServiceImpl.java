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
 * @file: BlockStatisticsServiceImpl.java
 * @author: v_wbsqwu
 * @date: 2018
 */

package org.bcos.browser.service.impl;

import org.bcos.browser.base.Constants;
import org.bcos.browser.base.TablePrefixEnum;
import org.bcos.browser.base.utils.DateTimeUtils;
import org.bcos.browser.base.utils.ProppertiesUtil;
import org.bcos.browser.dto.TbStatBlockDto;
import org.bcos.browser.dto.TbTablesDto;
import org.bcos.browser.mapper.TbStatBlockMapper;
import org.bcos.browser.mapper.TbTablesMapper;
import org.bcos.browser.service.BlockStatisticsService;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class BlockStatisticsServiceImpl implements BlockStatisticsService {
    private static Logger LOGGER =  LoggerFactory.getLogger(BlockStatisticsServiceImpl.class);

    @Autowired
    TbTablesMapper tbTablesMapper;
    @Autowired
    TbStatBlockMapper tbStatBlockMapper;

    /**
     *@Description:Query the total number of records in the block flow statistics list
     */
    @Override
    public int countTbStatBlock(Map<String,Object> map) {
        LOGGER.info("countTbStatBlock start. map:{}",JSON.toJSONString(map));

        //Dynamically obtain the table name according to the date
        String tableName = getTableName(map.get("dateStr"));

        TbTablesDto tbTablesDto = tbTablesMapper.getTableByName(ProppertiesUtil.getDbName(),tableName);
        LOGGER.info("getTableByName result:{}",JSON.toJSONString(tbTablesDto));
        if(tbTablesDto==null){
            return 0;
        }

        //Get the total number of records in the block process
        map.put("tableName",tableName);
        int count = tbStatBlockMapper.countTbStatBlock(map);

        LOGGER.info("countTbStatBlock end. count:{}",count);
        return count;
    }



    /**
     *@Description:Paging query block process statistics list
     */
    @Override
    public List<TbStatBlockDto> listTbStatBlock(Map<String,Object> map) {
        LOGGER.info("listTbStatBlock start. map:{}",JSON.toJSONString(map));

        //Dynamically obtain the table name according to the date
        String tableName = getTableName(map.get("dateStr"));

        TbTablesDto tbTablesDto = tbTablesMapper.getTableByName(ProppertiesUtil.getDbName(),tableName);
        LOGGER.info("getTableByName result:{}",JSON.toJSONString(tbTablesDto));
        if(tbTablesDto==null){
            return null;
        }

        //Paging query block process statistics list
        map.put("tableName",tableName);
        List<TbStatBlockDto> list = tbStatBlockMapper.listTbStatBlock(map);
        LOGGER.info("listTbStatBlock end.  list:{}", JSON.toJSONString(list));
        return list;
    }


    /**
     *@Description: Get dynamic table name
     */
    private final String getTableName(Object dateTime){
        String tableName;
        if(dateTime != null && dateTime.toString().replace("-","").trim().length()>0){
            String dateTimeStr = dateTime.toString().replace("-","").trim();
            tableName = TablePrefixEnum.TB_STAT_BLOCK.getValue() + dateTimeStr;
        }else {
            Date date = new Date();
            String dateStr = DateTimeUtils.date2String(date, Constants.DATE_FORMAT_YYMMDD);
            tableName = TablePrefixEnum.TB_STAT_BLOCK.getValue() + dateStr;
        }
        LOGGER.debug("tableName:{}",tableName);
        return  tableName;
    }
}
