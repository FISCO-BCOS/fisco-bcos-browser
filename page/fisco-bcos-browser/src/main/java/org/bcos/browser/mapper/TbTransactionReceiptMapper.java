package org.bcos.browser.mapper;

import org.bcos.browser.dto.TbTransactionReceiptDto;
import org.apache.ibatis.annotations.Param;

/**
 * @Description:交易回执的表,mapper
 * @Author: v_wbsqwu
 * @Date: 2017/11/2 15:06
 */
public interface TbTransactionReceiptMapper {
    /*根据pkHash查询交易回执信息*/
    TbTransactionReceiptDto getTbTransactionReceiptByPkHash(@Param(value = "pkHash") String pkHash);
}
