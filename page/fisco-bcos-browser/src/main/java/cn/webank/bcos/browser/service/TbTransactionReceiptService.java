package cn.webank.bcos.browser.service;

import cn.webank.bcos.browser.dto.TbTransactionReceiptDto;
import org.apache.ibatis.annotations.Param;

/**
 * @Description:Transaction receipt information table, service
 * @Author: v_wbsqwu
 * @Date: 2017/11/2 15:40
 */
public interface TbTransactionReceiptService {
    /*According to pkHash query transaction receipt information*/
    TbTransactionReceiptDto getTbTransactionReceiptByPkHash(@Param(value = "pkHash") String pkHash);
}
