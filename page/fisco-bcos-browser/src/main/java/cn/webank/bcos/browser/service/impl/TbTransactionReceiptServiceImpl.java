package cn.webank.bcos.browser.service.impl;

import cn.webank.bcos.browser.controller.HomeController;
import cn.webank.bcos.browser.dto.TbTransactionDto;
import cn.webank.bcos.browser.dto.TbTransactionReceiptDto;
import cn.webank.bcos.browser.mapper.TbTransactionReceiptMapper;
import cn.webank.bcos.browser.service.TbTransactionReceiptService;
import com.alibaba.fastjson.JSON;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description:Transaction receipt information table, service
 * @Author: v_wbsqwu
 * @Date: 2017/11/2 15:41
 */
@Service
public class TbTransactionReceiptServiceImpl implements TbTransactionReceiptService {
    private static Logger LOGGER =  LoggerFactory.getLogger(TbTransactionReceiptService.class);

    @Autowired
    TbTransactionReceiptMapper tbTransactionReceiptMapper;

    /**
     *@Description: According to pkHash query transaction receipt information
     */
    @Override
    public TbTransactionReceiptDto getTbTransactionReceiptByPkHash(@Param(value = "pkHash") String pkHash) {
        LOGGER.info("getTbTransactionReceiptByPkHash.start pkHash:{}",pkHash);
        TbTransactionReceiptDto tbTransactionReceiptDto = tbTransactionReceiptMapper.getTbTransactionReceiptByPkHash(pkHash);
        LOGGER.info("getTbTransactionReceiptByPkHash.end result:{}", JSON.toJSONString(tbTransactionReceiptDto));
        return tbTransactionReceiptDto;
    }
}
