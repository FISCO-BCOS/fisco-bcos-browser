package org.bcos.browser.mapper;

import org.bcos.browser.dto.TbTransactionDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *@Description: 交易数据的，mapper
 *@Author: v_wbsqwu
 *@Date: 2017/10/13 11:58
 */
public interface TbTransactionMapper {
    /*获取交易信息表的总记录数*/
    int getAllTransactionCount(@Param(value = "blockHeight") Integer blockHeight);
    /*分页查询最新的记录*/
    List<TbTransactionDto> getTbTransactionByOffset(@Param(value = "blockHeight") Integer blockHeight, @Param(value = "offset") Integer offset, @Param(value = "size") Integer size);
    /*根据pkHash查询交易表的记录*/
    TbTransactionDto getTbTransactionByPkHash(@Param(value = "pkHash") String pkHash);
}
