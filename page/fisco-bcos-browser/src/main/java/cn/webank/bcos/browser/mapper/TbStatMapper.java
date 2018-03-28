package cn.webank.bcos.browser.mapper;

import cn.webank.bcos.browser.dto.TbStatDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Description:Single point statistics table,mapper
 * @Author: v_wbsqwu
 * @Date: 2017/11/22 11:23
 */
public interface TbStatMapper {
    /*Query Object list*/
    List<String> listObject(@Param(value = "tableName") String tableName);
    /*Query property list*/
    List<TbStatDto> listAttr(@Param(value = "tableName") String tableName);
    /*Query a single point statistics list*/
    List<TbStatDto> ListTbStat(Map<String,Object> map);
}
