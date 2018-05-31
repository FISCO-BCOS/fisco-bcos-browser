package org.bcos.browser.service.impl;

import org.bcos.browser.base.Constants;
import org.bcos.browser.base.TablePrefixEnum;
import org.bcos.browser.base.utils.DateTimeUtils;
import org.bcos.browser.base.utils.ProppertiesUtil;
import org.bcos.browser.dto.TbStatDto;
import org.bcos.browser.dto.TbTablesDto;
import org.bcos.browser.entity.req.ReqListTbStatByObjectAttrVO;
import org.bcos.browser.mapper.TbStatMapper;
import org.bcos.browser.mapper.TbTablesMapper;
import org.bcos.browser.service.SingleStatisticsService;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
 * @file: SingleStatisticsServiceImpl.java
 * @author: v_wbsqwu
 * @date: 2018
 */
@Service
public class SingleStatisticsServiceImpl implements SingleStatisticsService {
    private static Logger LOGGER =  LoggerFactory.getLogger(SingleStatisticsServiceImpl.class);

    @Autowired
    TbStatMapper tbStatMapper;
    @Autowired
    TbTablesMapper tbTablesMapper;

    /**
     *@Description: Query Object list
     */
    @Override
    public List<String> listObject(String dateStr) {
        LOGGER.info("listObject start. dateStr:{}",dateStr);

        String tableName = getTableName(dateStr);

        TbTablesDto tbTablesDto = tbTablesMapper.getTableByName(ProppertiesUtil.getDbName(),tableName);
        LOGGER.info("getTableByName result:{}",JSON.toJSONString(tbTablesDto));
        if(tbTablesDto==null){
            return null;
        }

        List<String> objects = tbStatMapper.listObject(tableName);

        LOGGER.info("listObject end.   objects:{}", JSON.toJSONString(objects));
        return objects;
    }



    /**
     *@Description:Query property list
     */
    @Override
    public List<TbStatDto> listAttr(String dateStr) {
        LOGGER.info("listAttr start. dateStr:{}",dateStr);

        String tableName = getTableName(dateStr);

        TbTablesDto tbTablesDto = tbTablesMapper.getTableByName(ProppertiesUtil.getDbName(),tableName);
        LOGGER.info("getTableByName result:{}",JSON.toJSONString(tbTablesDto));
        if(tbTablesDto==null){
            return null;
        }

        List<TbStatDto> list = tbStatMapper.listAttr(tableName);

        LOGGER.info("listAttr end.   list:{}", JSON.toJSONString(list));
        return list;
    }



    /**
     *@Description: Query a single point statistics list
     */
    @Override
    public List<List<TbStatDto>> ListTbStat(ReqListTbStatByObjectAttrVO reqVo) {
        LOGGER.info("ListTbStat start.  reqVo:{}",JSON.toJSONString(reqVo));
        List<List<TbStatDto>> list = new ArrayList<>();

        String tableName = getTableName(reqVo.getDataStr());

        TbTablesDto tbTablesDto = tbTablesMapper.getTableByName(ProppertiesUtil.getDbName(),tableName);
        LOGGER.info("getTableByName result:{}",JSON.toJSONString(tbTablesDto));
        if(tbTablesDto==null){
            return null;
        }

        //Database query parameters
        Map<String,Object> map = new HashedMap();
        map.put("tableName",tableName);
        map.put("timeStartStr",reqVo.getTimeStartStr());
        map.put("timeEndStr",reqVo.getTimeEndtStr());

        //Query a single point statistics list
        for(String obj:reqVo.getObjArr()){
            for(String attr:reqVo.getAttrArr()){
                map.put("object",obj);
                map.put("attr",attr);
                List<TbStatDto> tbStatDtoList = tbStatMapper.ListTbStat(map);
                if(tbStatDtoList==null){
                    LOGGER.warn("listAttr.fail tableNameStr:{},obj:{},attr:{}",tableName,obj,attr);
                    continue;
                }

                Collections.sort(tbStatDtoList);//Sorting
                list.add(tbStatDtoList);
            }
        }

        LOGGER.info("listAttr.end.   list:{}", JSON.toJSONString(list));
        return list;
    }



    /**
     *@Description: Get dynamic table name
     */
    private final String getTableName(String dateTimeStr){
        String tableName;
        if(StringUtils.isNotBlank(dateTimeStr)){
            dateTimeStr = dateTimeStr.replace("-","");
            tableName = TablePrefixEnum.TB_SINGLE_STAT.getValue() + dateTimeStr;
        }else {
            Date date = new Date();
            String dateStr = DateTimeUtils.date2String(date, Constants.DATE_FORMAT_YYMMDD);
            tableName = TablePrefixEnum.TB_SINGLE_STAT.getValue() + dateStr;
        }
        LOGGER.debug("tableName:{}",tableName);
        return  tableName;
    }
}
