package org.bcos.browser.service.impl;

import org.bcos.browser.dto.TbTransactionReceiptDto;
import org.bcos.browser.mapper.TbTransactionReceiptMapper;
import org.bcos.browser.service.TbTransactionReceiptService;
import com.alibaba.fastjson.JSON;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description:交易回执信息表，服务
 * @Author: v_wbsqwu
 * @Date: 2017/11/2 15:41
 */
@Service
public class TbTransactionReceiptServiceImpl implements TbTransactionReceiptService {
    private static Logger LOGGER =  LoggerFactory.getLogger(TbTransactionReceiptService.class);

    @Autowired
    TbTransactionReceiptMapper tbTransactionReceiptMapper;

    /**
     *@Description: 根据pkHash查询交易回执信息
     */
    @Override
    public TbTransactionReceiptDto getTbTransactionReceiptByPkHash(@Param(value = "pkHash") String pkHash) {
        LOGGER.info("getTbTransactionReceiptByPkHash.start pkHash:{}",pkHash);
        TbTransactionReceiptDto tbTransactionReceiptDto = tbTransactionReceiptMapper.getTbTransactionReceiptByPkHash(pkHash);
        LOGGER.info("getTbTransactionReceiptByPkHash.end result:{}", JSON.toJSONString(tbTransactionReceiptDto));
        return tbTransactionReceiptDto;
    }
}
