package cn.webank.bcos.browser.mapper;

import cn.webank.bcos.browser.dto.TbBlockDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 *@Description: Table for each block，mapper
 *@Author: v_wbsqwu
 *@Date: 2017/10/13 11:58
 */
public interface TbBlockMapper {
    /*Query the total number of block table records*/
    int getAllBlockCount(Map<String,Object> map);
    /*Paging query block table records*/
    List<TbBlockDto> getBlockInfoByOffset(Map<String,Object> map);
    /*According to Block Hash Query Block*/
    TbBlockDto getBlockDetailInfoByBlockHash(@Param(value = "blockHash") String blockHash);
}
