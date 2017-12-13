package org.bcos.browser.service;


import org.bcos.browser.dto.TbPendingTransactionDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *@Description: 正在处理但还未上链的交易表,服务
 *@Author: v_wbsqwu
 *@Date: 2017/10/13 11:57
 */
public interface TbPendingTransactionService {
    /*获取正在处理但还未上链的交易表的总记录数*/
    int getAllPendingTransactionCount();
    /*分页查询最新的记录*/
    List<TbPendingTransactionDto> getTbPendingTransactionByOffset(Integer offset, Integer size);
    /*根据pkHash查询*/
    TbPendingTransactionDto getTbPendingTransactionByPkHash(@Param("pkHash") String pkHash);
}
