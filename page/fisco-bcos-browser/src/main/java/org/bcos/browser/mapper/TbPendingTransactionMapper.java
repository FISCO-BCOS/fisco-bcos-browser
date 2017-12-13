package org.bcos.browser.mapper;

import org.bcos.browser.dto.TbPendingTransactionDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *@Description: 正在处理但还未上链的交易表,mapper
 *@Author: v_wbsqwu
 *@Date: 2017/10/13 11:58
 */
public interface TbPendingTransactionMapper {
    /*获取正在处理但还未上链的交易表的总记录数*/
    int getAllPendingTransactionCount();
    /*分页查询正在处理但还未上链的交易表的记录*/
    List<TbPendingTransactionDto> getTbPendingTransactionByOffset(@Param(value = "offset") Integer offset, @Param(value = "size") Integer size);
    /*根据pkHash查询*/
    TbPendingTransactionDto getTbPendingTransactionByPkHash(@Param("pkHash") String pkHash);
}
