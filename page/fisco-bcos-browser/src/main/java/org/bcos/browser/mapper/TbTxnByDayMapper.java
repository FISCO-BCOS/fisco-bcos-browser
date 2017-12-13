package org.bcos.browser.mapper;

import org.bcos.browser.dto.TbTxnByDayDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *@Description: 每日交易量记录表,mapper
 *@Author: v_wbsqwu
 *@Date: 2017/10/13 11:58
 */
public interface TbTxnByDayMapper {
   /*分页查询最新的记录*/
    List<TbTxnByDayDto> getLastTbTxnByDayByOffset(@Param(value = "offset") Integer offset, @Param(value = "size") Integer size);
}
