package cn.webank.bcos.browser.service;


import cn.webank.bcos.browser.dto.TbTransactionDto;

import java.util.List;
import java.util.Map;

/**
 *@Description: Transaction data, services
 *@Author: v_wbsqwu
 *@Date: 2017/10/13 11:57
 */
public interface TbTransactionService {
    /*Get the total number of records in the transaction information table*/
    int getAllTransactionCount(Map<String,Object> map);
    /*Paging query the latest record*/
    List<TbTransactionDto> getTbTransactionByOffset(Map<String,Object> map);
    /*According to pkHash query transaction table records*/
    TbTransactionDto getTbTransactionByPkHash(String pkHash);
}
