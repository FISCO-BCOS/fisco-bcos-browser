package org.bcos.browser.service;

import org.bcos.browser.dto.TbTransactionReceiptDto;
import org.apache.ibatis.annotations.Param;

/**
 * @Description:交易回执信息表，服务
 * @Author: v_wbsqwu
 * @Date: 2017/11/2 15:40
 */
public interface TbTransactionReceiptService {
    /*根据pkHash查询交易回执信息*/
    TbTransactionReceiptDto getTbTransactionReceiptByPkHash(@Param(value = "pkHash") String pkHash);
}
