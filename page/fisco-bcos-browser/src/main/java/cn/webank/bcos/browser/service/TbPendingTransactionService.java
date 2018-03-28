package cn.webank.bcos.browser.service;


import cn.webank.bcos.browser.dto.TbPendingTransactionDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *@Description: Dealing with but not yet linked transaction tables, services
 *@Author: v_wbsqwu
 *@Date: 2017/10/13 11:57
 */
public interface TbPendingTransactionService {
    /*Get the total number of records that are processing but not yet linked to the transaction table*/
    int getAllPendingTransactionCount();
    /*Paging query the latest record*/
    List<TbPendingTransactionDto> getTbPendingTransactionByOffset(Integer offset, Integer size);
    /*According to pkHash query*/
    TbPendingTransactionDto getTbPendingTransactionByPkHash(@Param("pkHash")String pkHash);
}
