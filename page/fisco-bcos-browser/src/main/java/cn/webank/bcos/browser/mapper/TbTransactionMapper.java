package cn.webank.bcos.browser.mapper;

import cn.webank.bcos.browser.dto.TbTransactionDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 *@Description: Transaction data，mapper
 *@Author: v_wbsqwu
 *@Date: 2017/10/13 11:58
 */
public interface TbTransactionMapper {
    /*Get the total number of records in the transaction information table*/
    int getAllTransactionCount(Map<String,Object> map);
    /*Paging query the latest record*/
    List<TbTransactionDto> getTbTransactionByOffset(Map<String,Object> map);
    /*According to pkHash query transaction table records*/
    TbTransactionDto getTbTransactionByPkHash(@Param(value = "pkHash") String pkHash);
}
