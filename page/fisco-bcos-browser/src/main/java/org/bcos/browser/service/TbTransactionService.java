package org.bcos.browser.service;


import org.bcos.browser.dto.TbTransactionDto;

import java.util.List;

/**
 *@Description: 交易数据的,服务
 *@Author: v_wbsqwu
 *@Date: 2017/10/13 11:57
 */
public interface TbTransactionService {
    /*获取交易信息表的总记录数*/
    int getAllTransactionCount(Integer blockHeight);
    /*分页查询最新的记录*/
    List<TbTransactionDto> getTbTransactionByOffset(Integer blockHeight, Integer offset, Integer size);
    /*根据pkHash查询交易表的记录*/
    TbTransactionDto getTbTransactionByPkHash(String pkHash);
}
