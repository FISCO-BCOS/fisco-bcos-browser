package cn.webank.bcos.browser.service.impl;

import cn.webank.bcos.browser.base.Constants;
import cn.webank.bcos.browser.base.TablePrefixEnum;
import cn.webank.bcos.browser.base.utils.DateTimeUtils;
import cn.webank.bcos.browser.base.utils.ProppertiesUtil;
import cn.webank.bcos.browser.dto.TbStatDto;
import cn.webank.bcos.browser.dto.TbTablesDto;
import cn.webank.bcos.browser.entity.req.ReqListTbStatByObjectAttrVO;
import cn.webank.bcos.browser.mapper.TbStatMapper;
import cn.webank.bcos.browser.mapper.TbTablesMapper;
import cn.webank.bcos.browser.service.SingleStatisticsService;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Description:Single point statistics table, service
 * @Author: v_wbsqwu
 * @Date: 2017/11/22 16:50
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
