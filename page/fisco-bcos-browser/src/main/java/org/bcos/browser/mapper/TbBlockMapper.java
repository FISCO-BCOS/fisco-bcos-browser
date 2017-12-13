package org.bcos.browser.mapper;

import org.bcos.browser.dto.TbBlockDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *@Description: 每个区块的表，mapper
 *@Author: v_wbsqwu
 *@Date: 2017/10/13 11:58
 */
public interface TbBlockMapper {
    /*查询区块表记录总数*/
    int getAllBlockCount();
    /*分页查询区块表的记录*/
    List<TbBlockDto> getBlockInfoByOffset(@Param(value = "offset") Integer offset, @Param(value = "size") Integer size);
    /*根据块Hash查询块*/
    TbBlockDto getBlockDetailInfoByBlockHash(@Param(value = "blockHash") String blockHash);
}
