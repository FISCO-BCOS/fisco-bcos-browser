package cn.webank.bcos.browser.mapper;

import cn.webank.bcos.browser.dto.TbTablesDto;
import org.apache.ibatis.annotations.Param;

/**
 * @Description:Database table，mapper
 * @Author: v_wbsqwu
 * @Date: 2017/12/8 17:44
 */
public interface TbTablesMapper {
    //Query database table
    TbTablesDto getTableByName(@Param(value = "dbName") String dbName,@Param(value = "tableName") String tableName);
}
