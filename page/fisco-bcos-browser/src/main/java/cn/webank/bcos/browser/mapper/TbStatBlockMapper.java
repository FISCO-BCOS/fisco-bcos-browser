package cn.webank.bcos.browser.mapper;

import cn.webank.bcos.browser.dto.TbStatBlockDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Description:Blocking process statistics information table,mapper
 * @Author: v_wbsqwu
 * @Date: 2017/11/22 11:23
 */
public interface TbStatBlockMapper {
    //Query the total number of records in the block flow statistics list
    int countTbStatBlock(Map<String,Object> map);
    //Paging query block process statistics list
    List<TbStatBlockDto> listTbStatBlock(Map<String,Object> map);
}
