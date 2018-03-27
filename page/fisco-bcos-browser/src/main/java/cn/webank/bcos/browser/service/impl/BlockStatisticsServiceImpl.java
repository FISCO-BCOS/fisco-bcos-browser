package cn.webank.bcos.browser.service.impl;

import cn.webank.bcos.browser.base.Constants;
import cn.webank.bcos.browser.base.TablePrefixEnum;
import cn.webank.bcos.browser.base.utils.DateTimeUtils;
import cn.webank.bcos.browser.base.utils.ProppertiesUtil;
import cn.webank.bcos.browser.dto.TbStatBlockDto;
import cn.webank.bcos.browser.dto.TbTablesDto;
import cn.webank.bcos.browser.mapper.TbStatBlockMapper;
import cn.webank.bcos.browser.mapper.TbTablesMapper;
import cn.webank.bcos.browser.service.BlockStatisticsService;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description:Block flow statistics, services
 * @Author: v_wbsqwu
 * @Date: 2017/11/23 10:24
 */
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
