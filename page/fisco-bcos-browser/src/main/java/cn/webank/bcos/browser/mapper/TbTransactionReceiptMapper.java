package cn.webank.bcos.browser.mapper;

import cn.webank.bcos.browser.dto.TbTransactionReceiptDto;
import org.apache.ibatis.annotations.Param;

/**
 * @Description:交易回执的表,mapper
 * @Author: v_wbsqwu
 * @Date: 2017/11/2 15:06
 */
public interface TbTransactionReceiptMapper {
    /*Table of transaction receipts*/
    TbTransactionReceiptDto getTbTransactionReceiptByPkHash(@Param(value = "pkHash") String pkHash);
}
