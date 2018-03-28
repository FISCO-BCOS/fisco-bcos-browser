package cn.webank.bcos.browser.mapper;

import cn.webank.bcos.browser.dto.TbPendingTransactionDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *@Description: Dealing table that is being processed but not yet chained,mapper
 *@Author: v_wbsqwu
 *@Date: 2017/10/13 11:58
 */
public interface TbPendingTransactionMapper {
    /*Get the total number of records that are processing but not yet linked to the transaction table*/
    int getAllPendingTransactionCount();
    /*Paging query records of transaction tables that are processed but not yet chained*/
    List<TbPendingTransactionDto> getTbPendingTransactionByOffset(@Param(value = "offset") Integer offset, @Param(value = "size") Integer size);
    /*According to pkHash query*/
    TbPendingTransactionDto getTbPendingTransactionByPkHash(@Param("pkHash")String pkHash);
}
